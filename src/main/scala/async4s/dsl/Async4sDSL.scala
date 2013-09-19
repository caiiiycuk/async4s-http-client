package async4s.dsl

import com.ning.http.client.AsyncHttpClient
import async4s.request.RequestUrl
import scala.concurrent._
import scala.util.Success
import async4s.request.RequestUrl
import scala.util.Success
import scala.util.Failure
import async4s.request.RequestUrl
import async4s.request.RequestUrl

object Async4sDSL {
  import scala.concurrent.duration._

  object STRING extends async4s.response.STRING
  object BYTES extends async4s.response.BYTES
  object EMPTY

  implicit def string2RequestUrl(url: String) =
    new RequestUrl(url, STRING)

  private def _get(r: RequestUrl)(implicit client: AsyncHttpClient, ec: ExecutionContext) =
    future { client.prepareGet(r.url).execute.get }

  def async_get(r: RequestUrl)(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[r.responseType.T] =
    for (rr <- _get(r)) yield r.responseType.r2T(rr)

  def async_get(r1: RequestUrl, r2: RequestUrl)(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[(r1.responseType.T, r2.responseType.T)] =
    for (rr1 <- _get(r1); rr2 <- _get(r2)) yield (r1.responseType.r2T(rr1), r2.responseType.r2T(rr2))

  def get(r: RequestUrl)(implicit client: AsyncHttpClient, ec: ExecutionContext): r.responseType.T =
    unwrap(async_get(r))

  def get(r1: RequestUrl, r2: RequestUrl)(implicit client: AsyncHttpClient, ec: ExecutionContext): (r1.responseType.T, r2.responseType.T) =
    unwrap(async_get(r1, r2))

  private def unwrap[T](f: Future[T])(implicit ec: ExecutionContext): T = {
    Await.result(f, Duration.Inf)
  }

}