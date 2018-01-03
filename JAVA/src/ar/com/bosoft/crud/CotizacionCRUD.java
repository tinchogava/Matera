/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.CotizacionData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CotizacionCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    ArrayList parametros;

    public CotizacionCRUD(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        parametros = new ArrayList();
    }
    
    public void insert(CotizacionData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_moneda());
        parametros.add(nuevo.getMes());
        parametros.add(nuevo.getAño());
        parametros.add(nuevo.getValor());
        parametros.add(id_empresa);
        parametros.add(usuario);
        
        conexion.procAlmacenado("insertCotizacion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <CotizacionData> consulta(){
        parametros.clear();
        parametros.add(id_empresa);
        
        ResultSet rs = conexion.procAlmacenado("consultaCotizacion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <CotizacionData> lista = new ArrayList<>();
        CotizacionData registro;
        try {
            while (rs.next()){
                int id_moneda = rs.getInt("id_moneda");
                int mes = rs.getInt("mes");
                int año = rs.getInt("año");
                Double valor = rs.getDouble("valor");
                String moneda = rs.getString("moneda");
                
                registro = new CotizacionData(id_moneda, mes, año, valor, moneda);
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
