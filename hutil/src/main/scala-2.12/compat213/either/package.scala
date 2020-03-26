package compat213

package object either {

  implicit final class RightOpsWithLeft[+L, +R](private val right: Right[L, R]) extends AnyVal {
    @inline def withLeft[LL >: L]: Either[LL, R] = right
  }

  implicit final class LeftOpsWithRight[+L, +R](private val left: Left[L, R]) extends AnyVal {
    @inline def withRight[RR >: R]: Either[L, RR] = left
  }

  implicit final class EitherOpsFlatten[+L, +R](private val either: Either[L, R]) extends AnyVal {
    @inline def flatten[L1 >: L, RR](implicit ev: R <:< Either[L1, RR]): Either[L1, RR] =
      either.flatMap(x => x)
  }
}
