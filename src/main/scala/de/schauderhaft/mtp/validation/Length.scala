package de.schauderhaft.mtp.validation

import de.schauderhaft.mtp._

trait Length extends Validation[String] {
    self : Property[String] =>
    var min : Int = -1
    var max : Int = Int.MaxValue
    override def validate : List[String] = minimumValidation ::: maximumValidation ::: super.validate
    def minimumValidation = if (value != null && value.length < min) List("Should have minimum length " + min) else List()
    def maximumValidation = if (value != null && value.length > max) List("Should have maximum length " + max) else List()
}