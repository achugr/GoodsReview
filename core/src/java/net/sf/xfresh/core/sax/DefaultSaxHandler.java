package net.sf.xfresh.core.sax;

import net.sf.xfresh.core.SaxHandler;
import net.sf.xfresh.core.SelfSaxWriter;
import net.sf.xfresh.core.SelfWriter;
import net.sf.xfresh.core.xml.Tagable;
import net.sf.xfresh.util.XmlUtil;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static net.sf.xfresh.util.XmlUtil.*;
import static org.apache.commons.lang.ArrayUtils.contains;

/**
 * User: darl (darl@yandex-team.ru)
 * Date: 3/21/11 8:23 PM
 */
public class DefaultSaxHandler implements SaxHandler {
    private static final Logger log = Logger.getLogger(DefaultSaxHandler.class);


    private static final Class[] PRIMITIVES = {
            String.class,
            Long.class,
            Integer.class,
            Double.class,
            Float.class,
            Date.class,
    };

    private static final Class[] ATTRIBUTES = {
            Long.class,
            Integer.class,
            Double.class,
            Float.class,
            Date.class,
    };

    private static final String[] STOP_NAMES = {
            "class",
            "parent",
    };

    private static final Object[] EMPTY_ARGS = null;
    private static final Object INVALID_OBJ = new Object();

    private static final String COLLECTION_ELEMENT = "collection";
    private static final String MAP_ELEMENT = "map";

    private final ContentHandler handler;

    public DefaultSaxHandler(final ContentHandler contentHandler) {
        this.handler = contentHandler;
    }

    public void writeAny(final String externalName, final Object dataItem) throws SAXException {
        if (dataItem instanceof SelfWriter) {
            ((SelfWriter) dataItem).writeTo(handler);
        } else if (dataItem instanceof Tagable) {
            ((Tagable) dataItem).asTag().writeTo(handler);
        } else if (dataItem instanceof SelfSaxWriter) {
            ((SelfSaxWriter) dataItem).writeTo(this);
        } else if (dataItem instanceof Collection) {
            writeCollection(externalName, (Collection<?>) dataItem);
        } else if (dataItem instanceof Map) {
            writeMap(externalName, (Map<?, ?>) dataItem);
        } else {
            writeItem(handler, externalName, dataItem);
        }
    }

    public void writeCollection(final String externalName, final Collection<?> collection) throws SAXException {
        final String element = (externalName == null) ? COLLECTION_ELEMENT : externalName;
        start(handler, element);
        for (final Object dataItem : collection) {
            writeAny(null, dataItem);
        }
        end(handler, element);
    }

    public void writeMap(final String externalName, final Map<?, ?> map) throws SAXException {
        final String element = (externalName == null) ? MAP_ELEMENT : externalName;
        start(handler, element);

        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            final String entryElement = entry.getKey().toString();

            start(handler, entryElement);
            writeShortly(entry.getValue());
            end(handler, entryElement);
        }

        end(handler, element);
    }

    public ContentHandler getContentHandler() {
        return handler;
    }

    private void writeShortly(final Object dataItem) throws SAXException {
        if (isPrimitive(dataItem.getClass())) {
            text(handler, encode(dataItem.toString()));
        } else {
            writeAny(null, dataItem);
        }
    }

    private void writeItem(final ContentHandler handler, final String externalName, final Object dataItem) throws SAXException {
        if (dataItem == null) {
            empty(handler, externalName != null ? externalName : "null");
        } else {
            final String elementName = externalName != null ? externalName : toStandart(dataItem.getClass().getSimpleName());
            if (isPrimitive(dataItem.getClass())) {
                writePrimitive(handler, elementName, dataItem.toString());
            } else {
                final Map<String, ValueInfo> properties = extractProperties(dataItem);
                final AttributesImpl attributes = createAttributes(properties);
                start(handler, elementName, attributes);
                writeContent(properties);
                end(handler, elementName);
            }
        }
    }

    private void writeContent(final Map<String, ValueInfo> properties) throws SAXException {
        for (final Map.Entry<String, ValueInfo> property : properties.entrySet()) {
            final ValueInfo valueInfo = property.getValue();
            if (!isAttribute(valueInfo.getClazz())) {
                writeAny(toStandart(property.getKey()), valueInfo.getValue());
            }
        }
    }


    private AttributesImpl createAttributes(final Map<String, ValueInfo> properties) {
        final AttributesImpl attributes = new AttributesImpl();
        for (final Map.Entry<String, ValueInfo> property : properties.entrySet()) {
            final ValueInfo valueInfo = property.getValue();
            final Class<?> valueClass = valueInfo.getClazz();
            if (isAttribute(valueClass)) {
                final String name = toStandart(property.getKey());
                attributes.addAttribute("", name, name, XmlUtil.NULL_TYPE, valueInfo.getValue().toString());
            }
        }
        return attributes;
    }

    private void writePrimitive(final ContentHandler handler, final String xmlName, final String value)
            throws SAXException {
        start(handler, xmlName);
        text(handler, encode(value));
        end(handler, xmlName);
    }

    private Map<String, ValueInfo> extractProperties(final Object dataItem) {
        Map<String, ValueInfo> result = Collections.<String, ValueInfo>emptyMap();
        try {
            final Class<?> clazz = dataItem.getClass();
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            result = new HashMap<String, ValueInfo>(propertyDescriptors.length);
            for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                final Object propertyValue = getPropertyValue(dataItem, propertyDescriptor);
                log.debug(propertyDescriptor.getName() + ": " + propertyValue);
                if (propertyValue != INVALID_OBJ && propertyValue != dataItem) {
                    result.put(
                            propertyDescriptor.getName(),
                            new ValueInfo(propertyDescriptor.getPropertyType(), propertyValue));
                }
            }
        } catch (IntrospectionException e) {
            log.error("Error while introspecting object " + dataItem, e);// ignored
        }
        return result;
    }

    @Nullable
    private Object getPropertyValue(final Object dataItem, final PropertyDescriptor propertyDescriptor) {
        final String name = propertyDescriptor.getName();
        if (!contains(STOP_NAMES, name)) {
            final Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod == null) { // null if the property can't be read
                return INVALID_OBJ;
            }
            try {
                return readMethod.invoke(dataItem, EMPTY_ARGS);
            } catch (IllegalAccessException e) {
                log.error("Error while reading value of property " + name + " in object " +
                        dataItem, e);// ignored
            } catch (InvocationTargetException e) {
                log.error("Error while reading value of property " + name + " in object " +
                        dataItem, e);// ignored
            }
        }
        return INVALID_OBJ;
    }

    private static class ValueInfo {

        private final Class clazz;

        private final Object value;

        private ValueInfo(final Class clazz, final Object value) {
            this.clazz = clazz;
            this.value = value;
        }

        public Class getClazz() {
            return clazz;
        }

        public Object getValue() {
            return value;
        }
    }

    private boolean isPrimitive(final Class<?> clazz) {
        return contains(PRIMITIVES, clazz);
    }

    private boolean isAttribute(final Class<?> clazz) {
        return contains(ATTRIBUTES, clazz) || clazz.isPrimitive();
    }

    private String encode(final String value) {
        return value;
    }
}
