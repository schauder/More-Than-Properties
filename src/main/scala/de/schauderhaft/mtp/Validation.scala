package de.schauderhaft.mtp

trait Validation[T] {
    self : Property[T] =>
    import Property._

    lazy val validationMessages : Property[List[String]] = validate
    lazy val valid : Property[Boolean] = Property(validationMessages.isEmpty)

    protected def validate : List[String] = List()

    self.registerListener(value => {
        validationMessages := validate
        valid := validationMessages.isEmpty
    })

}
