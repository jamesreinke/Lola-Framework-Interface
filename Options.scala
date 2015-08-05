package interface.options

import interface.Encoder

/*
	Option References
*/
sealed trait Size extends Encoder
case object XSmall extends Size {
	val name = "GrWJ5MG6ZP"
}
case object Small extends Size {
	val name = "VfFyg9dK5N"
}
case object Medium extends Size {
	val name = "Xy3Zumo479"
}
case object Large extends Size {
	val name = "ZmrVGqjV7u"
}
case object XLarge extends Size {
	val name = "WepVJl9Jtx"
}

sealed trait TextType extends Encoder {
	val group: String
}
case object NoType extends TextType {
	val name = "iBvd71VPay"
	val group = "text-none"
}
case object Muted extends TextType {
	val name = "ql9thlAlRY"
	val group = "text-muted"
}
case object Primary extends TextType {
	val name = "P9RFcdsNWv"
	val group = "text-primary"
}
case object Success extends TextType {
	val name = "zfvi2rVKQV"
	val group = "text-success"
}
case object Info extends TextType {
	val name = "yMSnCDMHDg"
	val group = "text-info"
}
case object Warning extends TextType {
	val name = "fff5BKmgZO"
	val group = "text-warning"
}
case object Danger extends TextType {
	val name = "zPTiFiqPmy"
	val group = "text-danger"
}

sealed trait Position extends Encoder
case object Top extends Position {
	val name = "5k4Zy1wgZi"
}
case object Right extends Position {
	val name = "V69JbVoFeR"
}
case object Bottom extends Position {
	val name = "qLhNeYlsrI"
}
case object Left extends Position {
	val name = "4FICX7f9Xm"
}
/*
	Animation function options for jQuery CSS animations
*/
sealed trait Animation extends Encoder {
	val group: String
}
case object Swing extends Animation {
	val name = "RVXjSQzLfd"
	val group = "swing"
}
case object Linear extends Animation {
	val name = "paWtCk2D2v"
	val group = "linear"
}
case object InOut extends Animation {
	val name = ""
	val group = "easeInOutQuad"
}
case object In extends Animation {
	val name = ""
	val group = "easeInQuad"
} 
case object Out extends Animation {
	val name = ""
	val group = "easeOutQuad"
}
case object IElastic extends Animation {
	val name = ""
	val group = "easeInElastic"
}
case object OElastic extends Animation {
	val name = ""
	val group = "easeOutElastic"
}
case object IOElastic extends Animation { 
	val name = ""
	val group = "easeInOutElastic"
}
case object IBack extends Animation {
	val name = ""
	val group = "easeInBack"
}
case object OBack extends Animation {
	val name = ""
	val group = "easeOutBack"
}
case object IOBack extends Animation {
	val name = ""
	val group = "easeInOutBack"
}
case object IBounce extends Animation {
	val name = ""
	val group = "easeInBounce"
}
case object OBounce extends Animation {
	val name = ""
	val group = "easeOutBounce"
}
case object IOBounce extends Animation {
	val name = ""
	val group = "easeInOutBounce"
}