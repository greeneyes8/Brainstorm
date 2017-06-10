
enablePlugins(AndroidApp)
android.useSupportVectors

versionCode := Some(1)

instrumentTestRunner :=
  "android.support.test.runner.AndroidJUnitRunner"

platformTarget := "android-25"

javacOptions in Compile ++= "-source" :: "1.7" :: "-target" :: "1.7" :: Nil

libraryDependencies ++=
  "com.android.support" % "appcompat-v7" % "25.3.1" ::
  "com.android.support" % "design" % "25.3.1" ::
  "com.android.support.test" % "runner" % "0.5" % "androidTest" ::
  "com.android.support.test.espresso" % "espresso-core" % "2.2.2" % "androidTest" ::
  Nil

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"  % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1"  % Test

resolvers += Resolver.jcenterRepo
//libraryDependencies += "org.giwi" % "android-network-graph" % "0.0.1"
libraryDependencies += "jp.kai" % "forcelayout" % "1.0.9"
