package interface

import interface._
import interface.commands.Command

import scala.util.Random

/*
	Master object used for inteface management and global variables.  Also contains logic to encode and decode command sequences.
*/
object Master {
	var serverId = 0
	def assign: Int = {
		serverId += 1
		serverId
	}
}