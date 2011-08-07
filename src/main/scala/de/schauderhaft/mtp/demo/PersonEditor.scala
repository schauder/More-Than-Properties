package de.schauderhaft.mtp.demo

import de.schauderhaft.mtp.validation._
import de.schauderhaft.mtp.property.Property._
import de.schauderhaft.mtp.property._

/**
 * Represents the data of a person. Since it is a pure case class and there fore immutable its perfectly
 * suitable to be passed around inside a GUI application, including but not limited to transfer to a server for storage.
 */
case class Person(fristname : String, lastname : String, age : Int)

/**
 * An editor for a person, i.e. the logic of a (G)UI. It should contain information about everything an
 * editor needs to know about a Person. Therefore it should be possible to generate a UI for it without further manual work.
 *
 * This doesn't mean you have to or should generate your GUI. Thats of course depends on the requirements of your application
 *
 * The PersonEditor is an Aggregator, that means that the Properties that make up the PersonEditor can register themselves,
 * thereby making their status available in an aggregated form to the PersonEditor. This is currently implemented for
 * Validation and used in the save Action which is only enabled when all (registered) properties are valid.
 */
class PersonEditor extends ValidAggregator {
    /**
     * A Name is a Property of type String with a minimum length of 3 and a maximum length of 20
     *
     * Name is an example how you can abstract over the specification of properties.
     */
    private class Name(value : String) extends Property(value) with Length { min = 3; max = 20 }

    /**
     * firstname is a Name and therefore the restrictions for legal Names apply.
     * It is a registered Property therefore only when the firstname is valid can a person be saved.
     */
    val firstname = new Name("").@@

    /**
     * lastname is another Name property. See firstname and Name for more details
     */
    val lastname = new Name("").@@

    /**
     * a Property of type Int with initial value of 20, a minimum value of 10 and a maximum value of 110.
     *
     * Not that since Int doesn't allow <code>null</code> values, no <code>NotNull</code> trait is necessary.
     */
    val age = new Property(20) with Size { min = 10; max = 110 }.@@

    /**
     * in a real application this would save the Person, which gets created from the various Properties.
     * In this example the Person is just printed to Stdout.
     *
     * The action (or the JButton which will represent it) will only be enabled when all properties are valid.
     */
    val save = new Action(println(Person(firstname, lastname, age))) with Enabled { enabled = valid }
}