package interface

import commands.Command
trait Encoder {
	val name: String
	val aT = "~~"
	val eT = "~~|||"
	def encode: String = s"${name}"
	override def toString = encode
}

object Encode {
	def apply(cms: List[Command]): String = {
		cms.mkString("~~")
	}
	def apply(cm: Command): String = {
		Encode(List(cm))
	}
}