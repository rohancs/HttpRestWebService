# HttpRestWebService

Instructions:
  
  1. Set Cache Settings in ~\Cache\src\main\resources\cache.properties
  2. Run Main.java to launch Cache, MBean Server, Http Server
  3. Open Web Browser to test:
  <br />
  GET Key Value from Cache => http://localhost:8008/Cache/get/foo
  <br />
  PUT Key/Value (foo/bar) in Cache => http://localhost:8008/Cache/set/foo/bar
  <br />
  GET Cache  Size => http://localhost:8008/Cache/size

  4. Optionally impement load method on CacheServer to load cache when there is a cache miss.
