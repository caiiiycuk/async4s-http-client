package com.github.caiiiycuk.async4s.dsl

import com.ning.http.client.AsyncHttpClient
import com.github.caiiiycuk.async4s.request.RequestUrl
import com.github.caiiiycuk.async4s.util.Any2Param
import com.github.caiiiycuk.async4s.request.RequestParam
import com.ning.http.client.AsyncCompletionHandler
import com.ning.http.client.Response

object Async4sDSL {
  import scala.concurrent.duration._
  import Any2Param._

  object RAW extends com.github.caiiiycuk.async4s.response.RAW
  object STRING extends com.github.caiiiycuk.async4s.response.STRING
  object BYTES extends com.github.caiiiycuk.async4s.response.BYTES
  object STREAM extends com.github.caiiiycuk.async4s.response.STREAM

  implicit def string2RequestUrl(url: String): RequestUrl[String] =
    new RequestUrl(url, STRING)

  implicit def pair2RequestParam(pair: (String, Any)) =
    RequestParam(pair._1, pair._2)

  private def _get[T](r: RequestUrl[T])(implicit client: AsyncHttpClient) = {
    val prepared = client.prepareGet(r.url)

    for (
      r <- r.params;
      value <- any2Param(r.value)
    ) {
      prepared.addQueryParameter(r.key, value)
    }

    prepared.execute(new AsyncCompletionHandler[T] {
      def onCompleted(response: Response): T = {
        r.r2T(response)
      }
    })
  }

  private def _post[T](r: RequestUrl[T])(implicit client: AsyncHttpClient) = {
    val prepared = client.preparePost(r.url)
    for (
      r <- r.params;
      value <- any2Param(r.value)
    ) {
      prepared.addParameter(r.key, value)
    }

    prepared.execute(new AsyncCompletionHandler[T] {
      def onCompleted(response: Response): T = {
        r.r2T(response)
      }
    })
  }

  def async_get[T](r: RequestUrl[T])(implicit client: AsyncHttpClient) =
    _get(r)

  def async_get[T1](rs: Seq[RequestUrl[T1]])(implicit client: AsyncHttpClient) =
    for (r <- rs) yield _get(r)

  def async_post[T](r: RequestUrl[T])(implicit client: AsyncHttpClient) =
    _post(r)

  def async_post[T](rs: Seq[RequestUrl[T]])(implicit client: AsyncHttpClient) =
    for (r <- rs) yield _post(r)

  def get[T](r: RequestUrl[T])(implicit client: AsyncHttpClient) =
    async_get(r).get

  def get[T](rs: Seq[RequestUrl[T]])(implicit client: AsyncHttpClient) =
    for (r <- rs) yield async_get(r).get

  def post[T](r: RequestUrl[T])(implicit client: AsyncHttpClient) =
    async_post(r).get

  def post[T](rs: Seq[RequestUrl[T]])(implicit client: AsyncHttpClient) =
    for (r <- rs) yield async_post(r).get

}