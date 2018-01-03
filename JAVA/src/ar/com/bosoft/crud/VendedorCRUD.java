/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.VendedorData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class VendedorCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public VendedorCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(VendedorData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_vendedor());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getId_zona());
        parametros.add(nuevo.getId_especialidad());
        parametros.add(nuevo.getComision());
        parametros.add(nuevo.getBeneficio());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertVendedor", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <VendedorData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("selectVendedor", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <VendedorData> lista = new ArrayList<>();
        VendedorData registro;
        try {
            while (rs.next()){
                registro = new VendedorData();
                registro.setId_vendedor(rs.getInt("id_vendedor"));
                registro.setNombre(rs.getString("nombre"));
                registro.setId_zona(rs.getInt("id_zona"));
                registro.setId_especialidad(rs.getInt("id_especialidad"));
                registro.setComision(rs.getDouble("comision"));
                registro.setBeneficio(rs.getString("beneficio"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setZona(rs.getString("zona"));
                registro.setEspecialidad(rs.getString("especialidad"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <VendedorData> consulta(int id_zona, boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(id_zona);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaVendedor", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <VendedorData> lista = new ArrayList<>();
        VendedorData registro;
        try {
            while (rs.next()){
                registro = new VendedorData();
                registro.setId_vendedor(rs.getInt("id_vendedor"));
                registro.setNombre(rs.getString("nombre"));
                registro.setId_zona(rs.getInt("id_zona"));
                registro.setId_especialidad(rs.getInt("id_especialidad"));
                registro.setComision(rs.getDouble("comision"));
                registro.setBeneficio(rs.getString("beneficio"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setZona(rs.getString("zona"));
                registro.setEspecialidad(rs.getString("especialidad"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <VendedorData> consultaCobrador(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaCobrador", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <VendedorData> lista = new ArrayList<>();
        VendedorData registro;
        try {
            while (rs.next()){
                registro = new VendedorData();
                registro.setId_vendedor(rs.getInt("id_vendedor"));
                registro.setNombre(rs.getString("nombre"));
                registro.setId_zona(rs.getInt("id_zona"));
                registro.setId_especialidad(rs.getInt("id_especialidad"));
                registro.setComision(rs.getDouble("comision"));
                registro.setBeneficio(rs.getString("beneficio"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setZona(rs.getString("zona"));
                registro.setEspecialidad(rs.getString("especialidad"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
