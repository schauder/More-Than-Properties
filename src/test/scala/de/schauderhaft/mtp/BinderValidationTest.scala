package de.schauderhaft.mtp
import javax.swing._

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class BinderValidationTest extends FunSuite with ShouldMatchers {

    val validationText = "validation failed"
    val otherValidation = "otherValidation"

    def setup(initValue : Boolean, validationTexts : List[String] = List(validationText)) =
        (new Property(initValue) with Validation[Boolean] {
            override def validate = {
                if (value) List()
                else validationTexts
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

    test("after binding a valid Property the tooltip should be empty") {
        val (property, label) = setup(true)
        Binder.bindValidation(property, label)
        label.getToolTipText should be(null)
    }

    test("after binding an invalid Property the tooltip should contain the validationText") {
        val (property, label) = setup(false)
        Binder.bindValidation(property, label)
        label.getToolTipText should be(validationText)
    }

    test("after binding an invalid Property the tooltip should contain all validation messages") {
        val (property, label) = setup(false, List(validationText, otherValidation))
        Binder.bindValidation(property, label)
        label.getToolTipText.contains(validationText) should be(true)
        label.getToolTipText.contains(otherValidation) should be(true)
    }

    test("after binding an invalid Property the tooltip should contain a linebreak") {
        val (property, label) = setup(false, List(validationText, otherValidation))
        Binder.bindValidation(property, label)
        label.getToolTipText.contains("\n") should be(true)
    }

    test("when a property becomes valid the tooltip becomes empty") {
        val (property, label) = setup(false)
        Binder.bindValidation(property, label)
        property := true
        label.getToolTipText should be(null)
    }

    test("when a property becomes invalid the tooltip contains the validation messages and a linebreak") {
        val (property, label) = setup(true, List(validationText, otherValidation))
        Binder.bindValidation(property, label)
        property := false
        label.getToolTipText.contains(validationText) should be(true)
        label.getToolTipText.contains(otherValidation) should be(true)
        label.getToolTipText.contains("\n") should be(true)
    }
}