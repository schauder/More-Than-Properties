package de.schauderhaft.mtp.binding

import javax.swing.table.AbstractTableModel

class PropertyTableModel extends AbstractTableModel {
    override def getValueAt(x : Int, y : Int) : AnyRef = "hallo"
    override def getColumnCount() : Int = 1
    override def getRowCount() : Int = 0
}