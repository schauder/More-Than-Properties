package de.schauderhaft.mtp.binding

import de.schauderhaft.mtp.validation._
import de.schauderhaft.mtp.property._
import javax.swing.{ Action => SwingAction, _ }
import javax.swing.event.DocumentListener
import javax.swing.event.DocumentEvent
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import scala.collection.immutable._

object Binder {
    
    def bind[T : Manifest](p: Property[Seq[T]], table : JTable) {
        table.setModel(new PropertyTableModel(p))
    }
    
    def bind[T : Manifest](p : Property[T], textField : JTextField) {
        if (Manifest.classType(classOf[String]) == manifest)
            bindString(p.asInstanceOf[Property[String]], textField)
        else
            bindString(wrapProperty(p.asInstanceOf[Property[Int]]), textField)
    }

    private def bindString(p : Property[String], textField : JTextField) {
        var locked = false

        initTextField
        syncFromTextFieldToProperty
        syncFromPropertyToTextField

        def initTextField = p() match {
            case null => textField.setText("")
            case _    => textField.setText(p().toString())
        }

        def syncFromPropertyToTextField {
            p.registerListener { value : String =>
                if (textField.getText != value)
                    textField.setText(value)
            }
        }

        def syncFromTextFieldToProperty = textField.getDocument.addDocumentListener(new DocumentListener() {
            def changedUpdate(e : DocumentEvent) = updateProperty
            def insertUpdate(e : DocumentEvent) = updateProperty
            def removeUpdate(e : DocumentEvent) = updateProperty
        })

        def updateProperty = {
            p := textField.getText
        }
    }

    /**
     * binds a block to a JButton.
     *
     * When the JButton gets clicked the block gets executed.
     */
    def bind(action : => Unit, button : JButton) {
        button.addActionListener(new ActionListener() { def actionPerformed(e : ActionEvent) { action } })
    }

    /**
     * binds an Action to a JButton.
     *
     * When the JButton gets clicked the Action gets performed
     * The enabled Property gets bound to the enabled Property of the JButton.
     */
    def bind(action : Action, button : JButton) {
        button.addActionListener(new ActionListener() { def actionPerformed(e : ActionEvent) { action() } })
        action match {
            case a : Enabled => a.enabled.registerListener(bool => button.setEnabled(bool)); button.setEnabled(a.enabled())
            case _           =>
        }
    }

    /**
     * binds a Validation to a JComponent, making the JComponent visible when validation fails, and generates a tooltip from the Validation messages
     */
    def bindValidation(validation : Validation[_], component : JComponent) {

        component.setVisible(!validation.valid)
        validation.valid.registerListener(valid => component.setVisible(!valid))

        def setToolTip(messages : List[String]) {
            component.setToolTipText(
                {
                    messages match {
                        case x :: xs =>
                            "<html>" + (x :: xs).mkString("<br/>") + "</html>"
                        case _ => null
                    }
                })
        }

        setToolTip(validation.validationMessages())
        validation.validationMessages.registerListener(setToolTip)
    }

    def wrapProperty(p : Property[Int]) : Property[String] = {
        val wrapper = new Property(p.value.toString)
        p.registerListener(value => {
            wrapper := value.toString
        })
        wrapper.registerListener((value : String) => {
            try {
                p := value.toInt
            } catch {
                case e =>
            }
        })
        wrapper
    }
}