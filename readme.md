# Replace `scala.Symbol` Literals

[![Build Status](https://travis-ci.org/xuwei-k/replace-symbol-literals.svg?branch=master)](https://travis-ci.org/xuwei-k/replace-symbol-literals)

<https://scalacenter.github.io/scalafix/docs/rules/external-rules.html>

`project/scalafix.sbt`

```scala
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.16")
```

sbt shell

```
> scalafixEnable
> scalafix dependency:ReplaceSymbolLiterals@com.github.xuwei-k:replace-symbol-literals:0.1.1
> test:scalafix dependency:ReplaceSymbolLiterals@com.github.xuwei-k:replace-symbol-literals:0.1.1
```
