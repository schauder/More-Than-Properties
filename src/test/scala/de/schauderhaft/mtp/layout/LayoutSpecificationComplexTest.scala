package de.schauderhaft.mtp.layout

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

import org.junit.runner.RunWith
import org.scalatest.junit._

@RunWith(classOf[JUnitRunner])
class LayoutSpecificationComplexTest extends FunSuite with ShouldMatchers {
    test("Layout of a layout of a single element behaves like a simple layout") {
        val element = new AnyRef()
        val innerSpec = HorizontalLayoutSpecification(element)
        val spec = VerticalLayoutSpecification(innerSpec)

        spec.size should be(1, 1)
        spec.topLeft(element) should be(1, 1)
        spec.size(element) should be(1, 1)
    }

    test("Layout of a layout of a single element behaves like a simple layout, the other way") {
        val element = new AnyRef()
        val innerSpec = VerticalLayoutSpecification(element)
        val spec = HorizontalLayoutSpecification(innerSpec)

        spec.size should be(1, 1)
        spec.topLeft(element) should be(1, 1)
        spec.size(element) should be(1, 1)
    }

    test("layout one on top, two horizontal below") {
        val e1 = new AnyRef()
        val e2 = new AnyRef()
        val e3 = new AnyRef()

        val spec = VerticalLayoutSpecification(e1, HorizontalLayoutSpecification(e2, e3))

        spec.size should be(2, 2)
        spec.topLeft(e1) should be(1, 1)
        spec.size(e1) should be(2, 1)
        spec.topLeft(e2) should be(1, 2)
        spec.size(e2) should be(1, 1)
        spec.topLeft(e3) should be(2, 2)
        spec.size(e3) should be(1, 1)
    }

    test("layout one on the left, two vertical on the right") {
        val e1 = new AnyRef()
        val e2 = new AnyRef()
        val e3 = new AnyRef()

        val spec = HorizontalLayoutSpecification(e1, VerticalLayoutSpecification(e2, e3))

        spec.size should be(2, 2)
        spec.topLeft(e1) should be(1, 1)
        spec.size(e1) should be(1, 2)
        spec.topLeft(e2) should be(2, 1)
        spec.size(e2) should be(1, 1)
        spec.topLeft(e3) should be(2, 2)
        spec.size(e3) should be(1, 1)
    }
}