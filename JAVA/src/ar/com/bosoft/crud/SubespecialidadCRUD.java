/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.SubespecialidadData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class SubespecialidadCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public SubespecialidadCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(SubespecialidadData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_subespecialidad());
        parametros.add(nuevo.getId_especialidad());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getHabilita());
        
        conexion.procAlmacenado("insertSubespecialidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <SubespecialidadData> consulta(int id_especialidad, boolean soloHabilitados){
        parametros.clear();
        parametros.add(id_especialidad);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaSubespecialidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <SubespecialidadData> lista = new ArrayList<>();
        SubespecialidadData registro;
        try {
            while (rs.next()){
                registro = new SubespecialidadData();
                registro.setId_subespecialidad(rs.getInt("id_subespecialidad"));
                registro.setId_especialidad(rs.getInt("id_especialidad"));
                registro.setNombre(rs.getString("nombre"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setEspecialidad(rs.getString("especialidad"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <SubespecialidadData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("selectSubespecialidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <SubespecialidadData> lista = new ArrayList<>();
        SubespecialidadData registro;
        try {
            while (rs.next()){
                registro = new SubespecialidadData();
                registro.setId_subespecialidad(rs.getInt("id_subespecialidad"));
                registro.setId_especialidad(rs.getInt("id_especialidad"));
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
