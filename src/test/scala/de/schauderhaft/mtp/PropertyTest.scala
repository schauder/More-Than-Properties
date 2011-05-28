package de.schauderhaft.mtp

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class PropertyTest extends FunSuite with ShouldMatchers {

    test("property returns initial Value") {
        implicit val owner = new AnyRef() with PropertyOwner
        val property = new Property("testValue")
        property() should be("testValue")
    }
}
