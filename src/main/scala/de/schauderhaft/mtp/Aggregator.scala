package de.schauderhaft.mtp

import validation._
/**
 * Aggregates the collective properties of the contained/registered properties
 */
trait Aggregator extends Valid {
    val aggregator : Aggregator = this
    val valid = new Property(true);
    val validationMessages = new Property(List[String]())
    var validations = List[Validation[_]]()

    def register(p : Property[_]) {
        p match {
            case v : Validation[_] => registerValidation(v)
        }
    }

    private def registerValidation(v : Validation[_]) {
        v.valid.registerListener(_ => updateValidation())
        validations = v :: validations
        updateValidation()
    }

    def updateValidation() {
        valid := validations.forall(_.valid())
    }
}