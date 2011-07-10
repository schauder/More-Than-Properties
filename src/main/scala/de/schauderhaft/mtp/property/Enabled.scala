package de.schauderhaft.mtp.property

trait Enabled {
    var enabled : Property[Boolean] = new Property(true)
}