package de.schauderhaft.mtp.demo

import de.schauderhaft.mtp.property._

class PersonApplication() {
    def start { browser := Some(new PersonBrowser(this)) }
    val browser : Property[Option[PersonBrowser]] = None
    val editors : Property[List[PersonEditor]] = List()

    def showUser() { editors := new PersonEditor :: editors() }
}

object PersonApplication {
    def apply(repository : PersonRepository) = new PersonApplication
}