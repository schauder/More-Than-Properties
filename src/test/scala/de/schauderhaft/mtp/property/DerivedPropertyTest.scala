package de.schauderhaft.mtp.property

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class DerivedPropertyTest extends FunSuite with ShouldMatchers {
    for (v <- List(23, 12, "test", None)) {
        test("a derived property based on identity return value of base property (" + v + ")") {
            new DerivedProperty(new Property(v))(x => x)() should be(v)
        }
    }

    for (v <- List(23, 12)) {
        test("a derived property applies the function to the value of the delegate (" + v + ")") {
            new DerivedProperty(new Property(v))(_ * 2)() should be(2 * v)
        }
    }

    for (v <- List("test", "alfred")) {
        test("a derived property applies the function to the value of the delegate (" + v + ")") {
            new DerivedProperty(new Property(v))(_.length)() should be(v.length)
        }
    }

    test("throws event when value changes") {
        val property = new Property(23)
        val derived = new DerivedProperty(property)(x => 2 * x)
        var value = 0
        derived.registerListener { value = _ }

        property := 3

        value should be(6)
    }

    test("doesn't throw event when resulting value does not change") {
        val property = new Property("test")
        val derived = new DerivedProperty(property)(x => x.length)
        var called = false
        derived.registerListener { _ => called = true }

        property := "beta"

        called should be(false)
    }
}