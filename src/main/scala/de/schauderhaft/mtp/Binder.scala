package de.schauderhaft.mtp

import javax.swing._
import javax.swing.event.DocumentListener
import javax.swing.event.DocumentEvent
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

object Binder {
    def bind(p : Property[String], textField : JTextField) {
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
}