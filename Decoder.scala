package interface

import commands._
import gui._
import options._

object Decode {

	def apply(s: String): List[Command] = {
		val args = s.replaceAll("~~~~","~~ ~~").split("~~").toList map { x => x.trim }
		cm(args) match {
			case (item: Command, List()) => List(item)
			case (item: Command, rest: List[String]) => item +: Decode(rest mkString "~~")
			case _ => List()
		}
	}
	/*
		TODO: Review this new code and add argument passing for client and server id's
	*/
	private def node(args: List[String]): (Node, List[String]) = args.head match {
		case "|||" => (gui.Nothing(), args.tail)
		case gui.Nothing.name => (gui.Nothing(), args drop 2)
		case gui.Text.name => {
			val (a, b, c, d, id, sid, rest) = Consume[String, options.TextType, options.Size, Boolean, Int, Int](args.tail)
			(gui.Text(a, b, c, d, id, sid), rest.tail)
		}
		case gui.Image.name => {
			val (a, b, c, id, sid, rest) = Consume[String, Double, Double, Int, Int](args.tail)
			(gui.Image(a, b, c, id, sid), rest.tail)
		}
		case gui.Notification.name => {
			val (a, b, id, sid, rest) = Consume[gui.Text.Text, options.Position, Int, Int](args.tail)
			(gui.Notification(a, b, id, sid), rest.tail)
		}
		case gui.Close.name => (gui.Close(), args.tail)
		case gui.Button.name => {
			val (a, b, id, sid, rest) = Consume[gui.Text.Text, options.Size, Int, Int](args.tail)
			(gui.Button(a, b, id, sid), rest.tail)
		}
		case Block.name => {
			val (a, b, c, id, sid, rest) = Consume[List[gui.Node], Int, Int, Int, Int](args.tail)
			(gui.Block(a, b, c, id, sid), rest.tail)
		}
		case LoList.name => {
			val (a, id, sid, rest) = Consume[List[gui.Node], Int, Int](args.tail)
			(gui.LoList(a, id, sid), rest.tail)
		}
		case _ => (gui.Nothing(), List())
	}
	/* 
		Decodes command arguments.
	*/
	private def cm(args: List[String]): (Command, List[String]) = args.head match {
		case "|||" => (commands.Nothing(), args.tail)
		case commands.Nothing.name => (commands.Nothing(), args drop 2)
		case commands.Create.name => {
			val (item, rest) = Consume[gui.Node](args.tail)
			(commands.Create(item), rest.tail)
		}
		case commands.Delete.name => {
			val (item, rest) = Consume[gui.Node](args.tail)
			(commands.Delete(item), rest.tail)
		}
		case commands.Css.name => {
			val (item, key, value, rest) = Consume[gui.Node, String, String](args.tail)
			(commands.Css(item, key, value), rest.tail)
		}
		case commands.OnClick.name => {
			val (item, comm, rest) = Consume[gui.Node, commands.Command](args.tail)
			(commands.OnClick(item, comm), rest.tail)
		}
		case _ => (commands.Nothing(), args)
	}
	private def tType(s: String): Option[options.TextType] = s match {
		case options.NoType.name => Some(options.NoType)
		case options.Primary.name => Some(options.Primary)
		case options.Success.name => Some(options.Success)
		case options.Info.name => Some(options.Info)
		case options.Warning.name => Some(options.Warning)
		case options.Danger.name => Some(options.Danger)
		case _ => None
	}
	private def pos(s: String): Option[options.Position] = s match {
		case options.Top.name => Some(options.Top)
		case options.Right.name => Some(options.Right)
		case options.Bottom.name => Some(options.Bottom)
		case options.Left.name => Some(options.Left)
		case _ => None
	}
	private def size(s: String): Option[options.Size] = s match {
		case options.XSmall.name => Some(options.XSmall)
		case options.Small.name => Some(options.Small)
		case options.Medium.name => Some(options.Medium)
		case options.Large.name => Some(options.Large)
		case options.XLarge.name => Some(options.XLarge)
		case _ => None
	}
	/*
		Converts a list of strings into their respective types given the arity 1-5 and then returns the remaining
		list.  'Consume 1-5: TODO: Add all arities '
	*/
	object Consume {
		def apply[A](args: List[String]): (A, List[String]) = {
			args match {
				case Node((item, rest)) => (item.asInstanceOf[A], rest)
				case Nodes((item, rest)) => (item.asInstanceOf[A], rest)
				case Command((item, rest)) => (item.asInstanceOf[A], rest)
				case Commands((item, rest)) => (item.asInstanceOf[A], rest)
				case _ => args.head match {
					/* Order is very important here.  We must match from most specific to most general */
					case TextType(item) => (item.asInstanceOf[A], args.tail)
					case Size(item) => (item.asInstanceOf[A], args.tail)
					case Position(item) => (item.asInstanceOf[A], args.tail)
					case Integer(item) => (item.asInstanceOf[A], args.tail)
					case Double(item) => (item.asInstanceOf[A], args.tail)
					case Boolean(item) => (item.asInstanceOf[A], args.tail)
					case String(item) => (item.asInstanceOf[A], args.tail)
				}
			}
		}
		def apply[A,B](args: List[String]): (A, B, List[String]) = {
			val (item, rest1) = Consume[A](args)
			val (item2, rest2) = Consume[B](rest1)
			(item, item2, rest2)
		}
		def apply[A,B,C](args: List[String]): (A, B, C, List[String]) = {
			val (item1, item2, rest1) = Consume[A,B](args)
			val (item3, rest2) = Consume[C](rest1)
			(item1, item2, item3, rest2)
		}
		def apply[A,B,C,D](args: List[String]): (A, B, C, D, List[String]) = {
			val (item1, item2, rest1) = Consume[A,B](args)
			val (item3, item4, rest2) = Consume[C,D](rest1)
			(item1, item2, item3, item4, rest2)
		}
		def apply[A,B,C,D,E](args: List[String]): (A,B,C,D,E, List[String]) = {
			val (item1, item2, item3, rest1) = Consume[A,B,C](args)
			val (item4, item5, rest2) = Consume[D,E](rest1)
			(item1, item2, item3, item4, item5, rest2)
		}
		def apply[A,B,C,D,E,F](args: List[String]): (A,B,C,D,E,F, List[String]) = {
			val (item1, item2, item3, rest1) = Consume[A,B,C](args)
			val (item4, item5, item6, rest2) = Consume[D,E,F](rest1)
			(item1, item2, item3, item4, item5, item6, rest2)
		}

		/* Decodes a list of commands of arbitrary length */
		private def kCms(args: List[String], cmArgs: List[commands.Command] = List()): (List[commands.Command], List[String]) = {
			val output = cm(args)
			output._1 match {
				case _: commands.Nothing.Nothing => (cmArgs, output._2)
				case _: Command => kCms(output._2, cmArgs :+ output._1)
			}
		}

		/* Decodes a list of nodes of arbitrary length */
		private def kNodes(args: List[String], nodes: List[gui.Node] = List()): (List[gui.Node], List[String]) = {
			val output = node(args)
			output._1 match {
				case _: gui.Nothing.Nothing => (nodes, output._2)
				case _: gui.Node => kNodes(output._2, nodes :+ output._1)
			}
		}
		/*
			A collection of extractors, where we need a conversion of type string to data type.
		*/
		sealed trait Parameter
		object TextType extends Parameter {
			def apply(x: options.TextType) = x.toString
			def unapply(x: Any): Option[TextType] = x match {
				case x: String => tType(x)
				case _ => None
			}
		}
		/* 
			Node, Nodes, Command, Commands extractors.  Unlike primitives, these extractors require a List[String]
		*/
		object Node extends Parameter {
			def apply(x: gui.Node) = x.toString split "~~"
			def unapply(x: List[String]): Option[(gui.Node, List[String])] = x match {
				case s: List[String] => {
					val (item, rest) = node(s)
					item match {
						case _: gui.Nothing.Nothing => None
						case y => Some(y, rest)
					}
				}
				case _ => None
			}
		}
		object Nodes extends Parameter {
			def apply(x: List[gui.Node]) = x mkString "" split "~~"
			def unapply(x: List[String]): Option[(List[gui.Node], List[String])] = x match {
				case s: List[String] => {
					val (items, rest) = kNodes(s)
					items match {
						case List() => None
						case _ => Some(items, rest)
					}
				}
				case _ => None
			}
		}
		object Command extends Parameter {
			def apply(x: commands.Command) = x.toString split "~~"
			def unapply(x: List[String]): Option[(commands.Command, List[String])] = x match {
				case s: List[String] => {
					val (comm, rest) = cm(s)
					comm match {
						case _: commands.Nothing.Nothing => None
						case _ => Some(comm, rest)
					}
				}
				case _ => None
			}
		}
		object Commands extends Parameter {
			def apply(x: List[commands.Command]) = x mkString "" split "~~"
			def unapply(x: List[String]): Option[(List[commands.Command], List[String])] = x match {
				case s: List[String] => {
					val (comms, rest) = kCms(s)
					comms match {
						case List() => None
						case _ => Some(comms, rest)
					}
				}
			}
		}
		/*
			Primitive extractors.  Require String
		*/
		object Double extends Parameter {
			def apply(x: Double) = x.toString
			def unapply(x: Any): Option[Double] = x match {
				case s: String => try { Some(s.toDouble) } catch { case _: Throwable => None}
				case _ => None
			}
		}
		object String extends Parameter {
			def apply(x: String) = x
			def unapply(x: Any): Option[String] = x match {
				case x: String => Some(x)
				case _ => None
			}
		}
		object Boolean extends Parameter {
			def apply(x: Boolean) = x.toString
			def unapply(x: Any): Option[Boolean] = x.toString.toLowerCase match {
				case "true" => Some(true)
				case "false" => Some(false)
				case _ => None
			}
		}
		object Size extends Parameter {
			def apply(x: options.Size) = x.toString
			def unapply(x: Any): Option[options.Size] = x match {
				case x: String => size(x)
				case _ => None
			}
		}
		object Position extends Parameter {
			def apply(x: options.Position) = x.toString
			def unapply(x: Any): Option[Position] = x match {
				case x: String => pos(x)
				case _ => None
			}
		}
		object Integer extends Parameter {
			def apply(x: Int) = x.toString
			def unapply(x: Any): Option[Int] = x match {
				case s: String => try { Some(s.toInt) } catch { case _: Throwable => None}
				case _ => None
			}
		}
	}
}