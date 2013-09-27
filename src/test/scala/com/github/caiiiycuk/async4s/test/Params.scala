package com.github.caiiiycuk.async4s.test

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.github.caiiiycuk.async4s.dsl.Async4sDSL
import com.github.caiiiycuk.async4s.impl.Async4sTestClient
import com.github.caiiiycuk.async4s.request.RequestUrl

class Params extends FlatSpec with ShouldMatchers {

  import Async4sDSL._
  import Async4sTestClient._

  behavior of "Params dsl"

  it should "POST single url as string" in {
    val content = post("http://google.com" ~ ("" -> ""))
    content should include("Method Not Allowed")
  }

}