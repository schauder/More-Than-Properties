package de.schauderhaft.mtp.property
import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class ActionTest extends FunSuite with ShouldMatchers {

    test("when an action gets a code block on creation that code block gets executed on perform") {
        var called = false
        val action = Action(called = true)

        action()

        called should be(true)
    }
}