package async4s.request

import async4s.response.ResponseType

case class RequestUrl(url: String, responseType: ResponseType) {
  
  def as(responseType: ResponseType) = {
    new RequestUrl(url, responseType)
  }
  
}