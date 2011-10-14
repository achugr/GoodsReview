package net.sf.xfresh.core.sax;

import net.sf.xfresh.core.SaxHandler;
import net.sf.xfresh.core.SaxWriter;
import net.sf.xfresh.core.SelfSaxWriter;
import net.sf.xfresh.util.XmlUtil;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;

/**
 * User: darl (darl@yandex-team.ru)
 * Date: 3/21/11 8:37 PM
 */
public class MapSelfSaxWriter<K, V> implements SelfSaxWriter {
    private final Map<K, V> map;
    private final SaxWriter<Map.Entry<K, V>> writer;

    public MapSelfSaxWriter(final Map<K, V> map, final SaxWriter<Map.Entry<K, V>> writer) {
        this.map = new HashMap<K, V>(map);
        this.writer = writer;
    }

    public void writeTo(final SaxHandler saxHandler) throws SAXException {
        XmlUtil.start(saxHandler.getContentHandler(), "map");
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            writer.write(entry, saxHandler);
        }
        XmlUtil.end(saxHandler.getContentHandler(), "map");
    }
}
