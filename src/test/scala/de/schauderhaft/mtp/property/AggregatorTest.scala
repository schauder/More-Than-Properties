package de.schauderhaft.mtp.property

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class AggregatorTest extends FunSuite with ShouldMatchers {
    test("you can call the @@ operator"){
        class Example extends Aggregator{
            val prop = new Property("hallo").@@
        }
    }
}
