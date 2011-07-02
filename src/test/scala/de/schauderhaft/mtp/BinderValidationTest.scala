package de.schauderhaft.mtp
import javax.swing._

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class BinderValidationTest extends FunSuite with ShouldMatchers {

    def setup(initValue : Boolean) =
        (new Property(initValue) with Validation[Boolean] {
            override def validate = {
                if (value) List()
                else List("validation failed")
            }
        }, new JLabel())

    test("after binding a valid Property the label is not visible") {
        val (property, label) = setup(true)
        Binder.bindValidation(property, label)
        label.isVisible should be(false)
    }

    test("after binding an invalid Property the label is visible") {
        val (property, label) = setup(false)
        Binder.bindValidation(property, label)
        label.isVisible should be(true)
    }

    test("when a property becomes valid the label becomes invisible") {
        val (property, label) = setup(false)
        Binder.bindValidation(property, label)
        property := true
        label.isVisible should be(false)
    }

    test("when a property becomes invalid the label becomes visible") {
        val (property, label) = setup(true)
        Binder.bindValidation(property, label)
        property := false
        label.isVisible should be(true)
    }

}