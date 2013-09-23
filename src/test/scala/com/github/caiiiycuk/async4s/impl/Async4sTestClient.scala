package com.github.caiiiycuk.async4s.impl

import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.AsyncHttpClientConfig
import scala.concurrent.ExecutionContext

object Async4sTestClient {

  private val configBuilder = new AsyncHttpClientConfig.Builder()

  configBuilder.setAllowPoolingConnection(true)
  configBuilder.setMaximumConnectionsTotal(100)
  configBuilder.setConnectionTimeoutInMs(15000)
  configBuilder.setRequestTimeoutInMs(15000)
  configBuilder.setFollowRedirects(true)

  implicit val ec = ExecutionContext.Implicits.global
  implicit val httpClient = new AsyncHttpClient()

  def close() = httpClient.close()
  
}