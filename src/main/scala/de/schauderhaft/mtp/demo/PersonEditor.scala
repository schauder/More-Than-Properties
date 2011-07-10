package de.schauderhaft.mtp.demo

import de.schauderhaft.mtp.validation._
import de.schauderhaft.mtp.Property._
import de.schauderhaft.mtp._

case class Person(fristname : String, lastname : String, age : Int)

class PersonEditor extends Aggregator {
    class Name(value : String) extends Property(value) with Length { min = 3; max = 20 }
    val firstname = new Name("").@@
    val lastname = new Name("").@@
    val age = new Property(20) with Size { min = 10; max = 110 }.@@
    // in a real project you would 
    // access the database or a service layer 
    // in order to save your object
    val save = new Action(println(Person(firstname, lastname, age))) with Enabled { enabled = aggregator.valid }
}