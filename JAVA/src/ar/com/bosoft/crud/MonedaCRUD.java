/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.MonedaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class MonedaCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public MonedaCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(MonedaData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_moneda());
        parametros.add(nuevo.getCodigo());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getSimbolo());
        parametros.add(nuevo.getHabilita());
        parametros.add(nuevo.getCotizacion());
        
        conexion.procAlmacenado("insertMoneda", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <MonedaData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaMoneda", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <MonedaData> lista = new ArrayList<>();
        MonedaData registro;
        try {
            while (rs.next()){
                registro = new MonedaData();
                registro.setId_moneda(rs.getInt("id_moneda"));
                registro.setCodigo(rs.getString("codigo"));
                registro.setNombre(rs.getString("nombre"));
                registro.setSimbolo(rs.getString("simbolo"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setCotizacion(rs.getDouble("cotizacion"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
