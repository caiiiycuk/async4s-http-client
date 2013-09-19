package async4s.request

import async4s.response.ResponseType
import com.ning.http.client.Response

case class RequestUrl[T](url: String, rType: ResponseType[T]) {
  
  def as[T2](rType: ResponseType[T2]) = {
    new RequestUrl[T2](url, rType)
  }
  
  def r2T(response: Response): T = rType.r2T(response) 
  
}