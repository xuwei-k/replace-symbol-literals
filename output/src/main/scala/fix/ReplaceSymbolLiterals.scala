package fix

import scalaz.State

object ReplaceSymbolLiterals {
  val (a, b) = {
    for {
      x <- State[Int, Int](p => (1, 2))
      y <- State[Int, Int](p => (1, 2))
    } yield (x, y)
  }.eval(0)

  val (_, c) = (3, 4)
}
