package de.schauderhaft.mtp.table

trait Columns {
    protected var cols : Int => Any = identity(_)
	def column(col : Int) = cols(col)
}