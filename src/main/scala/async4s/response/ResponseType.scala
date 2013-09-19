package async4s.response

import com.ning.http.client.Response

trait ResponseType[T] {
  def r2T(response: Response): T
}