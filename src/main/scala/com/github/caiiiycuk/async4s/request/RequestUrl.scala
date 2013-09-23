package com.github.caiiiycuk.async4s.request

import com.ning.http.client.Response
import com.github.caiiiycuk.async4s.response.ResponseType

case class RequestUrl[T](url: String, rType: ResponseType[T]) {
  
  def as[T2](rType: ResponseType[T2]) = {
    new RequestUrl[T2](url, rType)
  }
  
  def r2T(response: Response): T = rType.r2T(response) 
  
}