/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.TipoOperacionData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class TipoOperacionCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public TipoOperacionCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public ArrayList <TipoOperacionData> consulta(){
        parametros.clear();
        
        ResultSet rs = conexion.procAlmacenado("consultaTipoOperacion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <TipoOperacionData> lista = new ArrayList<>();
        TipoOperacionData registro;
        try {
            while (rs.next()){
                registro = new TipoOperacionData();
                registro.setId_tipoOperacion(rs.getInt("id_tipoOperacion"));
                registro.setNombre(rs.getString("nombre"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
