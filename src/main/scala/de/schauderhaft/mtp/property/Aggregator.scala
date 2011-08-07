package de.schauderhaft.mtp.property

import de.schauderhaft.mtp.validation._
/**
 * Aggregates the collective properties of the contained/registered properties
 */
trait Aggregator {
    implicit val aggregator : Aggregator = this

    def register(p : Property[_]) {}

}