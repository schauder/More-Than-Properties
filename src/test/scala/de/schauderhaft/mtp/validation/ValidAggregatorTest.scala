package de.schauderhaft.mtp.validation

import de.schauderhaft.mtp.property._
import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class ValidAggregatorTest extends FunSuite with ShouldMatchers {
    test("an Aggregator without properties is valid") {
        val aggregate = new AnyRef() with ValidAggregator
        aggregate.valid() should be(true)
    }
    test("an Aggregator without properties has no validation message") {
        val aggregate = new AnyRef() with ValidAggregator
        aggregate.validationMessages() should be('empty)
    }

    test("an Aggregator with an invalid property is not valid") {
        val aggregate = new AnyRef() with ValidAggregator {
            val notValid = new Property[String]("aaaaaaa") with Length { max = 3 }.@@
        }
        aggregate.valid() should be(false)
    }
    test("an Aggregator with an invalid property has the validation message") {
        val aggregate = new AnyRef() with ValidAggregator {
            val notValid = new Property[String]("aaaaaaa") with Length { max = 3 }.@@
        }
        aggregate.validationMessages().size should be(1)
    }
    test("an Aggregator with two invalid properties has the validation message") {
        val aggregate = new AnyRef() with ValidAggregator {
            val notValid = new Property[String]("aaaaaaa") with Length { max = 3 }.@@
            val notValidEither = new Property[String]("aaaaaaa") with Length { max = 3 }.@@
        }
        aggregate.validationMessages().size should be(2)
    }
    test("an Aggregator with an invalid property becomes valid when the property becomes valid") {
        val aggregate = new AnyRef() with ValidAggregator {
            val prop = new Property[String]("aaaaaaa") with Length { max = 3 }.@@
        }

        aggregate.prop := "bb"
        aggregate.valid() should be(true)
    }

    test("a listener registered with an Aggregator gets informed about changes in the validity") {

        val aggregate = new AnyRef() with ValidAggregator {
            val prop = new Property[String]("aaaaaaa") with Length { max = 3 }.@@
        }

        var called = false

        aggregate.valid.registerListener(_ => { called = true })

        aggregate.prop := "bb"
        called should be(true)
    }

}