lazy val commonSettings = Seq(
  organization := "lack.of",
  version := "0.0.1-alpha",
  scalaVersion := "2.11.8"
)

lazy val core = (project in file("core")).settings(commonSettings)

lazy val android = (project in file("android")).dependsOn(core).settings(commonSettings)

