/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.TipoCompData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class TipoCompCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public TipoCompCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public ArrayList <TipoCompData> consulta(){
        parametros.clear();
        ResultSet rs = conexion.procAlmacenado("consultaTipoComp", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <TipoCompData> lista = new ArrayList<>();
        TipoCompData registro;
        try {
            while (rs.next()){
                registro = new TipoCompData();
                registro.setId_tipoComp(rs.getInt("id_tipoComp"));
                registro.setCodigo(rs.getString("codigo"));
                registro.setDenominacion(rs.getString("denominacion"));
                registro.setAbreviatura(rs.getString("abreviatura"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
