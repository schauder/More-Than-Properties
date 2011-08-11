package de.schauderhaft.mtp.binding

import de.schauderhaft.mtp.property._

import javax.swing.{ Action => _, _ }

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._
import scala.collection.immutable._
@RunWith(classOf[JUnitRunner])
class TableBinderTest extends FunSuite with ShouldMatchers {

    test("binding an empty Seq Property to a JTable results in an empty table"){
        val property = new Property(Seq[String]())
        val table = new JTable
        Binder.bind(property, table)
        
        table.getModel().getRowCount() should be (0)
    }
    		
     
    test("binding an empty Seq Property to a JTable results in a single column table"){
        val property = new Property(Seq[String]())
        val table = new JTable
        Binder.bind(property, table)
        
        table.getModel().getColumnCount() should be (1)
    }
    
    test("binding a single element Seq[String] Property to a JTable results in that element in the single cell"){
        val property = new Property(Seq("hello"))
        val table = new JTable
        Binder.bind(property, table)
        
        table.getModel().getColumnCount() should be (1)
        table.getModel().getRowCount() should be (1)
        table.getModel().getValueAt(0,0) should be ("hello")
    }
    
    test("binding a n element Seq[String] Property to a JTable results in the elements being values of the cells of a single column"){
        val property = new Property(Seq("hallo", "ollah", "hi"))
        val table = new JTable
        Binder.bind(property, table)

        table.getModel().getColumnCount() should be (1)
        table.getModel().getRowCount() should be (3)
        table.getModel().getValueAt(0,0) should be ("hallo")
        table.getModel().getValueAt(1,0) should be ("ollah")
        table.getModel().getValueAt(2,0) should be ("hi")
    }
}