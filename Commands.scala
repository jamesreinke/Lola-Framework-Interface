package interface.commands

import interface.Master
import interface.gui._
import interface.Encoder
import scala.util.Random
import interface.Tag

/*
	Commands
*/
sealed abstract class Command(t: Tag) extends Encoder {
	val tag = t
}

object Nothing {
	val tag = new Tag("a114MQCl0x")
	def apply(): Nothing = {
		new Nothing()
	}
	sealed case class Nothing() extends Command(tag) {
		override def encode = Master.genEncoding(List(tag))
	}
}
/* Create singleton objects */
object Create {
	val tag = new Tag("YbtqmMQG2O")
	def apply(item: Node): Create = {
		new Create(item)
	}
	sealed case class Create(item: Node) extends Command(tag) {
		override def encode = Master.genEncoding(List(tag, item))
	}
}
/* Delete singleton or group objects */
object Delete {
	val tag = new Tag("dPEe0XXvrG")
	def apply(item: Node): Delete = {
		new Delete(item)
	}
	sealed case class Delete(item: Node) extends Command(tag) {
		override def encode = Master.genEncoding(List(tag, item))
	}
}
/* 
	Animation Commands 
*/
sealed abstract class Animate(tag: Tag) extends Command(tag)

object Css {
	val tag = new Tag("gAzNSRvGl2")
	def apply(item: Node, key: String, value: String): Css = {
		new Css(item, key, value)
	}
	sealed case class Css(item: Node, key: String, value: String) extends Animate(tag) {
		override def encode = Master.genEncoding(List(tag, item, key, value))
	}
}
object OnClick {
	val tag = new Tag("GK8EUk8beZ")
	def apply(item: Node, cm: Command): OnClick = {
		new OnClick(item, cm)
	}
	sealed case class OnClick(item: Node, cm: Command) extends Animate(tag) {
		override def encode = Master.genEncoding(List(tag, item, cm))
	}
}
object Get {
	val tag = new Tag("iA8kuWTmYj")
	def apply(item: Node, url: String): Get = {
		new Get(item, url)
	}
	sealed case class Get(item: Node, url: String) extends Command(tag) {
		override def encode = Master.genEncoding(List(tag, item, url))
	}
}
object Set {
	val tag = new Tag("vnAED5Qjmo")
	def apply[A](item: Node, key: String, value: A): Set[A] = {
		new Set[A](item, key, value)
	}
	sealed case class Set[A](item: Node, key: String, value: A) extends Command(tag) {
		override def encode = Master.genEncoding(List(tag, item, key, value))
	}
}