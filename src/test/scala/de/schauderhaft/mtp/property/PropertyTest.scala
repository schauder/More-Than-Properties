package de.schauderhaft.mtp.property

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class PropertyTest extends FunSuite with ShouldMatchers {

    test("property returns initial Value") {
        val property = new Property("testValue")
        property() should be("testValue")
    }

    test("toString of a property with a value 12 returns 'Property[12]'") {
        new Property(12).toString should be("Property[12]")
    }

    test("property() returns the value set with := ") {
        val property = new Property("testValue")
        property := "otherValue"
        property() should be("otherValue")
    }

    test("toString of a property with a value 'zwölf' returns 'Property[zwölf]'") {
        new Property("zwölf").toString should be("Property[zwölf]")
    }

}
