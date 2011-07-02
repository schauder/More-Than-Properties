package de.schauderhaft.mtp

class Property[T](var value : T) {
    var listeners = List[T => Unit]()

    def apply() = value

    def :=(aValue : T) {
        if (value != aValue) {
            value = aValue
            fireEvent
        }
    }

    def registerListener(l : T => Unit) {
        listeners = l :: listeners
    }

    private def fireEvent {
        listeners.foreach(_(value))
    }

    override def toString() : String = "Property[" + value + "]"
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
