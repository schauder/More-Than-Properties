package de.schauderhaft.mtp

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class LayoutSpecificationHorizontalTest extends FunSuite with ShouldMatchers {

    val testdata = List(
        (0, (0, 1), Nil),
        (1, (1, 1), List((1, 1))),
        (2, (2, 1), List((1, 1), (2, 1))))

    testdata.foreach { t =>
        testSize(t._1, t._2)
        testPositionsAndElementSize(t._1, t._3)
    }

    def testSize(elementCount : Int, size : (Int, Int)) {
        test("a horizontal layout of " + elementCount + " has size " + size.toString + ".") {
            HorizontalLayoutSpecification(createElements(elementCount) : _*).size should be(size)
        }
    }

    def testPositionsAndElementSize(elementCount : Int, positions : List[(Int, Int)]) {
        positions.zipWithIndex.foreach { p =>
            testPosition(elementCount, p._1, p._2)
            testElementSize(elementCount, p._2)
        }
    }

    def testPosition(elementCount : Int, p : (Int, Int), elementNumber : Int) {
        test("in a horizontal layout of " + elementCount + " element #" + elementNumber + " has position " + p) {
            val elements = createElements(elementCount)
            HorizontalLayoutSpecification(elements : _*).topLeft(elements(elementNumber)) should be(p)
        }
    }
    def testElementSize(elementCount : Int, elementNumber : Int) {
        test("in a horizontal layout of " + elementCount + " element #" + elementNumber + " has position has size (1,1)") {
            val elements = createElements(elementCount)
            HorizontalLayoutSpecification(elements : _*).size(elements(elementNumber)) should be((1, 1))
        }
    }
    def createElements(amount : Int) : Seq[AnyRef] = {
        (1 to amount).map(i => new AnyRef())
    }
}