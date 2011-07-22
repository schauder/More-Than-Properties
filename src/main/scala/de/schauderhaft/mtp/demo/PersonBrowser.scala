package de.schauderhaft.mtp.demo

import de.schauderhaft.mtp.property._

class PersonBrowser(app : PersonApplication) {
    val create = new Action(app.showUser())
    val persons : Property[Seq[Person]] = List();
}