package net.sf.xfresh.core;

import net.sf.xfresh.core.impl.SimpleInternalRequest;
import net.sf.xfresh.core.impl.SimpleInternalResponse;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import java.io.ByteArrayOutputStream;

/**
 * Date: 20.04.2007
 * Time: 17:50:41
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class YaletProcessorTest extends AbstractDependencyInjectionSpringContextTests {
    private static final String TEST_CONTENT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<page><a>тест</a><data id=\"addTestInfo\"/></page>";
    private static final String TEST_TRANSFORMED_CONTENT = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
            "<title>Тест</title></head><body><h1>Проверка111</h1></body></html>";
    private YaletProcessor yaletProcessor;
    private ByteArrayOutputStream baos;
    private SimpleInternalResponse response;
    private SimpleInternalRequest request;

    public void setYaletProcessor(final YaletProcessor yaletProcessor) {
        this.yaletProcessor = yaletProcessor;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                "classpath:test-app-context.xml"
        };
    }

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();

        baos = new ByteArrayOutputStream();
        response = new SimpleInternalResponse(null);
        response.setOutputStream(baos);
    }

    public void testProcessFile() throws Exception {
        request = new SimpleInternalRequest(null, "./core/src/test/test.xml");
        request.setNeedTransform(false);
        yaletProcessor.process(request, response, new RedirHandler(null));
        assertEquals(TEST_CONTENT,
                new String(baos.toByteArray()).trim());
    }

    public void testTransformFile() throws Exception {
        request = new SimpleInternalRequest(null, "./core/src/test/test.xml");

        yaletProcessor.process(request, response, new RedirHandler(null));
        assertEquals(TEST_TRANSFORMED_CONTENT.toLowerCase(),
                new String(baos.toByteArray()).trim().toLowerCase());
    }

    public void testTransformXFile() throws Exception {
        request = new SimpleInternalRequest(null, "./core/src/test/xtest.xml");

        yaletProcessor.process(request, response, new RedirHandler(null));
        assertEquals(TEST_CONTENT,
                new String(baos.toByteArray()).trim());
    }
}
