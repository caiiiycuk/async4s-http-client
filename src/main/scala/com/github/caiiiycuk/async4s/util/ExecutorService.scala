package com.github.caiiiycuk.async4s.util

import scala.concurrent.ExecutionContext
import java.util.concurrent.TimeUnit
import java.util.concurrent.AbstractExecutorService

object ExecutorService {
  def apply(ec: ExecutionContext) = 
    new ExecutorService(ec)
}

class ExecutorService(ec: ExecutionContext) extends AbstractExecutorService {
  override def execute(r: Runnable) = 
    ec.execute(r)

  override def isTerminated(): Boolean = 
    false

  override def isShutdown(): Boolean = 
    false

  override def shutdownNow(): java.util.List[Runnable] = 
    throw new UnsupportedOperationException("Unable to shutdown ExecutorService")

  override def shutdown(): Unit = 
    throw new UnsupportedOperationException("Unable to shutdown ExecutorService")

  override def awaitTermination(timeout: Long, unit: TimeUnit): Boolean = 
    throw new UnsupportedOperationException()
}