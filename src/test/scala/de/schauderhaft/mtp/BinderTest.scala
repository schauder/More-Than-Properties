package de.schauderhaft.mtp
import javax.swing._

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class BinderTest extends FunSuite with ShouldMatchers {

    def setup(initValue : String) = {
        (Property(initValue), new JTextField())
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

    test("binding a method to a button and clicking the button invokes the bound bloc") {
        var invoked = false
        val button = new JButton()
        Binder.bind({ invoked = true }, button)

        button.doClick()

        invoked should be(true)
    }

    test("a bound button clicked twice gets executed twice") {
        var invoked = 0
        val button = new JButton()
        Binder.bind({ invoked += 1 }, button)

        button.doClick()
        button.doClick()

        invoked should be(2)
    }

    test("a button bound to a simple method is enabled") {
        var invoked = false
        val button = new JButton()
        Binder.bind({ invoked = true }, button)

        button.isEnabled should be(true)
    }

    test("binding an Action to a button and clicking the button invokes the bound block") {
        var invoked = false
        val button = new JButton()
        Binder.bind(Action(invoked = true), button)

        button.doClick()

        invoked should be(true)
    }

    test("a button bound to a simple method with enabled has the value of the enabled Property") {
        pending
    }

}

object Experiment {
    val func : () => Unit = { () => println("Hallo func") }
    def one(x : => Unit) {
        println("one before")
        x
    }

    def two[T](x : () => Unit with T) {
        println("two before")
        x()
    }

    def main(args : Array[String]) {
        one(println("hallo"))
        two(func)

        one(func()) // doesn't work as desired
        //two({ println("hallo") }) // doesnt compile
        two(() => println("hallo")) // works ok, but is ugly IMHO
    }
}