/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.DepartamentoData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class DepartamentoCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public DepartamentoCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public ArrayList <DepartamentoData> consulta(int id_provincia){
        parametros.clear();
        parametros.add(id_provincia);
        
        ResultSet rs = conexion.procAlmacenado("consultaDepartamento", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <DepartamentoData> lista = new ArrayList<>();
        DepartamentoData registro;
        try {
            while (rs.next()){
                registro = new DepartamentoData();
                registro.setId_departamento(rs.getInt("id_departamento"));
                registro.setNombre(rs.getString("nombre"));
                
                lista.add(registro);
            }
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <DepartamentoData> consulta(){
        parametros.clear();
        
        ResultSet rs = conexion.procAlmacenado("selectDepartamento", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <DepartamentoData> lista = new ArrayList<>();
        DepartamentoData registro;
        try {
            while (rs.next()){
                registro = new DepartamentoData();
                registro.setId_provincia(rs.getInt("id_provincia"));
                registro.setId_departamento(rs.getInt("id_departamento"));
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
