package com.github.caiiiycuk.async4s.request

import com.ning.http.client.Response
import com.github.caiiiycuk.async4s.response.ResponseType
import java.util.HashMap
import scala.collection.mutable.ListBuffer

case class RequestParam(key: String, value: Any)

case class RequestUrl[T](url: String, rType: ResponseType[T], 
    params: ListBuffer[RequestParam] = new ListBuffer[RequestParam]) {
  
  def as[T2](rType: ResponseType[T2]) = {
    new RequestUrl[T2](url, rType, params)
  }
  
  def ~(param: RequestParam) = {
    params += param
    this
  }
  
  def ~(param: (String, Any)) = {
    params += RequestParam(param._1, param._2)
    this
  }
  
  def r2T(response: Response): T = rType.r2T(response)
  
}