package de.schauderhaft.mtp

abstract class LayoutSpecification(elements : AnyRef*) {
    val width : Int
    val height : Int

    def contains(e : AnyRef) : Boolean = {
        elements.contains(e) ||
            elements.filter(_.isInstanceOf[LayoutSpecification])
            .exists(_.asInstanceOf[LayoutSpecification].contains(e))
    }

    /**
     * the elements of the this layout that are not basic elements, but sublayouts
     */
    protected val subLayouts = elements.filter(_.isInstanceOf[LayoutSpecification]).map(_.asInstanceOf[LayoutSpecification])

    def size : (Int, Int) = {
        (width, height)
    }

    /**
     * the size of the layout element in the context of this LayoutSpecification.
     * The behavior if <code>e</code> is not part
     * of this LayoutSpecification is not defined
     */
    def size(e : AnyRef) : (Int, Int) = optionalSize(e).get

    /**
     * the size of the layout element in the context of this LayoutSpecification, or None if
     * <code>e</code> is not part in the LayoutSpecification
     */
    def optionalSize(e : AnyRef) : Option[(Int, Int)]

    /**
     * the top left corner of the layout element provided as a parameter,
     * or None if <code>e</code> is not part of the LayoutSpecification
     */
    def topLeftOption(e : AnyRef) : Option[Pair[Int, Int]]

    def topLeft(e : AnyRef) : (Int, Int) = topLeftOption(e).get

}

case class VerticalLayoutSpecification(elements : AnyRef*) extends LayoutSpecification(elements : _*) {
    override val width = {
        if (subLayouts.isEmpty) 1
        else subLayouts.map(size(_)._1).max
    }

    override val height = elements.size

    def optionalSize(e : AnyRef) : Option[(Int, Int)] = {
        e match {
            case ls : LayoutSpecification    => Some(ls.size)
            case _ if (elements.contains(e)) => Some((width, 1))
            case _                           => subLayouts.flatMap(_.optionalSize(e)).headOption

        }
    }

    def topLeftOption(e : AnyRef) : Option[Pair[Int, Int]] = {
        val results = for ((elem, i) <- elements.zipWithIndex) yield elem match {
            case _ if (elem == e)         => Some((1, i + 1))
            case ls : LayoutSpecification => addOffset(ls.topLeftOption(e), i)
            case _                        => None
        }
        results.flatten.headOption
    }

    private def addOffset(position : Option[Pair[Int, Int]], offset : Int) = {
        position match {
            case None         => None
            case Some((x, y)) => Some((x, y + offset))
        }
    }
}

case class HorizontalLayoutSpecification(elements : AnyRef*) extends LayoutSpecification(elements : _*) {
    override def topLeftOption(e : AnyRef) : Option[Pair[Int, Int]] = {
        val results = for ((elem, i) <- elements.zipWithIndex) yield elem match {
            case _ if (elem == e)         => Some((i + 1, 1))
            case ls : LayoutSpecification => addOffset(ls.topLeftOption(e), i)
            case _                        => None
        }
        results.flatten.headOption
    }

    override val height = {
        val subLayouts = elements.filter(_.isInstanceOf[LayoutSpecification])
        if (subLayouts.isEmpty) 1
        else subLayouts.map(size(_)._2).max
    }

    override val width = elements.size

    override def optionalSize(e : AnyRef) : Option[(Int, Int)] = {
        e match {
            case ls : LayoutSpecification    => Some(ls.size)
            case _ if (elements.contains(e)) => Some((1, height))
            case _                           => subLayouts.flatMap(_.optionalSize(e)).headOption
        }
    }
    private def addOffset(position : Option[Pair[Int, Int]], offset : Int) = {
        position match {
            case None         => None
            case Some((x, y)) => Some((x + offset, y))
        }
    }
}

