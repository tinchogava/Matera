/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.ClasiEntidadData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ClasiEntidadCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public ClasiEntidadCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(ClasiEntidadData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_clasiEntidad());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getHabilita());
        
        conexion.procAlmacenado("insertClasiEntidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <ClasiEntidadData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaClasiEntidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ClasiEntidadData> lista = new ArrayList<>();
        ClasiEntidadData registro;
        try {
            while (rs.next()){
                registro = new ClasiEntidadData();
                registro.setId_clasiEntidad(rs.getInt("id_clasiEntidad"));
                registro.setNombre(rs.getString("nombre"));
                registro.setHabilita(rs.getString("habilita"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
