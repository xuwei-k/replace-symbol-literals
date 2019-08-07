package replace_symbol_literals

import scalafix.v1.{Patch, SemanticDocument, SemanticRule}
import scala.meta.Lit

class ReplaceSymbolLiterals extends SemanticRule("ReplaceSymbolLiterals") {

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case literal @ Lit.Symbol(_) =>
        Patch.replaceTree(literal, s"""Symbol("${literal.value.name}")""")
    }.foldLeft(Patch.empty)(_ + _)
  }

}
