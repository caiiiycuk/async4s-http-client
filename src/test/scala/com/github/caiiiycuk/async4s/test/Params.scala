package com.github.caiiiycuk.async4s.test

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.github.caiiiycuk.async4s.dsl.Async4sDSL
import com.github.caiiiycuk.async4s.impl.Async4sTestClient
import com.github.caiiiycuk.async4s.request.RequestUrl
import com.github.caiiiycuk.async4s.request.RequestUrl
import com.github.caiiiycuk.async4s.util.Any2Param

class Params extends FlatSpec with ShouldMatchers {

  import Async4sDSL._
  import Async4sTestClient._

  behavior of "Params dsl"

  it should "convert pairs to params" in {
    val x: RequestUrl[String] =
      "http://url" ~ 
      	("user" -> "caiiiycuk") ~ 
      	("token" -> "abcdedwqsdq") ~ 
      	("array" -> Seq(1, 2, 3))
    
    val first = x.params(0)
    val second = x.params(1)
    val third = x.params(2)
      
    first.key should equal("user")
    second.key should equal("token")
    third.key should equal("array")
    
    Any2Param.any2Param(first.value) should equal(Seq("caiiiycuk"))
    Any2Param.any2Param(second.value) should equal(Seq("abcdedwqsdq"))
    Any2Param.any2Param(third.value) should equal(Seq("1", "2", "3"))
  }
  
}