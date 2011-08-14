package de.schauderhaft.mtp.property

class Property[T](private val v : T) extends Value[T](v) with  Mutable[T] {
    override def toString() : String = "Property[" + value + "]"

    def @@(implicit aggregator : Aggregator) : Property[T] = {
        aggregator.register(this)
        this
    }
}

class Value[T](protected var value : T) {
    def apply() = value
}

trait Mutable[T] extends Observable[T] {
    self : Value[T] =>

    def :=(aValue : T) =
        if (value != aValue) {
            value = aValue
            fireEvent(value)
        }
}

object Property {
    implicit def apply[T](t : T) : Property[T] = {
        if (t.isInstanceOf[Property[_]]) {
            new Throwable().printStackTrace()
        }
        new Property(t : T)
    }

    implicit def toT[T](p : Property[T]) : T = p()
}

object NullAggregator extends Aggregator {
    override def register(p : Property[_]) {}
}
