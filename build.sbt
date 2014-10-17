libraryDependencies += "org.scala-stm" %% "scala-stm" % "0.7"

libraryDependencies += "com.qifun" %% "json-stream" % "0.2.1" % "provided"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % Test

libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23"

libraryDependencies += "com.dongxiguo" %% "zero-log" % "0.3.6" % Test

javacOptions in Compile in compile += "-Xlint:-deprecation"

crossScalaVersions := Seq("2.10.4", "2.11.2")

organization := "com.qifun"

name := "json-stream-bson"

version := "0.1.1-SNAPSHOT"

homepage := Some(url(s"https://github.com/qifun/${name.value}"))

startYear := Some(2014)

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

publishTo <<= (isSnapshot) { isSnapshot: Boolean =>
  if (isSnapshot)
    Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
    Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

scmInfo := Some(ScmInfo(
  url(s"https://github.com/qifun/${name.value}"),
  s"scm:git:git://github.com/qifun/${name.value}.git",
  Some(s"scm:git:git@github.com:qifun/${name.value}.git")))

pomExtra :=
  <developers>
    <developer>
      <id>Atry</id>
      <name>杨博 (Yang Bo)</name>
      <timezone>+8</timezone>
      <email>pop.atry@gmail.com</email>
    </developer>
    <developer>
      <id>zxiy</id>
      <name>张修羽 (Zhang Xiuyu)</name>
      <timezone>+8</timezone>
      <email>95850845@qq.com</email>
    </developer>
    <developer>
      <id>chank</id>
      <name>方里权 (Fang Liquan)</name>
      <timezone>+8</timezone>
      <email>fangliquan@qq.com</email>
    </developer>
  </developers>


