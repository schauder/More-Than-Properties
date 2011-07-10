package de.schauderhaft.mtp.property

import de.schauderhaft.mtp.validation._
/**
 * Aggregates the collective properties of the contained/registered properties
 */
trait Aggregator extends Valid {
    implicit val aggregator : Aggregator = this
    val valid = new Property(true);
    val validationMessages = new Property(List[String]())
    var validations = List[Validation[_]]()

    def register(p : Property[_]) {
        p match {
            case v : Validation[_] => registerValidation(v)
            // TODO needs test
            case _                 =>
        }
    }

    private def registerValidation(v : Validation[_]) {
        v.valid.registerListener(_ => updateValidation())
        validations = v :: validations
        updateValidation()
    }

    def updateValidation() {
        validationMessages := validations.flatMap(_.validationMessages())
        valid := validations.forall(_.valid())
    }
}