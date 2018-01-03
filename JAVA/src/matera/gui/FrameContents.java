/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui;

/*
 * JScroll - the scrollable desktop pane for Java.
 * Copyright (C) 2003 Tom Tessier
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;


/**
 * creates the internal frame constants used by the example applications
 *
 * @author Tom Tessier
 * @version 1.0.2
 */
public class FrameContents extends JPanel implements ActionListener {
    private JTextPane messageBox;
    private JTextField entryField;
    private Document doc;

    /**
     * Creates a new FrameContents object.
     */
    public FrameContents() {
        super(new BorderLayout());

        JPanel topFramePanel = new JPanel(new BorderLayout());
        JPanel bottomFramePanel = new JPanel();

        messageBox = new JTextPane();

        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style regular = messageBox.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");
        StyleConstants.setBold(def, true);

        Style s = messageBox.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);
        s = messageBox.addStyle("red", regular);
        StyleConstants.setForeground(s, Color.red);

        doc = messageBox.getStyledDocument();

        entryField = new JTextField(20);

        JButton sendButton = new JButton("Send");

        messageBox.setToolTipText("This is the main message window.");
        entryField.setToolTipText("Enter your message here.");
        sendButton.setToolTipText("Click this button to send your message.");

        messageBox.setEditable(false); // can't edit message box

        sendButton.setMnemonic(KeyEvent.VK_S);

        entryField.addActionListener(this);
        sendButton.addActionListener(this);

        // place the messageBox in a scrollPane
        JScrollPane messageBoxScroller = new JScrollPane(messageBox,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        messageBoxScroller.setPreferredSize(new Dimension(300, 200));
        messageBoxScroller.setMinimumSize(new Dimension(100, 50));

        topFramePanel.add(messageBoxScroller, BorderLayout.CENTER);

        bottomFramePanel.add(entryField);
        bottomFramePanel.add(sendButton);

        add(topFramePanel, BorderLayout.CENTER);
        add(bottomFramePanel, BorderLayout.SOUTH);
    }

    /**
     * the action performed upon text entry
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        String text = "";

        try {
            text = entryField.getText();

            doc.insertString(doc.getLength(), "Staff> ",
                messageBox.getStyle("red"));
            doc.insertString(doc.getLength(), text + "\n",
                messageBox.getStyle("italic"));

            entryField.setText(""); // clear the old text
        } catch (NullPointerException np) {
        } catch (BadLocationException bl) {
        }
    }
}
