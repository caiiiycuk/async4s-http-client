package async4s.response

import com.ning.http.client.Response

trait ResponseType {
  type T
  
  def r2T(response: Response): T
}