package de.schauderhaft.mtp

class Action(act : => Unit) {
    def apply() { act }
}
object Action {
    def apply(act : => Unit) = new Action(act)
}