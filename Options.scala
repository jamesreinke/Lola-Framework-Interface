package interface.options

import interface.{Encoder, Tag}

/*
	Option References
*/
sealed trait Size extends Encoder
case object XSmall extends Size {
	val tag = new Tag("GrWJ5MG6ZP")
}
case object Small extends Size {
	val tag = new Tag("VfFyg9dK5N")
}
case object Medium extends Size {
	val tag = new Tag("Xy3Zumo479")
}
case object Large extends Size {
	val tag = new Tag("ZmrVGqjV7u")
}
case object XLarge extends Size {
	val tag = new Tag("WepVJl9Jtx")
}

sealed trait TextType extends Encoder {
	val group: String
}
case object NoType extends TextType {
	val tag = new Tag("iBvd71VPay")
	val group = "text-none"
}
case object Muted extends TextType {
	val tag = new Tag("ql9thlAlRY")
	val group = "text-muted"
}
case object Primary extends TextType {
	val tag = new Tag("P9RFcdsNWv")
	val group = "text-primary"
}
case object Success extends TextType {
	val tag = new Tag("zfvi2rVKQV")
	val group = "text-success"
}
case object Info extends TextType {
	val tag = new Tag("yMSnCDMHDg")
	val group = "text-info"
}
case object Warning extends TextType {
	val tag = new Tag("fff5BKmgZO")
	val group = "text-warning"
}
case object Danger extends TextType {
	val tag = new Tag("zPTiFiqPmy")
	val group = "text-danger"
}

sealed trait Position extends Encoder
case object Top extends Position {
	val tag = new Tag("5k4Zy1wgZi")
}
case object Right extends Position {
	val tag = new Tag("V69JbVoFeR")
}
case object Bottom extends Position {
	val tag = new Tag("qLhNeYlsrI")
}
case object Left extends Position {
	val tag = new Tag("4FICX7f9Xm")
}
/*
	Animation function options for jQuery CSS animations
*/
sealed trait Animation extends Encoder {
	val group: String
}
case object Swing extends Animation {
	val tag = new Tag("RVXjSQzLfd")
	val group = "swing"
}
case object Linear extends Animation {
	val tag = new Tag("paWtCk2D2v")
	val group = "linear"
}
case object InOut extends Animation {
	val tag = new Tag("")
	val group = "easeInOutQuad"
}
case object In extends Animation {
	val tag = new Tag("")
	val group = "easeInQuad"
} 
case object Out extends Animation {
	val tag = new Tag("")
	val group = "easeOutQuad"
}
case object IElastic extends Animation {
	val tag = new Tag("")
	val group = "easeInElastic"
}
case object OElastic extends Animation {
	val tag = new Tag("")
	val group = "easeOutElastic"
}
case object IOElastic extends Animation { 
	val tag = new Tag("")
	val group = "easeInOutElastic"
}
case object IBack extends Animation {
	val tag = new Tag("")
	val group = "easeInBack"
}
case object OBack extends Animation {
	val tag = new Tag("")
	val group = "easeOutBack"
}
case object IOBack extends Animation {
	val tag = new Tag("")
	val group = "easeInOutBack"
}
case object IBounce extends Animation {
	val tag = new Tag("")
	val group = "easeInBounce"
}
case object OBounce extends Animation {
	val tag = new Tag("")
	val group = "easeOutBounce"
}
case object IOBounce extends Animation {
	val tag = new Tag("")
	val group = "easeInOutBounce"
}