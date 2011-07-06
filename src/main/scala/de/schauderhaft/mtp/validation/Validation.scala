package de.schauderhaft.mtp.validation

import de.schauderhaft.mtp._

trait Valid {
    val validationMessages : Property[List[String]]
    val valid : Property[Boolean]
}

trait Validation[T] extends Valid {
    self : Observable[T] =>
    import Property._

    override lazy val validationMessages : Property[List[String]] = validate
    override lazy val valid : Property[Boolean] = Property(validationMessages.isEmpty)

    protected def validate : List[String] = List()

    self.registerListener(value => {
        validationMessages := validate
        valid := validationMessages().isEmpty
    })

}
