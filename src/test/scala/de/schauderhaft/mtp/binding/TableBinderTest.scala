package de.schauderhaft.mtp.binding

import de.schauderhaft.mtp.property._
import javax.swing.{ Action => _, _ }
import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._
import scala.collection.immutable._
import javax.swing.table.TableModel
import de.schauderhaft.mtp.table._

@RunWith(classOf[JUnitRunner])
class TableBinderTest extends FunSuite with ShouldMatchers {

    /**
     * checks a table model agains a sequence of values
     */
    def check(model : TableModel, values : Seq[AnyRef]) {
        model.getColumnCount() should be(1)
        model.getRowCount() should be(values.size)
        for (i <- 0 to values.size - 1; v = values(i))
            model.getValueAt(i, 0) should be(v)
    }

    test("binding an empty Seq Property to a JTable results in an empty table") {
        val property = new Property(Seq[String]())
        val table = new JTable
        Binder.bind(property, table)

        check(table.getModel, Seq())
    }

    test("binding a single element Seq[String] Property to a JTable results in that element in the single cell") {
        val property = new Property(Seq("hello"))
        val table = new JTable
        Binder.bind(property, table)

        check(table.getModel(), Seq("hello"))
    }

    test("binding a n element Seq[String] Property to a JTable results in the elements being values of the cells of a single column") {
        val property = new Property(Seq("hallo", "ollah", "hi"))
        val table = new JTable
        Binder.bind(property, table)

        check(table.getModel(), Seq("hallo", "ollah", "hi"))
    }

    test("binding a Property with Columns uses the Columns") {
        val property = new Property(Seq("hallo", "ollah", "hi")) with Columns
        pending
        val table = new JTable
        Binder.bind(property, table)

        check(table.getModel(), Seq("ollah", "hallo", "ih"))
    }
}