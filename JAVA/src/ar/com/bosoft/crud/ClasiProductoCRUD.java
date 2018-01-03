/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.ClasiProductoData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ClasiProductoCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public ClasiProductoCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(ClasiProductoData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_clasiProducto());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getHabilita());
        
        conexion.procAlmacenado("insertClasiProducto", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <ClasiProductoData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaClasiProducto", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ClasiProductoData> lista = new ArrayList<>();
        ClasiProductoData registro;
        try {
            while (rs.next()){
                registro = new ClasiProductoData();
                registro.setId_clasiProducto(rs.getInt("id_clasiProducto"));
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
