name := "MarsExploration"

import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.felicissimo"
ThisBuild / organizationName := "felicissimo"

scalacOptions += "-deprecation"

lazy val root = (project in file("."))
  .settings(
    mainClass in assembly := Some("com.felicissimo.api.MarsExploration"),
    assemblyJarName in assembly := "mars-exploration.jar",
    assemblyMergeStrategy in assembly := {
      case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
      case x => MergeStrategy.defaultMergeStrategy(x)
    },
    name := "mars-exploration-api",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.typesafe.akka" %% "akka-http"   % "10.1.9",
      "com.typesafe.akka" %% "akka-stream" % "2.5.23",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.9",
      "pl.iterators" %% "kebs-spray-json" % "1.6.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
      "org.scalactic" %% "scalactic" % "3.0.8",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
