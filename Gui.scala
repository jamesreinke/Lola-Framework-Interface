package interface.gui

import interface.Master

import interface.options._
import interface.commands._
import interface.{Encoder, Tag}
import scala.util.Random

sealed trait Data[A] extends Node

/*
 	Object References
*/
sealed abstract class Node(n: Tag, _id: Int, _sid: Int) extends Encoder {
	val tag = n
	val id =  _id
	val sid = _sid match {
		case 0 => interface.Master.assign
		case _ => _sid
	}
}
sealed trait Appendable

object Nothing {
	val tag = new Tag("hYP8hy7xUh")
	def apply(): Nothing = {
		new Nothing()
	}
	sealed case class Nothing() extends Node(tag, 0, 0) {
		override def encode = Master.genEncoding(List(tag))
	}
}	

object Text {
	val tag = new Tag("5mxJ5DinO5")
	def apply(content: String, t: TextType = NoType, size: Size = Medium, block: Boolean = false, id: Int = 0, sid: Int = 0): Text = {
		new Text(content, t, size, block)(id, sid)
	}
	sealed case class Text(content: String, t: TextType, size: Size, block: Boolean)(id: Int, sid: Int) extends Node(tag,id, sid) {
		override def encode = Master.genEncoding(List(tag, content, t, size, block, id, sid))
	}
}

object Image {
	val tag = new Tag("iwUuC8nEFK")
	def apply(url: String, height: Double = 0.0, width: Double = 0.0,id: Int = 0, sid: Int = 0): Image = {
		new Image(url, height, width)(id, sid)
	}
	sealed case class Image(url: String, height: Double, width: Double)(id: Int, sid: Int) extends Node(tag,id, sid) {
		override def encode = Master.genEncoding(List(tag, url, height, width, id, sid))
	}
}

object Notification {
	val tag = new Tag("IHMgzzxD0K")
	def apply(msg: Text.Text, p: Position = Right, id: Int = 0, sid: Int = 0): Notification = {
		new Notification(msg, p)(id, sid)
	}
	sealed case class Notification(msg: Text.Text, p: Position)(id: Int, sid: Int) extends Node(tag,id, sid) {
		override def encode = Master.genEncoding(List(tag, msg, p, id, sid))
	}
}

object Close {
	val tag = new Tag("6J8UOKI0sm")
	def apply(id: Int= 0, sid: Int = 0): Close = {
		new Close()(id, sid)
	}
	sealed case class Close()(id: Int, sid: Int) extends Node(tag,id, sid) {
		override def encode = Master.genEncoding(List(tag, id, sid))
	}
}

object Button {
	val tag = new Tag("hljlAbU6hc")
	def apply(content: Text.Text, size: Size = Small, id: Int = 0, sid: Int = 0): Button = {
		new Button(content, size)(id, sid)
	}
	sealed case class Button(content: Text.Text, size: Size)(id: Int, sid: Int) extends Node(tag,id, sid) {
		override def encode = Master.genEncoding(List(tag, content, size, id, sid))
	}
}

object Block {
	val tag = new Tag("kYbAJ14AEX")
	def apply(items: List[Node], columns: Int = 4, offset: Int = 0,id: Int = 0, sid: Int = 0): Block = {
		inRange(columns)
		new Block(items, columns, offset)(id, sid)
	}
	def inRange(columns: Int): Unit = {
		if(columns < 0 || columns > 12) throw new Exception("Column values must be between 0 and 12")
	}
	sealed case class Block(items: List[Node], columns: Int, offset: Int)(id: Int, sid: Int) extends Node(tag,id, sid) with Appendable {
		override def encode = Master.genEncoding(List(tag, items, columns, offset, id, sid))
	}
}

object LoList {
	val tag = new Tag("MMhYzUZ1sp")
	def apply(items: List[Node], id: Int = 0, sid: Int = 0): LoList = {
		new LoList(items)(id, sid)
	}
	sealed case class LoList(items: List[Node])(id: Int, sid: Int) extends Node(tag, sid, sid) with Appendable {
		override def encode = Master.genEncoding(List(tag, items, id, sid))
	}
}

object Navigation {
	val tag = new Tag("9CeRj55zwR")
	def apply(list: LoList.LoList, vertical: Boolean = false, id: Int = 0, sid: Int = 0): Navigation = {
		new Navigation(list, vertical)(id, sid)
	}
	sealed case class Navigation(list: LoList.LoList, vertical: Boolean)(id: Int, sid: Int) extends Node(tag, id, sid) with Appendable {
		override def encode = Master.genEncoding(List(tag, list, vertical, id, sid))
	}
}

object ButtonToolbar {
	val tag = new Tag("i1VsIwdVIG")
	def apply(buttons: List[Button.Button], size: Size = Medium, id: Int = 0, sid: Int = 0): ButtonToolbar = {
		new ButtonToolbar(buttons, size)(id, sid)
	}
	sealed case class ButtonToolbar(buttons: List[Button.Button], size: Size)(id: Int, sid: Int) extends Node(tag, id, sid) with Appendable {
		override def encode = Master.genEncoding(List(tag, buttons, size, id, sid))
	}
}

object InputNumber {
	val tag = new Tag("OL9SPi7BG0")
	def apply(
		label: Option[Text.Text] = None, 
		size: Size = Medium, 
		addon: Option[Node] = None, 
		addonPos: Position = Left, 
		placeholder: String = "", id: Int = 0, sid: Int = 0): InputNumber = {

		new InputNumber(label, size, addon, addonPos, placeholder)(id, sid)
	
	}
	sealed case class InputNumber(
		label: Option[Text.Text], 
		size: Size, addon: Option[Node], 
		addonPos: Position, 
		placeholder: String)(id: Int, sid: Int) extends Node(tag, id, sid) with Data[Int] {
			override def encode = Master.genEncoding(List(tag, label, size, addonPos, placeholder, id, sid))
		}
}
object InputPassword {
	val tag = new Tag("EJPipsoypR")
	def apply(
		label: Option[Text.Text] = None, 
		size: Size = Medium, 
		addon: Option[Node] = None, 
		addonPos: Position = Left, 
		placeholder: String = "", id: Int = 0, sid: Int = 0): InputPassword = {

		new InputPassword(label, size, addon, addonPos, placeholder)(id, sid)
	
	}
	sealed case class InputPassword(
		label: Option[Text.Text], 
		size: Size, addon: Option[Node], 
		addonPos: Position, 
		placeholder: String)(id: Int, sid: Int) extends Node(tag, id, sid) with Data[String] {
			override def encode = Master.genEncoding(List(tag, label, size, addonPos, placeholder, id, sid))
		}
}
object InputText {
	val tag = new Tag("tfi5W7wIRZ")
	def apply(
		label: Option[Text.Text] = None, 
		size: Size = Medium, 
		addon: Option[Node] = None, 
		addonPos: Position = Left, 
		placeholder: String = "", id: Int = 0, sid: Int = 0): InputText = {

		new InputText(label, size, addon, addonPos, placeholder)(id, sid)
	
	}
	sealed case class InputText(
		label: Option[Text.Text], 
		size: Size, addon: Option[Node], 
		addonPos: Position, 
		placeholder: String)(id: Int, sid: Int) extends Node(tag, id, sid) with Data[String] {
			override def encode = Master.genEncoding(List(tag, label, size, addonPos, placeholder, id, sid))
		}
}
object TextArea {
	val tag = new Tag("jSxYIdNgq4")
	def apply(rows: Int = 5, label: Option[Text.Text] = None, id: Int = 0, sid: Int = 0): TextArea = {
		new TextArea(rows, label)(id, sid)
	}
	sealed case class TextArea(rows: Int, label: Option[Text.Text])(id: Int, sid: Int) extends Node(tag, id, sid) with Data[String] {
		override def encode = Master.genEncoding(List(rows, label, id, sid))
	}
}
object Form {
	val tag = new Tag("z8x17Not9b")
	/* Helper applications.  Cannot have default parameters for overloaded methods */
	def apply[A](a: Data[A]): Form1[A] = {
		new Form1[A](a)(0, 0)
	}
	def apply[A,B](a: Data[A], b: Data[B]): Form2[A,B] = {
		new Form2[A,B](a, b)(0, 0)
	}
	def apply[A,B,C](a: Data[A], b: Data[B], c: Data[C]): Form3[A,B,C] = {
		new Form3[A,B,C](a,b,c)(0, 0)
	}
	def apply[A,B,C,D](a: Data[A], b: Data[B], c: Data[C], d: Data[D]): Form4[A,B,C,D] = {
		new Form4[A,B,C,D](a,b,c,d)(0, 0)
	}
	def apply[A,B,C,D,E](a: Data[A], b: Data[B], c: Data[C], d: Data[D], e: Data[E]): Form5[A,B,C,D,E] = {
		new Form5[A,B,C,D,E](a,b,c,d,e)(0, 0)
	}
	def apply[A,B,C,D,E,F](a: Data[A], b: Data[B], c: Data[C], d: Data[D], e: Data[E], f: Data[F]): Form6[A,B,C,D,E,F] = {
		new Form6[A,B,C,D,E,F](a,b,c,d,e,f)(0, 0)
	}
	def apply[A](a: Data[A], id: Int, sid: Int): Form1[A] = {
		new Form1[A](a)(id, sid)
	}
	def apply[A,B](a: Data[A], b: Data[B], id: Int, sid: Int): Form2[A,B] = {
		new Form2[A,B](a,b)(id, sid)
	}
	def apply[A,B,C](a: Data[A], b: Data[B], c: Data[C], id: Int, sid: Int): Form3[A,B,C] = {
		new Form3[A,B,C](a,b,c)(id, sid)
	}
	def apply[A,B,C,D](a: Data[A], b: Data[B], c: Data[C], d: Data[D], id: Int, sid: Int): Form4[A,B,C,D] = {
		new Form4[A,B,C,D](a,b,c,d)(id, sid)
	}
	def apply[A,B,C,D,E](a: Data[A], b: Data[B], c: Data[C], d: Data[D], e: Data[E], id: Int, sid: Int): Form5[A,B,C,D,E] = {
		new Form5[A,B,C,D,E](a,b,c,d,e)(id, sid)
	}
	def apply[A,B,C,D,E,F](a: Data[A], b: Data[B], c: Data[C], d: Data[D], e: Data[E], f: Data[F], id: Int, sid: Int): Form6[A,B,C,D,E,F] = {
		new Form6[A,B,C,D,E,F](a,b,c,d,e,f)(id, sid)
	}
	sealed case class Form1[A](a: Data[A])(id: Int, sid: Int) extends Node(tag, id, sid) {
		override def encode = Master.genEncoding(List(tag, a, id, sid))
	}
	sealed case class Form2[A,B](a: Data[A], b: Data[B])(id: Int, sid: Int) extends Node(tag, id, sid) {
		override def encode = Master.genEncoding(List(tag, a, b, id, sid))
	}
	sealed case class Form3[A,B,C](a: Data[A], b: Data[B], c: Data[C])(id: Int, sid: Int) extends Node(tag, id, sid) {
		override def encode = Master.genEncoding(List(tag, a, b, c, id, sid))
	}
	sealed case class Form4[A,B,C,D](a: Data[A], b: Data[B], c: Data[C], d: Data[D])(id: Int, sid: Int) extends Node(tag, id, sid) {
		override def encode = Master.genEncoding(List(tag, a, b, c, d, id, sid))
	}
	sealed case class Form5[A,B,C,D,E](a: Data[A], b: Data[B], c: Data[C], d: Data[D], e: Data[E])(id: Int, sid: Int) extends Node(tag, id, sid) {
		override def encode = Master.genEncoding(List(tag, a, b, c, d, e, id, sid))
	}
	sealed case class Form6[A,B,C,D,E,F](a: Data[A], b: Data[B], c: Data[C], d: Data[D], e: Data[E], f: Data[F])(id: Int, sid: Int) extends Node(tag, id, sid) {
		override def encode = Master.genEncoding(List(tag, a, b, c, d, e, f, id, sid))
	}
}