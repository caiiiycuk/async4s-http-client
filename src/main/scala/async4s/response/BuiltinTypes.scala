package async4s.response

import com.ning.http.client.Response

class STRING extends ResponseType {
  type T = String

  def r2T(response: Response): T = {
    response.getResponseBody()
  }
}

class BYTES extends ResponseType {
  type T = Array[Byte]

  def r2T(response: Response): T = {
    response.getResponseBodyAsBytes()
  }
}