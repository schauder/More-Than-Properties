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
}

object Property {
    implicit def apply[T](t : T)(implicit owner : PropertyOwner) : Property[T] =
        new Property(t : T)

    implicit def toT[T](p : Property[T]) : T = p()
}

trait Validation {
    protected def validate : List[String] = List()
    val valid = validate.isEmpty
    val validationMessages = validate
}

trait PropertyOwner {
    implicit val THE_OWNER = this
}