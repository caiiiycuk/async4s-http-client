async4s-http-client
===================
A scala dsl on top of java [async-http-client](https://github.com/AsyncHttpClient/async-http-client)

usage
=====
Easiest way to get full content by url:

```
import Async4sDSL._

implicit val ec = ExecutionContext.Implicits.global
implicit val httpClient = new AsyncHttpClient()

val (google, bing, yahoo) =
  get("http://google.com", "http://www.bing.com", "http://www.yahoo.com")
```

Also you can use response type modifiers (```STRING```, ```BYTES``` or ```STREAM```):

```
import Async4sDSL._

implicit val ec = ExecutionContext.Implicits.global
implicit val httpClient = new AsyncHttpClient()

val (google, bing, yahoo) =
  get("http://google.com" AS STRING, 
      "http://www.bing.com" AS BYTES, 
      "http://www.yahoo.com" AS STREAM)
```

Method ```get``` take up to five urls. It will download content in parallel mode and return 
when all contents are downloaded. Also you can use async_get to download content in asynchronous mode.
