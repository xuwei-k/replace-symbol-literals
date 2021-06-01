# Replace `scala.Symbol` Literals

<https://scalacenter.github.io/scalafix/docs/rules/external-rules.html>

`project/scalafix.sbt`

```scala
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.29")
```

sbt shell

```
> scalafixEnable
> scalafixAll dependency:ReplaceSymbolLiterals@com.github.xuwei-k:replace-symbol-literals:0.1.1
```
