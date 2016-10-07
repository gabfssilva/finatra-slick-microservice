import sbt.Keys._

name := "finatra-slick-microservice"
version := "1.0.0"
scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Twitter Maven" at "https://maven.twttr.com"
)

// assembly for packaging as single jar
assemblyMergeStrategy in assembly := {
  case "BUILD" => MergeStrategy.discard
  case other => MergeStrategy.defaultMergeStrategy(other)
}

assemblyJarName in assembly := s"${name.value}.jar"

lazy val versions = new {
  val finatra = "2.4.0"
  val slick = "3.1.1"
}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.twitter" %% "finatra-slf4j" % versions.finatra,
  "com.twitter" %% "finatra-jackson" % versions.finatra,

  "com.twitter" %% "inject-server" % versions.finatra,
  "com.twitter" %% "inject-app" % versions.finatra,
  "com.twitter" %% "inject-modules" % versions.finatra,

  "com.typesafe.slick" %% "slick" % versions.slick,

  "com.h2database" % "h2" % "1.4.181",
  "com.mchange" % "c3p0" % "0.9.5.1",

  "org.scala-lang.modules" %% "scala-async" % "0.9.5",

  "com.twitter" %% "util-collection" % "6.36.0",

  "com.twitter" %% "bijection-util" % "0.9.2",
  "com.twitter" %% "bijection-core" % "0.9.2",

  "ch.qos.logback" % "logback-classic" % "0.9.28",

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test",

  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",

  //Test dependencies for finatra
  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",

  "com.google.inject.extensions" % "guice-testlib" % "4.1.0" % "test",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.specs2" %% "specs2" % "2.3.12" % "test",
  "junit" % "junit" % "4.12" % "test"
)


