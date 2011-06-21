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

    private def addToLabelColumn(
        panel : JPanel,
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
        panel : JPanel,
        component : JComponent,
        row : Int) {
        val c = new GridBagConstraints()
        c.gridx = 1
        c.gridy = row
        c.weightx = 1
        c.fill = 1
        panel.add(component, c)
    }

    private def addButton(
        panel : JPanel,
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

    private def createPersonPanel(p : PersonEditor) = {
        val panel = new JPanel()
        val layout = new GridBagLayout()
        panel.setLayout(layout)

        addToLabelColumn(panel, new JLabel("firstname"), 0)

        val firstnameTF = new JTextField()
        Binder.bind(p.firstname, firstnameTF)
        addToComponentColumn(panel, firstnameTF, 0)

        addToLabelColumn(panel, new JLabel("lastname"), 1)

        val lastnameTF = new JTextField()
        Binder.bind(p.lastname, lastnameTF)
        addToComponentColumn(panel, lastnameTF, 1)

        val button = new JButton("save")
        Binder.bind(p.save, button)
        addButton(panel, button, 2)
        panel
    }

    private def show(panel : JPanel) {
        val frame = new JFrame("Person")
        frame.getContentPane().add(panel)
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.pack
        frame.setVisible(true)
    }
}