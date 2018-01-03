/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import matera.db.dao.FacturaDao;
import matera.db.dao.MayorprofesionalDao;
import matera.db.dao.PresupuestoDao;
import matera.db.dao.MarchaNegociosDao;
import matera.jooq.tables.pojos.MarchaNegocios;
import org.jxls.area.XlsArea;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;

/**
 *
 * @author h2o
 * Serivicio para obtener los datos de la marcha de negocio de una ZONA entre el primer dia del mes y el dia actual.
 */
public class MarchaNegocioService {
    
    public static MarchaNegocios getMarchaNegociosByYearAndMonth(Integer year, Integer month){
        return new MarchaNegociosDao().fetchByYearAndMonth(year, month);
    }
    
    public static BigDecimal getTotalFacturado(Date d1, Date d2, Integer id_zona){
        return new FacturaDao().getTotalFacturadoBetweenDatesForZona(d1, d2, id_zona);
    }
    
    public static Double getRmPendiente(Date d1, Date d2, Integer id_zona){
        return new MayorprofesionalDao().getRmPendienteBetweenDatesForZona(d1, d2, id_zona);
    }
    
    public static Double getPendienteFacturacionFinalizado(Date d1, Date d2, Integer id_zona){
        return new PresupuestoDao().getPendienteFacturacionFinalizadoBetweenDatesForZona(d1, d2, id_zona);
    }
    
    public static Double getMontoPresupuestadoConfirmadoConFechaCirugia(Date d1, Date d2, Integer id_zona){
        return new PresupuestoDao().getMontoPresupuestadoConfirmadoConFechaCirugiaBetweenDatesForZona(d1, d2, id_zona);
    }
    
    public static Double getCostoVenta(Date d1, Date d2, Integer id_zona){
        return new PresupuestoDao().getCostoVentaBetweenDatesForZona(d1, d2, id_zona);
    }
    
    public static Double getMontoPresupuestadoAprobadoSinFechaCirugia(Date d1, Date d2, Integer id_zona){
        return new PresupuestoDao().getMontoPresupuestadoAprobadoSinFechaCirugiaForZona(id_zona);
    }
    
    public static Double getPendientesVip(Integer id_zona){
        return new PresupuestoDao().getMontoPresupuestadoPendientesVipForZona(id_zona);
    }
    
     public static File generateXlsx(){

        File xlsx = null;
        try {
            
            xlsx = File.createTempFile("marcha de negocios", ".xlsx");
            InputStream is =  new FileInputStream("xls/template-marcha-negocios.xls");
            OutputStream os = new FileOutputStream(xlsx.getAbsolutePath());
            Transformer transformer = TransformerFactory.createTransformer(is, os);
            
            XlsArea xlsArea = new XlsArea("Marcha!A1:M23", transformer);
            Context context = new Context();
            context.putVar("mes", "Mayo");
            context.putVar("zona", "Mendoza");
            context.putVar("forecast", 2220000);
            context.putVar("ft_5", "");
            xlsArea.applyAt(new CellRef("Sheet!A1"), context);
            xlsArea.processFormulas();
            transformer.write();
        } catch (IOException ex) {
            Logger.getLogger(MarchaNegocioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        return xlsx;
    }
    
}
