/*
  See talk:
  Heather Miller: Academese to English: Scala's Type System, Dependent Types and What It Means To You
  https://www.youtube.com/watch?v=ptCvn4-lyXM
 */
package kvstore._01miller

object AwesomeDB {
  @com.github.ghik.silencer.silent("never used")
  abstract class Key(name: String) {
    type Value
  }
}

import AwesomeDB.Key

class AwesomeDB {

  import scala.collection.immutable.Map

  val data = Map.empty[Key, Any]

  def get(key: Key): Option[key.Value]      = data.get(key).asInstanceOf[Option[key.Value]]
  def set(key: Key)(value: key.Value): Unit = data.updated(key, value): Unit
}

trait IntValued    extends Key { type Value = Int    }
trait StringValued extends Key { type Value = String }
object Keys {
  val foo = new Key("foo") with IntValued
  val bar = new Key("bar") with StringValued
}

object AwesomeDBApp extends hutil.App {

  val datastore = new AwesomeDB
  datastore.set(Keys.foo)(23)
  datastore.set(Keys.bar)("23")

  // datastore.set(Keys.foo)("23")
  // [error] [E1] core/src/main/scala/kvstore/_01miller/AwesomeDB.scala
  // [error]      type mismatch;
  // [error]       found   : String("23")
  // [error]       required: kvstore._01miller.AwesomeDBApp.Keys.foo.Value
  // [error]          (which expands to)  Int
  // [error]      L32:   datastore.set(Keys.foo)("23")
  // [error]                                     ^
}
