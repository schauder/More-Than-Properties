package de.schauderhaft.mtp.binding

import javax.swing.table.AbstractTableModel
import de.schauderhaft.mtp.property._

import scala.collection.immutable._

class PropertyTableModel[T : Manifest](property : Property[Seq[T]]) extends AbstractTableModel {
    override def getValueAt(x : Int, y : Int) : AnyRef = property()(x).toString
    override def getColumnCount() : Int = 1
    override def getRowCount() : Int = property().size
}