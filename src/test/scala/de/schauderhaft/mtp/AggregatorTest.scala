package de.schauderhaft.mtp

import validation._
import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class AggregatorTest extends FunSuite with ShouldMatchers {

    test("an Aggregator without properties is valid") {
        val aggregate = new AnyRef() with Aggregator
        aggregate.valid() should be(true)
    }

    test("an Aggregator with an invalid property is not valid") {
        val aggregate = new AnyRef() with Aggregator {
            val notValid = new Property[String]("aaaaaaa", this) with Length { max = 3 }
        }
        aggregate.valid() should be(false)
    }

    test("an Aggregator with an invalid property becomes valid when the property becomes valid") {
        val aggregate = new AnyRef() with Aggregator {
            val prop = new Property[String]("aaaaaaa", this) with Length { max = 3 }
        }

        aggregate.prop := "bb"
        println(aggregate.prop.valid)
        aggregate.valid() should be(true)
    }

    test("a listener registered with an Aggregator gets informed about changes in the validity") {

        val aggregate = new AnyRef() with Aggregator {
            val prop = new Property[String]("aaaaaaa", this) with Length { max = 3 }
        }

        var called = false

        //aggregate.valid.registerListener(_ => { called = true })

        aggregate.prop := "bb"
        aggregate.valid() should be(true)
    }

}
