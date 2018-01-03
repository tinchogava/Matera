package ar.com.bosoft.clases;

import ar.com.bosoft.formularios.ConsultaMayor;
import ar.com.dialogos.AvisoEspera;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.io.File;

/**
 *
 * @author Herni
 */
public class Reporte {
    public void startReport(String nombreReporte, Map param, Object data, int tipoSalida, 
                            String nombreArchivo, String impresora, int copias) {
        AvisoEspera avisoEspera = new AvisoEspera(null, false, AvisoEspera.CARGANDO);
        avisoEspera.setVisible(false);
        startReport(nombreReporte, param, data, tipoSalida, nombreArchivo, impresora, copias, avisoEspera);
    }
            
    public void startReport(String nombreReporte, Map param, Object data,int tipoSalida, 
                            String nombreArchivo, String impresora,int copias , AvisoEspera avisoEspera){

        try{
            String template = "reportes/" + nombreReporte + ".jasper"; 
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(template));
            
            //agrega Logo
            String logo = "/recursos/imagenes/logoMyATQ.jpg";
            param.put("logo", this.getClass().getResourceAsStream(logo));
            
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte,param,(JRDataSource) data);
//            if (data.getClass() == JRDataSource.class){
//                jasperprint = JasperFillManager.fillReport(reporte,param,(JRDataSource) data);
//            } else if (data.getClass() == JRResultSetDataSource.class) {
//                jasperprint = JasperFillManager.fillReport(reporte,param,(JRResultSetDataSource) data);
//            }
            
            switch (tipoSalida){
                case 0:
                    JasperViewer visor=new JasperViewer(jasperprint,false);
                    visor.setTitle("BOSOFT - Diseñador de Reportes");
                    visor.setVisible(true);
                    break;
                    
                case 1:
                    PrinterJob job = PrinterJob.getPrinterJob();                
                    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null); // Crea un array con las impresoras instaladas
                    int selectedService = 0;
                    /* Busca la impresora seleccionada */
                    for(int i = 0; i < services.length;i++){
                        if(services[i].getName().toUpperCase().contains(impresora)){
                            selectedService = i;
                        }
                    }
                    job.setPrintService(services[selectedService]);
                    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                    MediaSizeName mediaSizeName = MediaSize.findMedia(210,297,MediaPrintableArea.MM); 
                    // Elige el tamaño de hoja.
                    //Busca la mas proxima al tamaño que se pase
                    //MediaSizeName mediaSizeName = MediaSize.findMedia(tamañoX, tamañoY, unidad de medida)
                    printRequestAttributeSet.add(mediaSizeName);
                    printRequestAttributeSet.add(new Copies(1)); //Copias
                    JRPrintServiceExporter impreso;
                    impreso = new JRPrintServiceExporter();
                    impreso.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
                    /* Se setea la impresora elegida con los parametros anteriores */
                    impreso.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
                    impreso.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
                    impreso.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
                    impreso.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);  //Dialogos para armar la impresion
                    impreso.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    impreso.exportReport();
                    avisoEspera.setVisible(false);
                    break;
                    
                case 2:
                    JRExporter pdf = new JRPdfExporter();
                    pdf.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
                    pdf.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(nombreArchivo));
                    pdf.exportReport();
                    avisoEspera.setVisible(false);
                    aviso(nombreArchivo);
                    break;
                    
                case 3:
                    JRExporter xls = new JRXlsxExporter();
                    xls.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
                    xls.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(nombreArchivo));
                    xls.exportReport();
                    avisoEspera.setVisible(false);
                    aviso(nombreArchivo);
                    break;
                    
                case 4:
                    JRExporter txt = new JRRtfExporter();
                    txt.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
                    txt.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(nombreArchivo));
                    txt.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, 12);
                    txt.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, 12);
                    txt.exportReport();
                    avisoEspera.setVisible(false);
                    aviso(nombreArchivo);
                    break;
            }                
        }catch(PrinterException | JRException e){
            JOptionPane.showMessageDialog(null,"Hay un error al crear el reporte\n" + e, "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void aviso(String nombreArchivo){
        JOptionPane.showMessageDialog(null, "El archivo ha sido exportado en " + nombreArchivo, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
