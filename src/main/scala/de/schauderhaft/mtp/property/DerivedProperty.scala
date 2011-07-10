package de.schauderhaft.mtp.property

class DerivedProperty[T, S](delegate : Property[S])(func : S => T) extends Observable[T] {
    private var lastValue = func(delegate())

    delegate.registerListener(x => {
        val neu = func(x)
        if (neu != lastValue) {
            lastValue = neu
            fireEvent(neu)
        }
    })

    def apply() : T = lastValue

}