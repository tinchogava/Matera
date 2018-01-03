/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.LocalidadData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class LocalidadCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public LocalidadCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public ArrayList <LocalidadData> consulta(int id_departamento){
        parametros.clear();
        parametros.add(id_departamento);
        
        ResultSet rs = conexion.procAlmacenado("consultaLocalidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <LocalidadData> lista = new ArrayList<>();
        LocalidadData registro;
        try {
            while (rs.next()){
                registro = new LocalidadData();
                registro.setId_localidad(rs.getInt("id_localidad"));
                registro.setNombre(rs.getString("nombre"));
                
                lista.add(registro);
            }
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <LocalidadData> consulta(){
        parametros.clear();
        
        ResultSet rs = conexion.procAlmacenado("selectLocalidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <LocalidadData> lista = new ArrayList<>();
        LocalidadData registro;
        try {
            while (rs.next()){
                registro = new LocalidadData();
                registro.setId_departamento(rs.getInt("id_departamento"));
                registro.setId_localidad(rs.getInt("id_localidad"));
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
