package joiner.typeparam._01basic

trait Joiner[E, R] {
  def join(elems: Seq[E]): R
}

object Joiner {

  implicit val charJoiner: Joiner[Char, String] = new Joiner[Char, String] {
    override def join(elems: Seq[Char]): String =
      elems.mkString
  }

  // implicit val intJoiner: Joiner[Int, Int] = // using sam
  //   _.sum
  implicit val intJoiner: Joiner[Int, Int] = new Joiner[Int, Int] {
    override def join(elems: Seq[Int]): Int =
      elems.sum
  }
}

object JoinerApp extends hutil.App {

  def doJoin[E, R](elems: Seq[E])(implicit j: Joiner[E, R]): R =
    j.join(elems)

  implicit final class JoinerSyntax[E](private val elems: Seq[E]) extends AnyVal {
    def join[R](implicit j: Joiner[E, R]): R =
      j.join(elems)
  }

  import hutil.syntax.pipe._

  val chars = "Hello World!".toList |- println
  doJoin(chars)
    .ensuring(_ == "Hello World!") | println
  chars
    .join
    .ensuring(_ == "Hello World!") | println

  println
  val ints = (1 to 5).toList |- println
  doJoin(ints)
    .ensuring(_ == 15)
    .toString | println
  ints
    .join
    .ensuring(_ == 15)
    .toString | println
}
