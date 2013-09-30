name := "async4s-http-client"

version := "0.3-SNAPSHOT"

scalaVersion := "2.10.2"

organization := "com.github.caiiiycuk"


resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

libraryDependencies += "com.ning" % "async-http-client" % "1.7.9" % "compile"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

scalacOptions ++= Seq("-unchecked", "-deprecation")

// publishing

pgpSecretRing := file("/var/async4s-private.asc")

pgpPublicRing := file("/var/async4s.asc")

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials("Sonatype Nexus Repository Manager",
                           "oss.sonatype.org",
                           "",
                           "")