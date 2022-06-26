ThisBuild / scalaVersion := "3.1.2"
ThisBuild / versionScheme := Some("early-semver")
ThisBuild / githubWorkflowPublishTargetBranches := Seq()

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect" % "3.3.12",
    // "org.typelevel" %% "cats-mtl" % "1.2.1",
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"
  )
)

val shared = project.settings(
  commonSettings,
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.0.1",
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "1.0.1"
  )
)

val server = project
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "1.0.1",
      "org.http4s" %% "http4s-dsl" % "0.23.13",
      "org.http4s" %% "http4s-ember-server" % "0.23.13",
      "ch.qos.logback" % "logback-classic" % "1.2.11"
    ),
  )
  .dependsOn(shared)

val client = project.settings(commonSettings).dependsOn(shared)

val root = project
  .in(file("."))
  .settings(publish := {}, publish / skip := true)
  .aggregate(server, client, shared)
