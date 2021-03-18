import ReleaseTransformations._

lazy val V = _root_.scalafix.sbt.BuildInfo

lazy val commonSettings = Def.settings(
  List(
    organization := "com.github.xuwei-k",
    homepage := Some(url("https://github.com/xuwei-k/replace-symbol-literals")),
    licenses := Seq("MIT License" -> url("https://opensource.org/licenses/mit-license")),
    description := "scalafix rule for replace deprecated scala.Symbol literals",
    scalaVersion := V.scala212,
    addCompilerPlugin(scalafixSemanticdb),
    releaseCrossBuild := true,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      releaseStepCommandAndRemaining("+publishSigned"),
      setNextVersion,
      commitNextVersion,
      releaseStepCommand("sonatypeReleaseAll"),
      pushChanges
    ),
    pomExtra := (
      <developers>
        <developer>
          <id>xuwei-k</id>
          <name>Kenji Yoshida</name>
          <url>https://github.com/xuwei-k</url>
        </developer>
      </developers>
      <scm>
        <url>git@github.com:xuwei-k/replace-symbol-literals.git</url>
        <connection>scm:git:git@github.com:xuwei-k/replace-symbol-literals.git</connection>
      </scm>
    ),
    publishTo := sonatypePublishTo.value,
    (Compile / doc / scalacOptions) ++= {
      val hash = sys.process.Process("git rev-parse HEAD").lineStream_!.head
      Seq(
        "-sourcepath",
        (LocalRootProject / baseDirectory).value.getAbsolutePath,
        "-doc-source-url",
        s"https://github.com/xuwei-k/replace-symbol-literals/tree/${hash}€{FILE_PATH}.scala"
      )
    },
    scalacOptions ++= PartialFunction
      .condOpt(CrossVersion.partialVersion(scalaVersion.value)) {
        case Some((2, v)) if v >= 12 =>
          Seq(
            "-Ywarn-unused:imports",
          )
      }
      .toList
      .flatten,
    scalacOptions ++= PartialFunction
      .condOpt(CrossVersion.partialVersion(scalaVersion.value)) {
        case Some((2, v)) if v <= 12 =>
          Seq(
            "-Yno-adapted-args",
            "-Xfuture",
          )
      }
      .toList
      .flatten,
    scalacOptions ++= List(
      "-deprecation",
      "-unchecked",
      "-Yrangepos",
      "-P:semanticdb:synthetics:on"
    )
  )
)

commonSettings
publish / skip := true

lazy val rules = project.settings(
  commonSettings,
  name := "replace-symbol-literals",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion
)

lazy val input = project.settings(
  commonSettings,
  publish / skip := true
)

lazy val output = project.settings(
  commonSettings,
  publish / skip := true
)

lazy val tests = project
  .settings(
    commonSettings,
    publish / skip := true,
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full,
    (Compile / compile) :=
      (Compile / compile).dependsOn(input / Compile / compile).value,
    scalafixTestkitOutputSourceDirectories :=
      (output / Compile / sourceDirectories).value,
    scalafixTestkitInputSourceDirectories :=
      (input / Compile / sourceDirectories).value,
    scalafixTestkitInputClasspath :=
      (input / Compile / fullClasspath).value,
  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)
