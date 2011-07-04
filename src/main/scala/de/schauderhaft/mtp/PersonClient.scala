package de.schauderhaft.mtp

import validation._
import com.jgoodies.forms.layout._
import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

object PersonClient {

    def main(args : Array[String]) {
        val p = new PersonEditor()
        show(createPersonPanel(p))
    }

    case class PanelBuilder() {
        val LABEL_X = 0
        val VALIDATION_X = 1
        val COMPONENT_X = 2
        val BUTTON_X = 2

        val panel = new JPanel()
        val layout = new FormLayout(
            "min, 2dlu, 4dlu, 0dlu, pref:grow",
            "pref:grow, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref:grow")
        panel.setLayout(layout)

        var currentRow = 0

        private def gridx(logic : Int) = 2 * logic + 1
        private def gridy(logic : Int) = 2 * logic + 2
        def add(components : (JLabel, JLabel, JComponent)) {
            addToLabelColumn(components._1, currentRow)
            addToValidationColumn(components._2)
            addToComponentColumn(components._3, currentRow)
            currentRow += 1
        }

        def add(
            component : JButton) {
            val c = new CellConstraints()
            panel.add(component, c.xy(gridx(BUTTON_X), gridy(currentRow), "right, top"))
        }

        private def addToLabelColumn(
            component : JComponent,
            row : Int) {
            val c = new CellConstraints()
            panel.add(component, c.xy(gridx(LABEL_X), gridy(row), "right, default"))
        }

        private def addToValidationColumn(component : JComponent) {
            val c = new CellConstraints()
            panel.add(component, c.xy(gridx(VALIDATION_X), gridy(currentRow)))
        }

        private def addToComponentColumn(
            component : JComponent,
            row : Int) {
            val c = new CellConstraints()
            panel.add(component, c.xy(gridx(COMPONENT_X), gridy(row)))
        }

    }

    private def create[T : Manifest](name : String, property : Property[T]) : (JLabel, JLabel, JComponent) = {
        val textField = new JTextField()
        Binder.bind(property, textField)

        val validationLabel = property match {
            case v : Validation[_] => createValidationLabel(v);
            case _                 => new JLabel()
        }
        (new JLabel(name), validationLabel, textField)

    }

    private def createValidationLabel(v : Validation[_]) : JLabel = {
        val l = new JLabel("x")
        l.setForeground(Color.RED)
        Binder.bindValidation(v, l);
        l
    }

    private def create(name : String, action : => Unit) = {
        val button = new JButton("save")
        Binder.bind(action, button)
        button
    }

    private def createPersonPanel(p : PersonEditor) = {
        val builder = PanelBuilder()

        builder.add(create("firstname", p.firstname))
        builder.add(create("lastname", p.lastname))
        builder.add(create("age", p.age))
        builder.add(create("save", p.save))

        builder.panel
    }

    private def show(panel : JPanel) {
        val frame = new JFrame("Person")
        frame.getContentPane().add(panel)
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.pack
        frame.setVisible(true)
    }
}