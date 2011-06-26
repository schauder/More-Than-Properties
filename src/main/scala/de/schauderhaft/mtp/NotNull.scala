package de.schauderhaft.mtp

trait NotNull[T] extends Validation[T] {
    self : Property[T] =>
    override def validate = nullValidation ::: super.validate
    def nullValidation = if (value == null)
        List("Value must not be <NULL>")
    else
        List()

}