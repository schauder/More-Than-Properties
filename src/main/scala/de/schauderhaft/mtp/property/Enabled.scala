package de.schauderhaft.mtp

trait Enabled {
    var enabled : Property[Boolean] = new Property(true)
}