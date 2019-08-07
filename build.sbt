import ReleaseTransformations._

lazy val V = _root_.scalafix.sbt.BuildInfo

lazy val commonSettings = Def.settings(
  List(
    organization := "com.github.xuwei-k",
    homepage := Some(url("https://github.com/xuwei-k/replace-symbol-literals")),
    licenses := Seq("MIT License" -> url("https://opensource.org/licenses/mit-license")),
    scalaVersion := V.scala212,
    addCompilerPlugin(scalafixSemanticdb),
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      releaseStepCommandAndRemaining("publishSigned"),
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
    scalacOptions ++= List(
      "-Yrangepos",
      "-P:semanticdb:synthetics:on"
    )
  )
)

commonSettings
skip in publish := true

lazy val rules = project.settings(
  commonSettings,
  name := "replace-symbol-literals",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion
)

lazy val input = project.settings(
  commonSettings,
  skip in publish := true
)

lazy val output = project.settings(
  commonSettings,
  skip in publish := true
)

lazy val tests = project
  .settings(
    commonSettings,
    skip in publish := true,
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full,
    compile in Compile :=
      compile.in(Compile).dependsOn(compile.in(input, Compile)).value,
    scalafixTestkitOutputSourceDirectories :=
      sourceDirectories.in(output, Compile).value,
    scalafixTestkitInputSourceDirectories :=
      sourceDirectories.in(input, Compile).value,
    scalafixTestkitInputClasspath :=
      fullClasspath.in(input, Compile).value,
  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)
