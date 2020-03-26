package joiner.typemem._03monoid

import cats.kernel.Monoid
import cats.instances.int._

trait Joiner[E] {
  type R
  def join(elems: Seq[E]): R
}

object Joiner {

  type Aux[E, R0] = Joiner[E] { type R = R0 }

  private def instance[E, R0](f: Seq[E] => R0): Joiner.Aux[E, R0] = new Joiner[E] {
    type R = R0
    override def join(elems: Seq[E]): R =
      f(elems)
  }

  implicit val charJoiner: Joiner.Aux[Char, String] = instance(_.mkString)

  implicit def monoidJoiner[A](implicit M: Monoid[A]): Joiner.Aux[A, A] = instance(M.combineAll(_))
  // implicit val intJoiner: Joiner.Aux[Int, Int] = instance(_.sum)
}

object JoinerApp extends hutil.App {

  def doJoin[E, R0](elems: Seq[E])(implicit j: Joiner.Aux[E, R0]): R0 =
    j.join(elems)

  implicit final class JoinerSyntax[E](private val elems: Seq[E]) extends AnyVal {
    def join[R0](implicit j: Joiner.Aux[E, R0]): R0 =
      j.join(elems)
  }

  import hutil.syntax.pipe._

  val chars = "Hello World!".toList |- println
  doJoin(chars) // implicitly uses charJoiner
    .ensuring(_ == "Hello World!") | println
  chars.join // implicitly uses charJoiner
    .ensuring(_ == "Hello World!") | println

  println
  val ints = (1 to 5).toList |- println
  doJoin(ints) // implicitly uses monoidJoiner with Monoid[Int]
    .ensuring(_ == 15)
    .toString | println
  ints.join // implicitly uses monoidJoiner with Monoid[Int]
    .ensuring(_ == 15)
    .toString | println
}
