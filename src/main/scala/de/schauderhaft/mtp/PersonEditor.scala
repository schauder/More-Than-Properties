package de.schauderhaft.mtp

import Property._

case class Person(fristname : String, lastname : String)

class PersonEditor extends PropertyOwner {
    val firstname : Property[String] = ""
    val lastname : Property[String] = ""
    // in a real project you would 
    // access the database or a service layer 
    // in order to save your object
    def save() = println(Person(firstname, lastname))

}