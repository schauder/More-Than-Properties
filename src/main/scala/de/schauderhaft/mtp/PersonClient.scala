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
        val panel = new JPanel()
        val layout = new GridBagLayout()
        panel.setLayout(layout)

        def add(components : (JLabel, JComponent), row : Int) {
            addToLabelColumn(components._1, row)
            addToComponentColumn(components._2, row)
        }

        def add(
            component : JButton,
            row : Int) {
            val c = new GridBagConstraints()

            c.weightx = 1
            c.gridx = 1
            c.gridy = row
            c.fill = 0
            c.anchor = GridBagConstraints.SOUTHEAST
            panel.add(component, c)
        }

        private def addToLabelColumn(
            component : JComponent,
            row : Int) {
            val c = new GridBagConstraints()
            c.gridx = 0
            c.gridy = row
            c.weightx = 0
            c.fill = 0
            panel.add(component, c)
        }

        private def addToComponentColumn(
            component : JComponent,
            row : Int) {
            val c = new GridBagConstraints()
            c.gridx = 1
            c.gridy = row
            c.weightx = 1
            c.fill = 1
            panel.add(component, c)
        }

    }

    private def create[T : Manifest](name : String, property : Property[T]) : (JLabel, JComponent) = {
        val textField = new JTextField()
        Binder.bind(property, textField)
        (new JLabel(name), textField)
    }

    private def create(name : String, action : => Unit) = {
        val button = new JButton("save")
        Binder.bind(action, button)
        button
    }

    private def createPersonPanel(p : PersonEditor) = {
        val builder = PanelBuilder()

        builder.add(create("firstname", p.firstname), 0)
        builder.add(create("lastname", p.lastname), 1)
        builder.add(create("age", p.age), 2)
        builder.add(create("save", p.save), 3)

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