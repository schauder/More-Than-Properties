package de.schauderhaft.mtp
import javax.swing._

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

/**
 * Tests the binding of Int Properties to TextFields
 */
@RunWith(classOf[JUnitRunner])
class BinderIntTest extends FunSuite with ShouldMatchers {

    def setup(initValue : Int) = {
        (Property(initValue), new JTextField())
    }

    test("binding a property to a textfield sets the textfield with value 23") {
        val (property, textField) = setup(23)
        Binder.bind(property, textField)

        textField.getText should be("23")
    }

    test("binding a property to a textfield sets the textfield with value 42") {
        val (property, textField) = setup(42)
        Binder.bind(property, textField)

    }

    test("changing a bound property changes the textfield") {
        val (property, textField) = setup(42)
        Binder.bind(property, textField)

        property := 11

        textField.getText should be("11")
    }

    test("changing the textfield changes the bound property") {
        val (property, textField) = setup(42)
        Binder.bind(property, textField)

        textField.setText("11")

        property() should be(11)
    }

    test("changing the textfield to a not parsable value does not change the bound property") {
        val (property, textField) = setup(42)
        Binder.bind(property, textField)

        textField.setText("zwölf")

        property() should be(42)
    }

}