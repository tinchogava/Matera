/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.dialog;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author h2o
 */
public class LoadingDialog {
    JDialog dialog;
    final JProgressBar progressBar;
    JLabel label;
    
    public LoadingDialog(){
        progressBar = new JProgressBar();   
        dialog = new JDialog( (JFrame) null, "Buscando datos...", true);    
        label = new JLabel("Procesando...");
        dialog.add(BorderLayout.NORTH,label);
        dialog.add(BorderLayout.CENTER, progressBar);
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(null);
        dialog.pack();    
    }
    
    public JDialog getDialog(){
        return dialog;
    }
    
    public JProgressBar getProgressBar(){
        return progressBar;
    }
    
    public JLabel getLabel(){
        return label;
    }
    
    public void finish(){
        getProgressBar().setIndeterminate(false);
        getLabel().setText("Successful");
        getProgressBar().setValue(100); // 100%
        getDialog().dispose();  
        getDialog().setVisible(false);
    }
}
