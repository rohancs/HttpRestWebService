# HttpRestWebService

##Instructions:
  
  Set Cache Settings in ~\Cache\src\main\resources\cache.properties

### Launching Cache Server:
  Run "CacheServerMain.java" with following JVM options:

      -Dcom.sun.management.jmxremote.port=8009
      -Dcom.sun.management.jmxremote.authenticate=false
      -Dcom.sun.management.jmxremote.ssl=false

  The Service should start on port:8009 (default).

### Launching Http Rest Service:
  Run "HttpClientMain.java"
  This launches a REST web service end point at port 8008

      NOTE: Optionally implement load method on "CacheServer.java" to load cache when there is a cache miss.

## Test Cases:
  Open Web Browser:
  Get value for key "foo"

      http://localhost:8008/Cache/get/foo

  Put value "bar" for key "foo"

      http://localhost:8008/Cache/set/foo/bar

  Get Cache Size

      http://localhost:8008/Cache/size

