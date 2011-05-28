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

    private def createPersonPanel(p : PersonEditor) = {
        val panel = new JPanel()
        val layout = new GridBagLayout()
        val c = new GridBagConstraints()
        c.gridx = 0
        c.gridy = 0

        panel.setLayout(layout)

        c.fill = 0
        panel.add(new JLabel("firstname"), c)

        c.fill = 1
        c.gridx += 1
        c.weightx = 1
        val firstnameTF = new JTextField()
        panel.add(firstnameTF, c)
        Binder.bind(p.firstname, firstnameTF)

        c.fill = 0
        c.weightx = 0
        c.gridx = 0
        c.gridy += 1

        panel.add(new JLabel("lastname"), c)
        c.fill = 1

        c.gridx += 1
        val lastnameTF = new JTextField()
        Binder.bind(p.lastname, lastnameTF)
        panel.add(lastnameTF, c)
        c.fill = 0
        c.weightx = 1
        c.gridy += 1
        c.anchor = GridBagConstraints.SOUTHEAST
        val button = new JButton("save")
        Binder.bind(p.save, button)
        panel.add(button, c)
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