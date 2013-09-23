package com.github.caiiiycuk.async4s.dsl

import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.future
import com.ning.http.client.AsyncHttpClient
import com.github.caiiiycuk.async4s.request.RequestUrl

object Async4sDSL {
  import scala.concurrent.duration._

  object STRING extends com.github.caiiiycuk.async4s.response.STRING
  object BYTES extends com.github.caiiiycuk.async4s.response.BYTES
  object STREAM extends com.github.caiiiycuk.async4s.response.STREAM

  implicit def string2RequestUrl(url: String): RequestUrl[String] =
    new RequestUrl(url, STRING)

  private def _get[T](r: RequestUrl[T])(implicit client: AsyncHttpClient, ec: ExecutionContext) =
    future { r.r2T(client.prepareGet(r.url).execute.get) }

  def async_get[T](r: RequestUrl[T])(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[T] =
    for (rr <- _get(r)) yield rr

  def async_get[T1, T2](r1: RequestUrl[T1], r2: RequestUrl[T2])(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[(T1, T2)] =
    for (rr1 <- _get(r1); rr2 <- _get(r2)) yield (rr1, rr2)

  def async_get[T1, T2, T3](r1: RequestUrl[T1], r2: RequestUrl[T2], r3: RequestUrl[T3])(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[(T1, T2, T3)] =
    for (rr1 <- _get(r1); rr2 <- _get(r2); rr3 <- _get(r3)) yield (rr1, rr2, rr3)

  def async_get[T1, T2, T3, T4](r1: RequestUrl[T1], r2: RequestUrl[T2], r3: RequestUrl[T3], r4: RequestUrl[T4])(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[(T1, T2, T3, T4)] =
    for (rr1 <- _get(r1); rr2 <- _get(r2); rr3 <- _get(r3); rr4 <- _get(r4)) yield (rr1, rr2, rr3, rr4)

  def async_get[T1, T2, T3, T4, T5](r1: RequestUrl[T1], r2: RequestUrl[T2], r3: RequestUrl[T3], r4: RequestUrl[T4], r5: RequestUrl[T5])(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[(T1, T2, T3, T4, T5)] =
    for (rr1 <- _get(r1); rr2 <- _get(r2); rr3 <- _get(r3); rr4 <- _get(r4); rr5 <- _get(r5)) yield (rr1, rr2, rr3, rr4, rr5)

  def get[T](r: RequestUrl[T])(implicit client: AsyncHttpClient, ec: ExecutionContext): T =
    await(async_get(r))

  def get[T1, T2](r1: RequestUrl[T1], r2: RequestUrl[T2])(implicit client: AsyncHttpClient, ec: ExecutionContext): (T1, T2) =
    await(async_get(r1, r2))

  def get[T1, T2, T3](r1: RequestUrl[T1], r2: RequestUrl[T2], r3: RequestUrl[T3])(implicit client: AsyncHttpClient, ec: ExecutionContext): (T1, T2, T3) =
    await(async_get(r1, r2, r3))

  def get[T1, T2, T3, T4](r1: RequestUrl[T1], r2: RequestUrl[T2], r3: RequestUrl[T3], r4: RequestUrl[T4])(implicit client: AsyncHttpClient, ec: ExecutionContext): (T1, T2, T3, T4) =
    await(async_get(r1, r2, r3, r4))

  def get[T1, T2, T3, T4, T5](r1: RequestUrl[T1], r2: RequestUrl[T2], r3: RequestUrl[T3], r4: RequestUrl[T4], r5: RequestUrl[T5])(implicit client: AsyncHttpClient, ec: ExecutionContext): (T1, T2, T3, T4, T5) =
    await(async_get(r1, r2, r3, r4, r5))

  def async_get[T](rs: Seq[RequestUrl[T]])(implicit client: AsyncHttpClient, ec: ExecutionContext): Future[Seq[T]] =
    Future.sequence(for (r <- rs) yield _get(r))

  def get[T](r: Seq[RequestUrl[T]])(implicit client: AsyncHttpClient, ec: ExecutionContext): Seq[T] =
    await(async_get(r))

  private def await[T](f: Future[T])(implicit ec: ExecutionContext): T = {
    Await.result(f, Duration.Inf)
  }

}