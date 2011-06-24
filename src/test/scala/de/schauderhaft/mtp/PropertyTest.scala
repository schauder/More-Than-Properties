package de.schauderhaft.mtp

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class PropertyTest extends FunSuite with ShouldMatchers {

    test("property returns initial Value") {
        implicit val owner = new AnyRef() with PropertyOwner
        val property = new Property("testValue")
        property() should be("testValue")
    }

    test("when validate finds nothing to complain, valid returns true and the list of validation messages is empty") {
        implicit val owner = new AnyRef() with PropertyOwner
        val property = new Property("testValue") with Validation

        property.valid should be(true)
        property.validationMessages should be('empty)
    }

    test("when validate returns validation messages then valid returns false and validationMessages returns the messages") {
        implicit val owner = new AnyRef() with PropertyOwner
        val messages = List("isn't ok for some reason", "and this reason applies as well")
        val property = new Property("testValue") with Validation {
            override def validate = messages
        }

        property.valid should be(false)
        property.validationMessages should be(messages)

    }
}
