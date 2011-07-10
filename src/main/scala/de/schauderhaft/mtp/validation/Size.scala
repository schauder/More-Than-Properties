package de.schauderhaft.mtp.validation

import de.schauderhaft.mtp.property._

trait Size extends Validation[Int] {
    self : Property[Int] =>

    var max : Int = Int.MaxValue
    var min : Int = Int.MinValue

    override def validate : List[String] = validateSize ::: super.validate

    def validateSize = validateMax ::: validateMin

    def validateMin = if (value >= min) List() else List("Must not be smaller than " + min)

    def validateMax = if (value <= max) List() else List("Must not be larger than " + max)
}