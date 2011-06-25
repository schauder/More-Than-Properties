package de.schauderhaft.mtp

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class LengthTest extends FunSuite with ShouldMatchers {

    test("Minimum length Validation complains about Strings being to short") {
        val property = new Property("test") with Length { min = 5 }
        property.validationMessages() should be(List("Should have minimum length 5"))
    }

    test("Minimum length Validation doesn't complains about Strings being to short if they aren't") {
        val property = new Property("test") with Length { min = 4 }
        property.validationMessages() should be('empty)
    }

    test("Minimum length Validation complains about Strings being to short with correct length") {
        val property = new Property("test") with Length { min = 6 }

        property.validationMessages() should be(List("Should have minimum length 6"))
    }

    test("given a property with value null and length validation minimum length does not apply") {
        val property = new Property(null.asInstanceOf[String]) with Length { min = 6 }

        property.validationMessages() should be('empty)
    }

    test("given a property with value null and length validation maximum length does not apply") {
        val property = new Property(null.asInstanceOf[String]) with Length { max = 6 }

        property.validationMessages() should be('empty)
    }

    test("Maximum length Validation complains about Strings being to long") {
        val property = new Property("test") with Length { max = 3 }
        property.validationMessages() should be(List("Should have maximum length 3"))
    }

    test("Maximum length Validation complains about Strings being to long with correct length") {
        val property = new Property("test") with Length { max = 2 }
        property.validationMessages() should be(List("Should have maximum length 2"))
    }

    test("Maximum length Validation does not complains about Strings being to long if they aren't") {
        val property = new Property("test") with Length { max = 5 }
        property.validationMessages() should be('empty)
    }

    test("Length Validation does not hide other Validations") {
        trait Dummy extends Validation[String] {
            self : Property[String] =>
            override def validate = List("dummy") ::: super.validate
        }

        val property = new Property("test") with Dummy with Length
        property.validationMessages() should be(List("dummy"))
    }
}
