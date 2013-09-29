async4s-http-client
===================
A scala dsl on top of java [async-http-client](https://github.com/AsyncHttpClient/async-http-client)

[Using with sbt](#using-with-sbt)  
[GET requests](#get)  
[POST requests](#post)  
[Working with parameters](#working-with-parameters)  
[Custom response parsers](#custom-response-parsers)  
[Getting raw response](#getting-raw-response)  
[Configuring AsyncHttpClient](#configuring-asynchttpclient)

Using with SBT
==============

Add dependency to build.sbt:
```
resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases")
                
libraryDependencies += "com.github.caiiiycuk" %% "async4s-http-client" % "0.2-SNAPSHOT" % "compile"
```

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
    
    httpClient.close
```

Also you can use builtin response type modifiers (```STRING```, ```BYTES```, ```STREAM``` or ```RAW```):

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
    
    httpClient.close
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
    
    httpClient.close
```

POST
====

You can send post requests absolute in the same way as get, through methods ```post``` and ```async post```.
```
    import Async4sDSL._

    implicit val ec = ExecutionContext.Implicits.global
    implicit val httpClient = new AsyncHttpClient()

    val response =
      post("http://url")
    
    httpClient.close
```

Working with parameters
=======================

You can specify request parameters throught operator ```~```:

```
    val content =
        post("http://url"
            ~ ("user" -> "caiiiycuk")
            ~ ("token" -> "abcdedwqsdq")
            ~ ("array" -> Seq(1, 2, 3)))
```

When you use POST method request parameters will be passed in request body. When you use
GET method request parameters will be passed in request uri (for this example uri will be 
"http://url?user=caiiiycuk&token=abcdedwqsdq&array=1&array=2&array=3").

Custom response parsers
=======================

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
    
    httpClient.close
```

Getting raw response
====================

You can use ```RAW``` response type to get ```com.ning.http.client.Response```:

```
    import Async4sDSL._

    implicit val ec = ExecutionContext.Implicits.global
    implicit val httpClient = new AsyncHttpClient()

    val google =
      get("http://google.com" as RAW)
      
    // google.getContentType should equal ("text/html; charset=UTF-8")
    // google.getResponseBody should include("google")
    
    httpClient.close
```

Configuring AsyncHttpClient
===========================

Usually only one http client needed for whole application. For this case we should use singleton object with instance of ```AsyncHttpClient```:

```
    private object HttpClient {
      private val configBuilder = new AsyncHttpClientConfig.Builder()

      configBuilder.setAllowPoolingConnection(true)
      configBuilder.setMaximumConnectionsTotal(100)
      configBuilder.setConnectionTimeoutInMs(15000)
      configBuilder.setRequestTimeoutInMs(15000)
      configBuilder.setFollowRedirects(true)

      val client = new AsyncHttpClient(configBuilder.build())
      def close = client.close
    }
```

Now we can define a trait with implicit variable of ExecutionContext and AsyncHttpClient:
```
    trait WS {
      implicit val ec = ExecutionContext.Implicits.global
      implicit val httpClient = HttpClient.client
    }
```

After that we can simply write:
```
    object Get extends App with WS {
      import Async4sDSL._

      val (google, bing, yahoo) =
        get("http://www.google.com", "http://www.bing.com", "http://www.yahoo.com")

      // google should include("google")
      // bing should include("bing")
      // yahoo should include("yahoo")

      HttpClient.close
    }
```

