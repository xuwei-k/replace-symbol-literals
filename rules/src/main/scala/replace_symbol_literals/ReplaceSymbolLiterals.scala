package replace_symbol_literals

import scalafix.v1.{Patch, SemanticDocument, SemanticRule}

import scala.meta._
import scalafix.v1._

class ReplaceSymbolLiterals extends SemanticRule("ReplaceSymbolLiterals") {

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case x: Type.Apply if """scalaz/`\/`#""" == x.tpe.symbol.value && x.args.size == 2 =>
        Patch.replaceTree(x, s"""${x.args(0)} \\/ ${x.args(1)}""")
    }.asPatch
  }

}
