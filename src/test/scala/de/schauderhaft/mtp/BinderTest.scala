package de.schauderhaft.mtp
import javax.swing._

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class BinderTest extends FunSuite with ShouldMatchers {

    def setup(initValue : String) = {
        val owner = new AnyRef with PropertyOwner
        (Property(initValue)(owner), new JTextField())
    }

    test("binding a property to a textfield sets the textfield") {
        val (property, textField) = setup("text")
        Binder.bind(property, textField)

        textField.getText should be("text")
    }

    test("binding a property with null value sets the textfield to an empty string") {
        val (property, textField) = setup(null)
        Binder.bind(property, textField)

        textField.getText should be("")
    }

    test("changing a property sets the textfield to the new value") {
        val (property, textField) = setup(null)
        Binder.bind(property, textField)

        property := "23"

        textField.getText should be("23")
    }

    test("changing a property to null sets the textfield to the new value") {
        val (property, textField) = setup("sometext")
        Binder.bind(property, textField)

        property := null

        textField.getText should be("")
    }

    test("changing the textField sets the property to the new value") {
        val (property, textField) = setup(null)
        Binder.bind(property, textField)

        textField.setText("23")

        property() should be("23")
    }

    test("changing the textField sets the property to the new value (special case -> empty textfield") {
        val (property, textField) = setup("123")
        Binder.bind(property, textField)

        textField.setText("123")
        textField.setText("")

        property() should be("")
    }

    test("when the property throws exceptions the binding is not harmed") {
        class FailingProperty[T](val init : T) extends Property[T](init) {
            override def :=(value : T) = {
                if (value == "42") throw new IllegalArgumentException("fake exception")
                else super.:=(value)
            }
        }

        val property = new FailingProperty("start")
        property() should be("start")
        val textField = new JTextField()
        Binder.bind(property, textField)
        property() should be("start")

        val iae = intercept[IllegalArgumentException] {
            textField.setText("42")
        }
        iae.getMessage should be("fake exception")
        // although I'd like to it looks like we can't ensure this, since a change to the 
        // value of a textfield triggers multiple events, the first being a 'remove everything'
        // and there isn't any information available, that this is part of a series of events
        //        property() should be("start")

        textField.setText("zwölf")
        property() should be("zwölf")
    }

    test("when the textfield throws exceptions the binding is not harmed") { pending }

    test("binding a method to a button invokes the bound method") {
        var invoked = false
        val button = new JButton()
        Binder.bind({ invoked = true }, button)

        button.doClick()

        invoked should be(true)
    }
}