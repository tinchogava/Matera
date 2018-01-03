/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.helpers;

import ar.com.bosoft.clases.Utiles;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author h2o
 */
public class ExportHelper {
    public static void tableToExcel(DefaultTableModel model, JTable table, Boolean onlySelection, Integer fromCol, Integer toCol){
        try{
            if (model.getRowCount() > 0) {
                //Crear un objeto FileChooser
                JFileChooser fc = new JFileChooser();
                //Mostrar la ventana para abrir archivo y recoger la respuesta
                int respuesta = fc.showSaveDialog(null);
                //Comprobar si se ha pulsado Aceptar
                if (respuesta == JFileChooser.APPROVE_OPTION){
                    try {
                        //Crear un objeto File con el archivo elegido
                        File file = new File(fc.getSelectedFile().getCanonicalPath() + ".xls");
                        WritableWorkbook workbook1 = Workbook.createWorkbook(file);
                        WritableSheet sheet1 = workbook1.createSheet("Hoja", 0);
                        for (int i = fromCol; i < toCol; i++) {
                            Label column = new Label(i, 0, model.getColumnName(i));
                            sheet1.addCell(column);
                        }
                        int excelRow = 0;
                        int[] selectedRows = table.getSelectedRows();
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (onlySelection){
                                if (selectedRows.length > 0){
                                    if (!ArrayUtils.contains(selectedRows, i)){
                                        continue;
                                    }
                                }
                            }
                            for (int j = fromCol; j < toCol; j++) {

                                int rowIndex = table.convertRowIndexToModel(i);
                                int colIndex = j;
                                String val = (model.getValueAt(rowIndex, colIndex) == null ? "" : model.getValueAt(rowIndex, colIndex).toString());
                                if (NumberUtils.isNumber(val)){
                                    sheet1.addCell(new jxl.write.Number(colIndex, excelRow + 1, Double.parseDouble(val)));
                                }
                                else {
                                    sheet1.addCell(new Label(colIndex, excelRow + 1, val));                            
                                }
                            }
                            excelRow++;
                        }
                        workbook1.write();
                        workbook1.close();
                        JOptionPane.showMessageDialog(null,"Exportación exitosa!", "Información",JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException | WriteException ex) {
                        JOptionPane.showMessageDialog(null,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"No hay resultados para mostrar", "Información",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
    }
}
