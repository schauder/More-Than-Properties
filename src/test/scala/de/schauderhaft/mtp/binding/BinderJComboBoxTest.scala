package de.schauderhaft.mtp.binding

import de.schauderhaft.mtp.property._

import javax.swing._

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

/**
 * Tests the binding of Int Properties to TextFields
 */
@RunWith(classOf[JUnitRunner])
class BinderJComboBoxTest extends FunSuite with ShouldMatchers with EDTContext {

    def setup(initValue : Int) = {
        (Property(initValue), new JTextField())
    }

    
    test("binding a property to a JCombobox sets the value of the Combobox") {
        pending
    }
    
    test("binding a property with ChooseFrom to a JCombobox sets the value of the selection list to the options of the ChooseFrom") {
    	pending
    }
}