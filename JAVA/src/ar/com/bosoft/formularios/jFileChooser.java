/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.dialogos.AvisoEspera;
import com.google.common.io.Files;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Blob;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author tinchogava
 */
public class jFileChooser extends javax.swing.JInternalFrame {
    BufferedImage uploadedImage;
    ImageIcon img = null;
    String name, extension;
    AvisoEspera avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
    private FileInputStream fis;
    private Integer bytes;
    /**
     * Creates new form CargaImagen
     */
    public jFileChooser() {
        
        initComponents();
    }
 
/*
    public String saveFile(JLabel jLabelFile) throws IOException{
        try{
            
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG y PNG", "GIF", "jpg", "png", "gif");
        //pdf, txt
        jFileChooser.setFileFilter(filtro);
        jFileChooser.showSaveDialog(this);
        
        if (jFileChooser.getSelectedFile().getName().lastIndexOf(".") > 0) {
            extension = jFileChooser.getSelectedFile().getName().substring(jFileChooser.getSelectedFile().
                    getName().lastIndexOf(".") + 1).toLowerCase();
        }
        this.avisoEspera.setVisible(true);
        int id = (int)(Math.random()*(1-1000000)+1000000);
        name = String.valueOf(id);
        
        switch(extension){
            case "jpg":
            case "gif":
            case "png":
                
                uploadedImage = ImageIO.read(jFileChooser.getSelectedFile());
                
                name = name + ".png";
                File newImage = new File(Utiles.RECLAMOS_DIR, name);
  
                if(uploadedImage != null){
                    BufferedImage newBufferedImage = new BufferedImage(uploadedImage.getWidth(),
                                uploadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    newBufferedImage.createGraphics().drawImage(uploadedImage, 0, 0, Color.WHITE, null);
                    ImageIO.write(newBufferedImage, "png", newImage);
                    this.avisoEspera.setVisible(false);
                    ImageIcon icon = new ImageIcon(jFileChooser.getSelectedFile().toString());
                    Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabelFile.getWidth(), 
                            jLabelFile.getHeight(), Image.SCALE_DEFAULT));
                    jLabelFile.setText(jFileChooser.getName());   
                    //jLabelFile.setIcon(icono);  
                }
                break;
            default:
                File file =jFileChooser.getSelectedFile();
                if(file !=null){
                    name = name + "." + extension; 
                    FileWriter save;
                    save = new FileWriter(file);
                    save.write(name);
                    save.close();
                    this.avisoEspera.setVisible(false);
                    jLabelFile.setText(jFileChooser.getSelectedFile().getName());
                }
                break;
        }
        JOptionPane.showMessageDialog(null, 
                            "Se ha cargado el archivo correctamente", 
                            "Información",JOptionPane.INFORMATION_MESSAGE);
        
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(null,
                                "El archivo no pudo ser cargado", 
                                "Advertencia",JOptionPane.WARNING_MESSAGE);
            return "noFile";
        }
        
        return name;
    }*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser;
    // End of variables declaration//GEN-END:variables

    
}
