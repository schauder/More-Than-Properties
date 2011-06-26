package de.schauderhaft.mtp

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class SizeTest extends FunSuite with ShouldMatchers {

    test("Max size validation complains when value larger than max") {
        val property = new Property(24) with Size { max = 23 }

        property.validationMessages() should be(List("Must not be larger than 23"))
    }

    test("Max size validation complains when value larger than max, with correct value for max") {
        val property = new Property(24) with Size { max = 12 }

        property.validationMessages() should be(List("Must not be larger than 12"))
    }

    test("Max size validation does not complain when value equal to max") {
        val property = new Property(5) with Size { max = 5 }

        property.validationMessages() should be('empty)
    }

    test("Max size validation does not complain when value is null") {
        val property = new Property(null.asInstanceOf[Int]) with Size { max = 5 }

        property.validationMessages() should be('empty)
    }
    test("Min size validation complains when value smaller than min") {
        val property = new Property(22) with Size { min = 23 }

        property.validationMessages() should be(List("Must not be smaller than 23"))
    }

    test("Min size validation complains when value smaller then min, with correct value for min") {
        val property = new Property(6) with Size { min = 12 }

        property.validationMessages() should be(List("Must not be smaller than 12"))
    }

    test("Min size validation does not complain when value equal to min") {
        val property = new Property(5) with Size { min = 5 }

        property.validationMessages() should be('empty)
    }

    test("Min size validation does not complain when value is null") {
        val property = new Property(null.asInstanceOf[Int]) with Size { min = 5 }

        property.validationMessages() should be('empty)
    }

    test("Size Validation does not hide other Validations") {
        trait Dummy extends Validation[Int] {
            self : Property[Int] =>
            override def validate = List("dummy") ::: super.validate
        }

        val property = new Property(23) with Dummy with Size
        property.validationMessages() should be(List("dummy"))
    }

    test("int spike") {
        val myNull : Int = null.asInstanceOf[Int]
    }
}
