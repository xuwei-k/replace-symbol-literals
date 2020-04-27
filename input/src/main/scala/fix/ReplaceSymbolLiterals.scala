/*
rule = ReplaceSymbolLiterals
 */
package fix

import scalaz.State

object ReplaceSymbolLiterals {
  val (_, (a, b)) = {
    for {
      x <- State[Int, Int](p => (1, 2))
      y <- State[Int, Int](p => (1, 2))
    } yield (x, y)
  }.apply(0)

  val (_, c) = (3, 4)
}
