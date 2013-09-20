async4s-http-client
===================
A scala dsl on top of java [async-http-client](https://github.com/AsyncHttpClient/async-http-client)

GET
===

Easiest way to get full content by url:

```
    import Async4sDSL._

    implicit val ec = ExecutionContext.Implicits.global
    implicit val httpClient = new AsyncHttpClient()

    val (google, bing, yahoo) =
      get("http://google.com", "http://www.bing.com", "http://www.yahoo.com")
      
    // google should include("google")
    // bing should include("bing")
    // yahoo should include("yahoo")
```

Also you can use builtin response type modifiers (```STRING```, ```BYTES``` or ```STREAM```):

```
    import Async4sDSL._

    implicit val ec = ExecutionContext.Implicits.global
    implicit val httpClient = new AsyncHttpClient()

    val (google, bing, yahoo) =
      get("http://google.com" AS STRING, 
          "http://www.bing.com" AS BYTES, 
          "http://www.yahoo.com" AS STREAM)
    
    // type of google is String
    // type of bing is Array[Byte]
    // type if yahoo is InputStream
```

Method ```get``` take up to five urls. It will download content in parallel mode and return 
when all contents are downloaded. Also you can use async_get to download content in asynchronous mode 
(it will return Future[T]).

Also you can pass Seq of request to ```get``` method:

```
    import Async4sDSL._

    implicit val ec = ExecutionContext.Implicits.global
    implicit val httpClient = new AsyncHttpClient()

    val responses = get(Seq[RequestUrl[String]](
      "http://google.com",
      "http://www.bing.com",
      "http://www.yahoo.com",
      "https://duckduckgo.com/",
      "http://www.yandex.ru"))
      
    // type of responses is Seq[String]
    // because we pass Seq[RequestUrl[String]]
```

Custom response parser
======================

You can define own response types throught subclassing from ```ResponseType[T]```:

```
    import async4s.response.ResponseType
    import com.ning.http.client.Response
    import Async4sDSL._
    
    implicit val ec = ExecutionContext.Implicits.global
    implicit val httpClient = new AsyncHttpClient()

    case class MyType(body: String)

    object MY_TYPE extends ResponseType[MyType] {
      def r2T(response: Response): MyType = {
        MyType(response.getResponseBody)
      }
    }

    val (response) =
      get("http://google.com" as MY_TYPE)

    // type of response is MyType
    // response.body should include("google")
```
