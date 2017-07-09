name := """play-scals-users-template"""
organization := "bd"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.1.1-P25-B3"
)

resolvers += "SQLite-JDBC Repository" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
jdbc,
"com.typesafe.play" %% "anorm" % "2.5.1",
"org.xerial" % "sqlite-jdbc" % "3.19.3"
)

//Enable evolutions
//https://www.playframework.com/documentation/2.5.x/Evolutions
libraryDependencies += evolutions

// https://mvnrepository.com/artifact/org.mindrot/jbcrypt
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"
