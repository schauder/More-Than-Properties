package de.schauderhaft.mtp

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class NotNullTest extends FunSuite with ShouldMatchers {

    test("NotNull does not complain about NotNull values") {
        val property = new Property("test") with NotNull[String]
        property.validationMessages() should be('empty)
    }

    test("NotNull does complain about Null values") {
        val property = new Property[String](null) with NotNull[String]
        property.validationMessages() should be(List("Value must not be <NULL>"))
    }

    test("Length Validation does not hide other Validations") {
        trait Dummy extends Validation[String] {
            self : Property[String] =>
            override def validate = List("dummy") ::: super.validate
        }

        val property = new Property("test") with Dummy with NotNull[String]
        property.validationMessages() should be(List("dummy"))
    }
}
