package de.schauderhaft.mtp.validation
import de.schauderhaft.mtp.property.Property
import de.schauderhaft.mtp.property.Aggregator

trait ValidAggregator extends Aggregator with Valid {
    val valid = new Property(true);
    val validationMessages = new Property(List[String]())
    var validations = List[Validation[_]]()
    private def registerValidation(v : Validation[_]) {
        v.valid.registerListener(_ => updateValidation())
        validations = v :: validations
        updateValidation()
    }

    def updateValidation() {
        validationMessages := validations.flatMap(_.validationMessages())
        valid := validations.forall(_.valid())
    }
    override def register(p : Property[_]) {

        p match {
            case v : Validation[_] => registerValidation(v)
            // TODO needs test
            case _                 =>
        }
    }
}