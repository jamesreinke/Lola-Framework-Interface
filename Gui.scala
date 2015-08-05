package interface.gui

import interface.options._
import interface.commands._
import interface.Encoder
import scala.util.Random
/*
 	Object References
*/
sealed abstract class Node(n: String, _id: Int, _sid: Int) extends Encoder {
	val name = n
	val id =  _id
	val sid = _sid match {
		case 0 => interface.Master.assign
		case _ => _sid
	}
}
sealed trait Appendable

object Nothing {
	val name = "hYP8hy7xUh"
	def apply(): Nothing = {
		new Nothing()
	}
	sealed case class Nothing() extends Node(name, 0, 0) {
		override def encode = s"${name}${eT}"
	}
}	

object Text {
	val name = "5mxJ5DinO5"
	def apply(content: String, t: TextType = NoType, size: Size = Medium, block: Boolean = false, id: Int = 0, sid: Int = 0): Text = {
		new Text(content, t, size, block)(id, sid)
	}
	sealed case class Text(content: String, t: TextType, size: Size, block: Boolean)(id: Int, sid: Int) extends Node(name,id, sid) {
		override def encode = s"${name}${aT}${content}${aT}${t}${aT}${size}${aT}${id}${aT}${sid}${eT}"
	}
}

object Image {
	val name = "iwUuC8nEFK"
	def apply(url: String, height: Double = 0.0, width: Double = 0.0,id: Int = 0, sid: Int = 0): Image = {
		new Image(url, height, width)(id, sid)
	}
	sealed case class Image(url: String, height: Double, width: Double)(id: Int, sid: Int) extends Node(name,id, sid) {
		override def encode = s"${name}${aT}${url}${aT}${height}${aT}${width}${aT}${id}${aT}${sid}${eT}"
	}
}

object Notification {
	val name = "IHMgzzxD0K"
	def apply(msg: Text.Text, p: Position = Right,id: Int = 0, sid: Int = 0): Notification = {
		new Notification(msg, p)(id, sid)
	}
	sealed case class Notification(msg: Text.Text, p: Position)(id: Int, sid: Int) extends Node(name,id, sid) {
		override def encode = s"${name}${aT}${p}${aT}${id}${aT}${sid}${eT}"
	}
}

object Close {
	val name = "6J8UOKI0sm"
	def apply(id: Int= 0, sid: Int = 0): Close = {
		new Close()(id, sid)
	}
	sealed case class Close()(id: Int, sid: Int) extends Node(name,id, sid) {
		override def encode = s"${name}${aT}${id}${aT}${sid}${eT}"
	}
}

object Button {
	val name = "hljlAbU6hc"
	def apply(content: Text.Text, size: Size = Small,id: Int = 0, sid: Int = 0): Button = {
		new Button(content, size)(id, sid)
	}
	sealed case class Button(content: Text.Text, size: Size)(id: Int, sid: Int) extends Node(name,id, sid) {
		override def encode = s"${name}${aT}${content}${aT}${size}${aT}${id}${aT}${sid}${eT}"
	}
}

object Block {
	val name = "kYbAJ14AEX"
	def apply(items: List[Node], columns: Int = 4, offset: Int = 0,id: Int = 0,sid: Int = 0): Block = {
		inRange(columns)
		new Block(items, columns, offset)(id, sid)
	}
	def inRange(columns: Int): Unit = {
		if(columns < 0 || columns > 12) throw new Exception("Column values must be between 0 and 12")
	}
	sealed case class Block(items: List[Node], columns: Int, offset: Int)(id: Int, sid: Int) extends Node(name,id, sid) with Appendable {
		override def encode = s"""${name}${aT}${items mkString ("${aT}")}${aT}${columns}${aT}${offset}${aT}${id}${aT}${sid}${eT}"""
	}
}

object LoList {
	val name = "MMhYzUZ1sp"
	def apply(items: List[Node],id: Int = 0, sid: Int = 0): LoList = {
		new LoList(items)(id, sid)
	}
	sealed case class LoList(items: List[Node])(id: Int, sid: Int) extends Node(name, sid, sid) with Appendable {
		override def encode = s"""${name}${aT}${items mkString ("${aT}")}${aT}${id}${aT}${sid}${eT}"""
	}
}
