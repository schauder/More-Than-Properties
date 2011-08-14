package de.schauderhaft.mtp.table
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.mtp.property.Property

@RunWith(classOf[JUnitRunner])
class ColumnsTest extends FunSuite with ShouldMatchers {

    test("column(0) returns the value of the first column"){
        val property = new Property("x") with Columns{ cols = Seq("y")}
        property.column(0) should be ("y")
    }
    
    test("column(1) returns the value of the second column"){
    	val property = new Property("x") with Columns{ cols = Seq("y","z")}
    	property.column(0) should be ("y")
    	property.column(1) should be ("z")
    }
    test("column() has access to the underlying Property"){
    	val property = new Property("x") with Columns{ cols = Seq("y","z")}
    	property.column(0) should be ("y")
    	property.column(1) should be ("z")
    }
}