lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val core = (project in file("core"))

lazy val android = (project in file("android")).dependsOn(core)
