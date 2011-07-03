package de.schauderhaft.mtp

/**
 * Aggregates the collective properties of the contained/registered properties
 */
trait Aggregator {
    implicit val aggregator : Aggregator = this

    var properties = List[Property[_]]()

    def register(p : Property[_]) {
        println("register")
        properties = p :: properties
    }

    def valid() = properties.forall(_ match {
        case v : Validation[_] => v.valid()
        case _                 => true
    })
}