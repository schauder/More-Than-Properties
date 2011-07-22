package de.schauderhaft.mtp.demo;

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

/**
 * Integration tests for a little PersonEditor
 */
@RunWith(classOf[JUnitRunner])
class PersonApplicationTest extends FunSuite with ShouldMatchers {
    test("starting the application opens the PersonBrowser") {
        val app = PersonApplication(new InMemoryPersonRepository())
        import app._
        browser() should be(None)
        start
        browser().isDefined should be(true)
    }

    test("starting the application opens zero PersonEditors") {
        val app = PersonApplication(new InMemoryPersonRepository())
        import app._
        editors() should be('empty)
        start
        editors() should be('empty)
    }

    test("clicking create in the Browser opens a new editor") {
        val app = PersonApplication(new InMemoryPersonRepository())
        app.start
        val personBrowser = app.browser.getOrElse(fail("there should be a PersonBrowser here"))
        personBrowser.create()
        app.editors() should have size (1)
    }

    test("clicking create in the Browser leaves the browser alone") {
        val app = PersonApplication(new InMemoryPersonRepository())
        app.start
        val personBrowser = app.browser.getOrElse(fail("there should be a PersonBrowser here"))
        personBrowser.create()
        app.browser().get should be(personBrowser)
        personBrowser.persons() should have size (0)

    }
}
