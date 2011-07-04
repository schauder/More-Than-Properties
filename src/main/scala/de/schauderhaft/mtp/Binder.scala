package de.schauderhaft.mtp

import validation._
import javax.swing._
import javax.swing.event.DocumentListener
import javax.swing.event.DocumentEvent
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

object Binder {
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

    def bind(action : => Unit, button : JButton) {
        button.addActionListener(new ActionListener() { def actionPerformed(e : ActionEvent) { action } })
    }

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