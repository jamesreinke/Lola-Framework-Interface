package interface.commands

import interface.gui._

import interface.Encoder

import scala.util.Random

/*
	Commands
*/
sealed abstract class Command(n: String) extends Encoder {
	val name = n
}

object Nothing {
	val name = "a114MQCl0x"
	def apply(): Nothing = {
		new Nothing()
	}
	sealed case class Nothing() extends Command(name) {
		override def encode = s"${name}${eT}"
	}
}
/* Create singleton objects */
object Create {
	val name = "YbtqmMQG2O"
	def apply(item: Node): Create = {
		new Create(item)
	}
	sealed case class Create(item: Node) extends Command(name) {
		override def encode = s"${name}${aT}${item.encode}${eT}"
	}
}
/* Delete singleton or group objects */
object Delete {
	val name = "dPEe0XXvrG"
	def apply(item: Node): Delete = {
		new Delete(item)
	}
	sealed case class Delete(item: Node) extends Command(name) {
		override def encode = s"${name}${aT}${item.encode}${eT}"
	}
}
/* 
	Animation Commands 
*/
sealed abstract class Animate(name: String) extends Command(name)

object Css {
	val name = "gAzNSRvGl2"
	def apply(item: Node, key: String, value: String): Css = {
		new Css(item, key, value)
	}
	sealed case class Css(item: Node, key: String, value: String) extends Animate(name) {
		override def encode = s"${name}${aT}${item.encode}${aT}${key}${aT}${value}${eT}"
	}
}
object OnClick {
	val name = "GK8EUk8beZ"
	def apply(item: Node, cm: Command): OnClick = {
		new OnClick(item, cm)
	}
	sealed case class OnClick(item: Node, cm: Command) extends Animate(name) {
		override def encode = s"${name}${aT}${item.encode}${aT}${cm.encode}${eT}"
	}
}