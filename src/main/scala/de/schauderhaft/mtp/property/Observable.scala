package de.schauderhaft.mtp.property

trait Observable[T] {
    var listeners = List[T => Unit]()

    def registerListener(l : T => Unit) {
        listeners = l :: listeners
    }

    protected def fireEvent(v : T) {
        listeners.foreach(_(v))
    }

}