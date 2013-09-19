package async4s

import async4s.dsl.Async4sDSL
import async4s.engine.Async4sEngine
import scala.concurrent.ExecutionContext
import async4s.response.BYTES

object DslApp extends App {
  
  import Async4sDSL._
  import Async4sEngine._
  
  val (google, yandex) = 
    get(
      "http://google.com" as BYTES,
      "http://ya.ru" as STRING 
    )

//  println(google.length)
//  println(yandex)

  println("Well done!")

}