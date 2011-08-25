package de.schauderhaft.mtp.property

trait ChooseFrom[T] {
    val options = new Value[Seq[T]](Seq())
}