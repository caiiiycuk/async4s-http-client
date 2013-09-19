name := "async4s-http-client"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.2"

organization := "async4s"


resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

libraryDependencies += "com.ning" % "async-http-client" % "1.7.9" % "compile"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

scalacOptions ++= Seq("-unchecked", "-deprecation")  
