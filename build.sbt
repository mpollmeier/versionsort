name := "versionsort"
organization := "com.michaelpollmeier"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % Test

// make this a pure java build
autoScalaLibrary := false
crossPaths := false

scalacOptions ++= Seq("-release", "8")

Compile/compile/javacOptions ++= Seq(
  "-g", // debug symbols
  "--release", "8"
)

homepage := Some(url("https://github.com/mpollmeier/versionsort"))
scmInfo := Some(ScmInfo(
  url("https://github.com/mpollmeier/versionsort"),
  "scm:git@github.com:mpollmeier/versionsort.git"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
developers := List(
  Developer("mpollmeier", "Michael Pollmeier", "michael@michaelpollmeier.com", url("https://michaelpollmeier.com"))
)
publishTo := sonatypePublishToBundle.value
