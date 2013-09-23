package com.github.caiiiycuk.async4s.test

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import com.github.caiiiycuk.async4s.dsl.Async4sDSL
import com.github.caiiiycuk.async4s.impl.Async4sTestClient

class ResponseTypeTest extends FlatSpec with ShouldMatchers {

  import Async4sTestClient._

  behavior of "response type"

  it should "work with custom response types" in {
    import com.github.caiiiycuk.async4s.response.ResponseType
    import com.ning.http.client.Response
    import Async4sDSL._

    case class MyType(body: String)

    object MY_TYPE extends ResponseType[MyType] {
      def r2T(response: Response): MyType = {
        MyType(response.getResponseBody)
      }
    }

    val (response) =
      get("http://google.com" as MY_TYPE)

    response.body should include("google")
  }

}