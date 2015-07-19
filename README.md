# HttpRestWebService

##Instructions:
  
  1. Set Cache Settings in ~\Cache\src\main\resources\cache.properties

  Launching Cache Server:
  1. Run "CacheServerMain.java" with following JVM options:
      -Dcom.sun.management.jmxremote.port=8009
      -Dcom.sun.management.jmxremote.authenticate=false
      -Dcom.sun.management.jmxremote.ssl=false

  2. The Service should start on port:8009 (default).

  Launching Http Rest Service:
  1. Run "HttpClientMain.java"
  2. This launches a REST web service end point at port 8008

  NOTE:
  Optionally implement load method on "CacheServer.java" to load cache when there is a cache miss.

## Test Cases:
  Open Web Browser:
  <br />
  GET Test=> http://localhost:8008/Cache/get/foo
  <br />
  PUT Test => http://localhost:8008/Cache/set/foo/bar
  <br />
  Cache Size => http://localhost:8008/Cache/size

