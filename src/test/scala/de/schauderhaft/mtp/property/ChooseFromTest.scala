package de.schauderhaft.mtp.property

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ChooseFromTest extends FunSuite with ShouldMatchers {
	test("a ChooseFrom provides an empty list with possible values"){
	    val cf = new AnyRef() with ChooseFrom[String]
	    cf.options() should be ('empty)
	}
	
	test("the options of a ChooseFrom can be set on creation"){
	    val cf = new AnyRef() with ChooseFrom[String]{ override val options = new Value(Seq("alpha", "beta","gamma"))}
	    cf.options() should be (Seq("alpha", "beta", "gamma"))
	}
	
	test("the options of a ChooseFrom can be made mutable"){
	    val cf = new AnyRef() with ChooseFrom[String]{ override val options = new Property(Seq("String"))}
	    var eventValue = Seq[String]()
	    cf.options.registerListener( eventValue = _)
	    
	    cf.options := Seq("a","b")
	    
	    eventValue should be (Seq("a", "b"))
	    cf.options() should be (Seq("a", "b"))
	}
} 