package async4s.test

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import async4s.dsl.Async4sDSL
import async4s.impl.Async4sTestClient
import async4s.request.RequestUrl

class GET extends FlatSpec with ShouldMatchers {

  import Async4sDSL._
  import Async4sTestClient._

  behavior of "GET dsl"

  it should "GET single url as string" in {
    val content = get("http://google.com")
    content should include("google")
  }

  it should "GET single url as bytes" in {
    val bytes = get("http://google.com" as BYTES)
    bytes.length > (0)
  }

  it should "GET two urls" in {
    val (content, bytes) =
      get("http://google.com", "http://google.com" as BYTES)

    content should include("google")
    bytes.length > (0)
  }

  it should "GET three urls" in {
    val (google, bing, yahoo) =
      get("http://google.com", "http://www.bing.com", "http://www.yahoo.com")

    google should include("google")
    bing should include("bing")
    yahoo should include("yahoo")
  }

  it should "GET four urls" in {
    val (google, bing, yahoo, duckduckgo) =
      get("http://google.com",
        "http://www.bing.com",
        "http://www.yahoo.com",
        "https://duckduckgo.com/")

    google should include("google")
    bing should include("bing")
    yahoo should include("yahoo")
    duckduckgo should include("duckduckgo")
  }

  it should "GET five urls" in {
    val (google, bing, yahoo, duckduckgo, yandex) =
      get("http://google.com",
        "http://www.bing.com",
        "http://www.yahoo.com",
        "https://duckduckgo.com/",
        "http://www.yandex.ru")

    google should include("google")
    bing should include("bing")
    yahoo should include("yahoo")
    duckduckgo should include("duckduckgo")
    yandex should include("yandex")
  }

  it should "GET urls from seq" in {
    val responses = get(Seq[RequestUrl[String]](
      "http://google.com",
      "http://www.bing.com",
      "http://www.yahoo.com",
      "https://duckduckgo.com/",
      "http://www.yandex.ru"))

    responses(0) should include("google")
    responses(1) should include("bing")
    responses(2) should include("yahoo")
    responses(3) should include("duckduckgo")
    responses(4) should include("yandex")
  }

}