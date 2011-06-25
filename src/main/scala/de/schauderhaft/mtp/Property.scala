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
    implicit def apply[T](t : T) : Property[T] =
        new Property(t : T)

    implicit def toT[T](p : Property[T]) : T = p()
}

trait Validation {
    self : Property[String] =>
    import Property._

    val valid : Property[Boolean] = Property(validate.isEmpty)
    val validationMessages : Property[List[String]] = validate

    protected def validate : List[String] = List()

    self.registerListener(value => {
        validationMessages := validate
        valid := validationMessages.isEmpty
    })
}
