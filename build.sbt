ThisBuild / scalaVersion := "3.1.2"


val commonSettings = Seq(
    scalaVersion := "3.1.2",
    libraryDependencies ++= Seq(
        "org.typelevel" %% "cats-effect" % "3.3.12",
        // "org.typelevel" %% "cats-mtl" % "1.2.1",
        "org.typelevel" %% "munit-cats-effect-3" % "0.3.0"
    )
)

val shared = project.settings(commonSettings)

val server = project.settings(commonSettings).dependsOn(shared)

val client = project.settings(commonSettings).dependsOn(shared)

val root = project
.in(file("."))
.settings(publish := {})
.aggregate(server, client, shared)