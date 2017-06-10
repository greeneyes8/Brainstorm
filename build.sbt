lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val core = (project in file("core")).settings(commonSettings)

lazy val android = (project in file("android")).dependsOn(core).settings(commonSettings)

resolvers += Resolver.jcenterRepo
libraryDependencies += "jp.kai" % "forcelayout" % "1.0.9"
