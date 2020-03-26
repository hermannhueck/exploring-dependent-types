package compat213.collections

package object tapeach {

  implicit final class SeqOpsTapEach[A](private val seq: Seq[A]) extends AnyVal {
    @inline def tapEach(f: A => Unit): Seq[A] = seq.map { a => f(a); a }
  }

  implicit final class ListOpsTapEach[A](private val seq: List[A]) extends AnyVal {
    @inline def tapEach(f: A => Unit): List[A] = seq.map { a => f(a); a }
  }

  implicit final class VectorOpsTapEach[A](private val seq: Vector[A]) extends AnyVal {
    @inline def tapEach(f: A => Unit): Vector[A] = seq.map { a => f(a); a }
  }

  implicit final class SetOpsTapEach[A](private val seq: Set[A]) extends AnyVal {
    @inline def tapEach(f: A => Unit): Set[A] = seq.map { a => f(a); a }
  }

  implicit final class MapOpsTapEach[K, V](private val seq: Map[K, V]) {
    @inline def tapEach(f: ((K, V)) => Unit): Map[K, V] = seq.map { pair => f(pair); pair }
  }
}
