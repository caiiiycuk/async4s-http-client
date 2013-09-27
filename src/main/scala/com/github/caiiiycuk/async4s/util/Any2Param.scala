package com.github.caiiiycuk.async4s.util

object Any2Param {

  def any2Param(param: Any): Seq[String] = param match {
    case params: Seq[_] => 
      val join = 
        for { param <- params } yield any2Param(param)
      
      for {
        params <- join;
        param <- params
      } yield param
    case _ => 
      Seq(param.toString)
  }
  
}