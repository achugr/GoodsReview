package net.sf.xfresh.core.xml;

import net.sf.xfresh.core.SelfWriter;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

import java.util.*;

/**
 * Author: Alexander Astakhov (alalast@yandex.ru)
 * Date: 02.01.11 1:35
 */
public class Xmler {

    private Xmler() {
    }

    public static final Tag EMPTY_TAG = new Tag() {
        public void writeTo(final ContentHandler target) {
            //do nothing
        }
    };

    public static final Tag[] EMPTY_TAG_ARRAY = {};

    private static final Attributes EMPTY_ATTRIBUTES = new AttributesImpl();

    public static Tag openTag(final String name) {
        return openTag(name, null);
    }

    public static Tag openTag(final String name, final Attribute attribute) {
        return new Tag() {
            public void writeTo(final ContentHandler xmlWriter) {
                startElement(xmlWriter, attribute != null ? attribute.asAttributes() : EMPTY_ATTRIBUTES, name);
            }
        };
    }

    public static Tag closeTag(final String name) {
        return new Tag() {
            public void writeTo(final ContentHandler xmlWriter) {
                endElement(xmlWriter, name);
            }
        };
    }

    public static Tag tag(final String name, @Nullable final Attribute attribute) {
        return tag(name, attribute, Collections.<Tag>emptyList());
    }

    public static Tag tag(final String name, @Nullable final Attribute attribute, final List<? extends Tagable>... tags) {
        final List<Tagable> tmp = new LinkedList<Tagable>();
        for (final List<? extends Tagable> list : tags) {
            tmp.addAll(list);
        }
        return tag(name, attribute, tmp);
    }

    public static Tag tag(final String name, @Nullable final Attribute attribute, final List<? extends Tagable> tags) {
        return tag(name, attribute, tags.toArray(new Tagable[tags.size()]));
    }


    public static Tag tagOrEmpty(final String name, final @Nullable String value) {
        if (value == null) {
            return EMPTY_TAG;
        }
        return tag(name, value);
    }

    /**
     * Creates Tag with given name, attributes, and nested tags.
     *
     * @param name      name of created tag, cannot be null
     * @param attribute attributes of created tag, can be null
     * @param tags      nested tags, can be null or zero-length array.
     * @return Tag object
     */
    public static Tag tag(final String name, @Nullable final Attribute attribute, final Tagable... tags) {
        return new Tag() {
            public void writeTo(final ContentHandler target) {
                startElement(target, attribute != null ? attribute.asAttributes() : EMPTY_ATTRIBUTES, name);
                for (final Tagable innerTagable : tags) {
                    innerTagable.asTag().writeTo(target);
                }
                endElement(target, name);
            }
        };
    }

    public static Tag tag(final String name, @Nullable final Attribute attribute, final String value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null for tag with name {" + name + "}");
        }
        return new Tag() {
            public void writeTo(final ContentHandler target) {
                startElement(target, attribute != null ? attribute.asAttributes() : EMPTY_ATTRIBUTES, name);
                characters(target, value.toCharArray());
                endElement(target, name);
            }
        };
    }

    private static void endElement(final ContentHandler target, final String name) {
        try {
            target.endElement("", name, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates Tag with given name and nested tags
     *
     * @param name name of Tag to create. Cannot be empty or null.
     * @param tags nested tags. Can be null or zero-length array
     * @return created xml tag
     */
    public static Tag tag(final String name, final Tagable... tags) {
        return tag(name, null, tags);
    }

    public static Tag tag(final String name, final List<? extends Tagable> tags) {
        return tag(name, null, tags);
    }


    /**
     * Returns Tag representation of given Tagable object.
     * More of convinience method, created to allow "tag(someObj)" instead of "someObj.asTag()"
     *
     * @param tagable Tagable object, cannot be null.
     * @return Tag representation of given tagable.
     */
    public static Tag tag(final Tagable tagable) {
        return tagable == null ? EMPTY_TAG : tagable.asTag();
    }

    /**
     * Creates tag with given name and value.
     *
     * @param name       name of Tag to create.
     * @param givenValue value of Tag to create.
     * @return created Tag object.
     */
    public static Tag tag(final String name, final String givenValue) {
        if (name == null) {
            throw new IllegalArgumentException("tagname cannot be null");
        }
        final String value = givenValue == null ? "" : givenValue;

        return new Tag() {
            public void writeTo(final ContentHandler target) {
                startElement(target, EMPTY_ATTRIBUTES, name);
                final char[] chars = givenValue != null ? givenValue.toCharArray() : new char[0];
                characters(target, chars);
                endElement(target, name);
            }
        };
    }

    private static void characters(final ContentHandler target, final char[] chars) {
        try {
            target.characters(chars, 0, chars.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void startElement(final ContentHandler target, final Attributes attrs, final String name) {
        try {
            target.startElement("", name, name, attrs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates array of Tag objects from given collection of Tagable objects.
     * Result can be used to pass in tag(String, Attribute, Tag...) method as a last parameter.
     *
     * @param tagables Collection of Tagable objects
     * @return Arrays of Tags.
     */
    public static Tag[] tags(final Collection<? extends Tagable> tagables) {
        final Tag[] out = new Tag[tagables.size()];
        int i = 0;
        for (final Tagable t : tagables) {
            out[i++] = t.asTag();
        }
        return out;
    }


    public static List<Tag> tagsList(final String name, final Collection<String> values) {
        return values == null
                ? Collections.<Tag>emptyList()
                : Arrays.asList(tags(name, values));
    }

    /**
     * Creates array of Tag objects from given name and collection of given values.
     * <pre>
     * Example: tags("foo", ["bar", "baz"]) => [tag("foo", "bar"), tag("foo", "baz")]
     * </pre>
     *
     * @param name   name for every Tag in output array.
     * @param values collection of values, one per each tag
     * @return array of Tag objects
     */
    public static Tag[] tags(final String name, final Collection<String> values) {
        if (values == null) {
            return EMPTY_TAG_ARRAY;
        } else {
            final Tag[] out = new Tag[values.size()];
            int i = 0;
            for (final String value : values) {
                out[i++] = tag(name, value);
            }
            return out;
        }
    }

    /**
     * Creates Attribute object from given name and value. value is xml-escaped.
     *
     * @param name  name of the attribute to create
     * @param value value of the attribute.
     * @return created Attribute object.
     */
    public static Attribute attribute(final String name, final Object value) {
        return attribute(name, String.valueOf(value));
    }

    public static Attribute attribute(final String name, final String value) {
        return new Attribute() {

            public Attributes asAttributes() {
                final AttributesImpl attrs = new AttributesImpl();
                return addAttribute(attrs, name, value);
            }
        };
    }

    private static Attributes addAttribute(final AttributesImpl attrs, final String name, final String value) {
        attrs.addAttribute("", name, name, "string", value);
        return attrs;
    }

    public static final Attribute EMPTY_ATTRS = emptyAttrs();

    public static Attribute emptyAttrs() {
        return new Attribute() {

            public Attributes asAttributes() {
                return EMPTY_ATTRIBUTES;
            }
        };
    }

    public abstract static class Attribute implements SaxAttributable {
        /**
         * Creates attribute by appending name-value pair to the given one.
         *
         * @param name  name of name-value pair to append
         * @param value value of name-value pair to append
         * @return new Attribute object, containing result of appending.
         */
        public Attribute and(final String name, final String value) {
            return new Attribute() {

                public Attributes asAttributes() {
                    final AttributesImpl attrs = (AttributesImpl) Attribute.this.asAttributes(); //ouch
                    addAttribute(attrs, name, value);
                    return attrs;
                }
            };
        }

        public Attribute and(final String name, final Object value) {
            return and(name, String.valueOf(value));
        }
    }

    public abstract static class Tag implements Tagable, SelfWriter { //just a convenience class, in case something is added

        public Tag asTag() {
            return this;
        }

    }

}