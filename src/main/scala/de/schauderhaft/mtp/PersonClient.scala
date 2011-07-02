package de.schauderhaft.mtp
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
        val LABEL_GRID_X = 0
        val VALIDATION_GRID_X = 1
        val COMPONENT_GRID_X = 2
        val BUTTON_GRID_X = 2

        val panel = new JPanel()
        val layout = new GridBagLayout()
        panel.setLayout(layout)

        var currentRow = 0

        def add(components : (JLabel, JLabel, JComponent)) {
            addToLabelColumn(components._1, currentRow)
            addToValidationColumn(components._2)
            addToComponentColumn(components._3, currentRow)
            currentRow += 1
        }

        def add(
            component : JButton) {
            val c = new GridBagConstraints()

            c.gridx = BUTTON_GRID_X
            c.gridy = currentRow
            c.weightx = 1
            c.fill = 0
            c.anchor = GridBagConstraints.SOUTHEAST
            panel.add(component, c)
        }

        private def addToLabelColumn(
            component : JComponent,
            row : Int) {
            val c = new GridBagConstraints()
            c.gridx = LABEL_GRID_X
            c.gridy = row
            c.weightx = 0
            c.fill = 0
            c.anchor = GridBagConstraints.EAST
            panel.add(component, c)
        }

        private def addToValidationColumn(component : JComponent) {
            val c = new GridBagConstraints()
            c.gridx = VALIDATION_GRID_X
            c.gridy = currentRow
            c.weightx = 0
            c.fill = 0
            panel.add(component, c)
        }

        private def addToComponentColumn(
            component : JComponent,
            row : Int) {
            val c = new GridBagConstraints()
            c.gridx = COMPONENT_GRID_X
            c.gridy = row
            c.weightx = 1
            c.fill = 1
            panel.add(component, c)
        }

    }

    private def create[T : Manifest](name : String, property : Property[T]) : (JLabel, JLabel, JComponent) = {
        val textField = new JTextField()
        Binder.bind(property, textField)

        val validationLabel = new JLabel("x");
        //        Binder.bindValidation(property, validationLabel)
        (new JLabel(name), validationLabel, textField)
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