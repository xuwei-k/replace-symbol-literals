# Replace `scala.Symbol` Literals

<https://scalacenter.github.io/scalafix/docs/rules/external-rules.html>

`project/scalafix.sbt`

```scala
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.27")
```

sbt shell

```
> scalafixEnable
> scalafix dependency:ReplaceSymbolLiterals@com.github.xuwei-k:replace-symbol-literals:0.1.1
> test:scalafix dependency:ReplaceSymbolLiterals@com.github.xuwei-k:replace-symbol-literals:0.1.1
```
