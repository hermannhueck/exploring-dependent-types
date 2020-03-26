package joiner.typemem._04mapjoin

trait MapJoiner[A, B] {
  type R
  def mapJoin(elems: Seq[A], f: A => B): R
}

object MapJoiner {

  type Aux[A, B, R0] = MapJoiner[A, B] { type R = R0 }

  private def instance[A, B, R0](fj: Seq[B] => R0): MapJoiner[A, B] = new MapJoiner[A, B] {
    type R = R0
    override def mapJoin(elems: Seq[A], fm: A => B): R =
      fj(elems.map(fm))
  }

  implicit val intToStringMapJoiner: MapJoiner[Int, Char] = instance(_.mkString)

  implicit val sumMapJoiner: MapJoiner[Int, Int] = instance(_.sum)
}

object MapJoinerApp extends hutil.App {

  def doMapJoin[A, B](elems: Seq[A], f: A => B)(implicit j: MapJoiner[A, B]): j.R =
    j.mapJoin(elems, f)

  implicit final class MapJoinerSyntax[A](private val elems: Seq[A]) extends AnyVal {
    def mapJoin[B](f: A => B)(implicit j: MapJoiner[A, B]): j.R =
      j.mapJoin(elems, f)
  }

  import hutil.syntax.pipe._

  val ints1                  = "Hello World!".toList.map(_.toInt) |- println
  val intToChar: Int => Char = _.toChar
  doMapJoin(ints1, intToChar)
    .ensuring(_ == "Hello World!") | println
  ints1
    .mapJoin(intToChar)
    .ensuring(_ == "Hello World!") | println

  println
  val ints2              = (1 to 5).toList |- println
  val square: Int => Int = x => x * x
  doMapJoin(ints2, square)
    .ensuring(_ == 55) | println
  ints2
    .mapJoin(square)
    .ensuring(_ == 55) | println
}
