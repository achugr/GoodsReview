var testValue = request.getCookie("test");
writer.write(testValue);
var cookies = request.getCookies();
cookies.put("test", "-1")
response.setCookies(cookies);