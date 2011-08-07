package de.schauderhaft.mtp.meta

import org.scalatest._
import org.scalatest.matchers._
import org.junit.runner.RunWith
import org.scalatest.junit._

import jdepend.framework._

@RunWith(classOf[JUnitRunner])
class DependencyTest extends FunSuite with ShouldMatchers {
    val jdepend = new JDepend()
    jdepend.addDirectory("./target/scala_2.9.0/test-classes")
    jdepend.addDirectory("./target/scala_2.9.0/classes")
    jdepend.analyzeInnerClasses(true)
    val packages = jdepend.analyze()

    test("the project has at least 5 packages") {
        packages.size() should be >= 5
    }

    test("the project has no dependency cycles on package level") {
        printCycles
        jdepend.containsCycles() should be(false) // packages mit zyklen ausgeben
    }

    def printCycles() {
        import scala.collection.JavaConversions._
        def printPackage(p : JavaPackage) {
            println(p.getName)
            for (pa : JavaPackage <- p.getAfferents().toSet.asInstanceOf[Set[JavaPackage]]; if (pa.containsCycle()))
                println("<--- " + pa.getName())
            for (pe : JavaPackage <- p.getEfferents().toSet.asInstanceOf[Set[JavaPackage]]; if (pe.containsCycle()))
                println("---> " + pe.getName())

        }
        for (p : JavaPackage <- packages.toSet.asInstanceOf[Set[JavaPackage]]; if (p.containsCycle)) {
            printPackage(p)
        }
    }

    test("the project honours a layer structure") {
        pending
    }

}