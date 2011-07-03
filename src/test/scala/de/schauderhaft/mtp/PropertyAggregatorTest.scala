package de.schauderhaft.mtp

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class PropertyAggregatorTest extends FunSuite with ShouldMatchers {

    test("a PropertyAggregator without properties is valid") {
        val aggregate = new AnyRef() with Aggregator
        aggregate.valid() should be(true)
    }

    test("a PropertyAggregator with an invalid property is not valid") {
        val aggregate = new AnyRef() with Aggregator {
            val notValid = new Property[String]("aaaaaaa") with Length { max = 3 }
        }
        aggregate.valid() should be(false)
    }

}
