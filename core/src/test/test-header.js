var testValue = request.getHeader("test");
writer.write(testValue);
response.setHeader("test", -1);