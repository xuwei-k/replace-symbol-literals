package replace_symbol_literals

import scalafix.v1.{Patch, SemanticDocument, SemanticRule}
import scala.meta._
import scalafix.v1._

class ReplaceSymbolLiterals extends SemanticRule("ReplaceSymbolLiterals") {

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case t: Term.Apply
          if t.fun.symbol.displayName == "apply" && t.symbol.owner.value == "scalaz/IndexedStateT#" && t.args.size == 1 =>
        val Term.Select(_, method) = t.fun
        Patch.replaceTree(method, "eval")
      case t: Defn.Val if t.pats.size == 1 && t.rhs.symbol.owner.value == "scalaz/IndexedStateT#" =>
        t.pats match {
          case List(x @ Pat.Tuple(List(Pat.Wildcard(), p))) =>
            Patch.replaceTree(x, p.toString)
          case _ =>
            Patch.empty
        }
    }.asPatch
  }

}
