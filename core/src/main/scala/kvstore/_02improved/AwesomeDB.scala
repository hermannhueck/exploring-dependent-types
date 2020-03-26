/*
  See talk:
  Heather Miller: Academese to English: Scala's Type System, Dependent Types and What It Means To You
  https://www.youtube.com/watch?v=ptCvn4-lyXM
 */

package kvstore._02improved

object AwesomeDB {

  trait Key {
    type Value
  }

  object Keys {
    val intKey    = new Key { type Value = Int    }
    val stringKey = new Key { type Value = String }
  }
}

import AwesomeDB._

class AwesomeDB {

  val data = scala.collection.immutable.Map.empty[Key, Any]

  def get(key: Key): Option[key.Value]      = data.get(key).asInstanceOf[Option[key.Value]]
  def set(key: Key)(value: key.Value): Unit = data.updated(key, value): Unit
}

object AwesomeDBApp extends hutil.App {

  val datastore = new AwesomeDB
  datastore.set(Keys.intKey)(42)
  datastore.set(Keys.stringKey)("42")

  //datastore.set(Keys.intKey)("42")
  // [error] [E1] core/src/main/scala/kvstore/_02improved/AwesomeDB.scala
  // [error]      type mismatch;
  // [error]       found   : String("42")
  // [error]       required: kvstore._02improved.AwesomeDBApp.Keys.foo.Value
  // [error]          (which expands to)  Int
  // [error]      L32:   datastore.set(Keys.foo)("42")
  // [error]                                     ^
}
