package compat213

package object string {

  implicit final class StringOpsToXXXOption(private val s: String) extends AnyVal {

    import scala.util.Try

    @inline def toIntOption: Option[Int]         = Try(s.toInt).toOption
    @inline def toDoubleOption: Option[Double]   = Try(s.toDouble).toOption
    @inline def toBooleanOption: Option[Boolean] = Try(s.toBoolean).toOption
  }
}
