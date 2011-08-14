package de.schauderhaft.mtp.validation

import de.schauderhaft.mtp.property._

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class ValidationTest extends FunSuite with ShouldMatchers {

    test("when validate finds nothing to complain, valid returns true and the list of validation messages is empty") {
        val property = new Property("testValue") with Validation[String]

        property.valid() should be(true)
        property.validationMessages() should be('empty)
    }

    test("when validate returns validation messages then valid returns false and validationMessages returns the messages") {
        val messages = List("isn't ok for some reason", "and this reason applies as well")
        val property = new Property("testValue") with Validation[String] {
            override def validate = messages
        }

        property.valid() should be(false)
        property.validationMessages() should be(messages)
    } 

    test("validate sees the initial value") {
        val property = new Property("testValue") with Validation[String] {
            override def validate = List(value) 
        }

        property.valid() should be(false)
        property.validationMessages() should be(List("testValue"))
    }

    test("validate sees the newly set value") {
        val property = new Property(" oldValue") with Validation[String] {
            override def validate = {
                List(value)
            }
        }

        property := "new value"

        property.valid() should be(false)
        property.validationMessages() should be(List("new value"))
    }

    test("valid property fires event on change") {
        val property = new Property("testValue") with Validation[String] {
            override def validate = if (value != "testValue") List(value) else List()
        }
        var called = false

        val validProperty = property.valid
        validProperty.registerListener({ _ => called = true })

        property := "otherValue"
        called should be(true)
    }

    test("validationMessages property fires event on change") {
        val property = new Property("testValue") with Validation[String] {
            override def validate = List(value)
        }
        var called = false

        val validProperty = property.validationMessages
        validProperty.registerListener({ _ => called = true })

        property := "otherValue"
        called should be(true)
    }

}
