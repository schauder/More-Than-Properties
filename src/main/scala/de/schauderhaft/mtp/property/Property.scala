package de.schauderhaft.mtp.property

class Property[T](var value : T) extends Observable[T] {

    def apply() = value

    def :=(aValue : T) {
        if (value != aValue) {
            value = aValue
            fireEvent(value)
        }
    }

    override def toString() : String = "Property[" + value + "]"

    def @@(implicit aggregator : Aggregator) : Property[T] = {
        aggregator.register(this)
        this
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
