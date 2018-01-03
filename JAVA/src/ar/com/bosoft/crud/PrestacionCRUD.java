/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.PrestacionData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class PrestacionCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public PrestacionCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(PrestacionData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_prestacion());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getImporte());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertPrestacion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <PrestacionData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaPrestacion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <PrestacionData> lista = new ArrayList<>();
        PrestacionData registro;
        try {
            while (rs.next()){
                registro = new PrestacionData();
                registro.setId_prestacion(rs.getInt("id_prestacion"));
                registro.setNombre(rs.getString("nombre"));
                registro.setImporte(rs.getDouble("importe"));
                registro.setHabilita(rs.getString("habilita"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <PrestacionData> consultaSinOrden(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaPrestacionSinOrden", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <PrestacionData> lista = new ArrayList<>();
        PrestacionData registro;
        try {
            while (rs.next()){
                registro = new PrestacionData();
                registro.setId_prestacion(rs.getInt("id_prestacion"));
                registro.setNombre(rs.getString("nombre"));
                registro.setImporte(rs.getDouble("importe"));
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
