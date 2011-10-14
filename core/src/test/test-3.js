//var result1 = httpLoader.loadInBackground("http://localhost:33333/test.xml?_ox", 300, cache("test_key", 300));
var loaded = httpLoader.load("http://localhost:33333/test-js-1.xml?_ox", 100);
if (!   loaded.isEmpty()) {
    var i = loaded.evaluateToString("/page/out-example[1]", "ERROR");
    //write(escape(result1));
} else {
    // timeout handling
}
writer.write(i);
//write(1);
//write(2);
writer.write("\n\nэто кусок ноды:\n");
writer.writeNode(loaded.evaluateToNode("/page/out-example[2]"));
writer.write("\n\nэто загруженный документ:\n");
writer.writeLoaded(loaded);
writer.write("\n<a>а так не работает :) -- теги эскейпятся</a>");
//httpLoader.wait(result1);
//if ("aaa" = request.getParameter("do_send")) {
//    sendMessage("bla-bla");
//}
