package interface

import interface._
import interface.commands.Command

import scala.util.Random

/*
	Master object used for inteface management and global variables.  Also contains logic to encode and decode command sequences.
*/
object Master extends Encoder {
	var serverId = 0
	def assign: Int = {
		serverId += 1
		serverId
	}

	val tag = new Tag("")

	def genEncoding(items: List[Any]): String = {
		(encodings(items) mkString aT) + eT
	}

	private def encodings(items: List[Any], list: List[String] = List()): List[String] = {
		items match {
			case List() => list
			case _ => {
				items.head match {
					case x: List[Any] => encodings(items.tail, list :+ ((x map { x => primitiveEncoding(x) } mkString aT) + eT))
					case x => encodings(items.tail, list :+ primitiveEncoding(x))
				}
			}
		}
	}
	/* 
		Encodes primitive values to strings.  Everything but strings encode correctly... including options.
	*/
	private def primitiveEncoding(x: Any): String = x match {
		case s: String => s"'${x}'"
		case a: Any => a.toString
	}
}