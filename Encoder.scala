package interface

import commands.Command
trait Encoder {
	val tag: Tag
	val aT = "~~"
	val eT = "~~|||"
	def encode: String = s"${tag.name}"
	override def toString = encode
}

sealed case class Tag(name: String) {
		override def toString = name
}
