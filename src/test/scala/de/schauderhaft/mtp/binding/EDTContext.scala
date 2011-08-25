package de.schauderhaft.mtp.binding
import javax.swing.SwingUtilities
import org.scalatest.Suite
import org.scalatest.AbstractSuite

trait EDTContext extends AbstractSuite {
    self : Suite =>

    abstract override def withFixture(aTest : NoArgTest) {
        var throwable : Option[Throwable] = None

        def superFixture(test : NoArgTest) = super.withFixture(test)

        SwingUtilities.invokeAndWait(new Runnable() {
            override def run() {
                try {
                    EDTContext.super.withFixture(aTest)
                } catch {
                    case e : Throwable => {
                        throwable.synchronized(throwable = Some(e))
                    }
                }
            }
        })

        throwable.synchronized {
            throwable match {
                case Some(t) => throw t
                case None    =>
            }
        }
    }
}