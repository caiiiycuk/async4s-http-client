package com.github.caiiiycuk.async4s.response

import com.ning.http.client.Response
import java.io.InputStream

class RAW extends ResponseType[Response] {
  def r2T(response: Response): Response = {
    response
  }
}

class STRING extends ResponseType[String] {
  def r2T(response: Response): String = {
    response.getResponseBody()
  }
}

class BYTES extends ResponseType[Array[Byte]] {
  def r2T(response: Response): Array[Byte] = {
    response.getResponseBodyAsBytes()
  }
}

class STREAM extends ResponseType[InputStream] {
  def r2T(response: Response): InputStream = {
    response.getResponseBodyAsStream()
  }  
}