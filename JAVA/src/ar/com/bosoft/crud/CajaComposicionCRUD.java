/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.CajaComposicionData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class CajaComposicionCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    ArrayList parametros;
    
    public CajaComposicionCRUD(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.parametros = new ArrayList();
    }
    
    public void insert(CajaComposicionData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_cajaDeposito());
        parametros.add(nuevo.getId_producto());
        parametros.add(nuevo.getCantidad());
        parametros.add(this.usuario);
        
        conexion.procAlmacenado("insertCajaComposicion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <CajaComposicionData> consulta(int id_cajaDeposito){
        parametros.clear();
        parametros.add(id_cajaDeposito);
        
        ResultSet rs = conexion.procAlmacenado("consultaCajaComposicion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <CajaComposicionData> lista = new ArrayList<>();
        CajaComposicionData registro;
        try {
            while (rs.next()){
                registro = new CajaComposicionData();
                registro.setCodigo(rs.getString("codigo"));
                registro.setProducto(rs.getString("nombre"));
                registro.setCantidad(rs.getInt("cantidad"));
                registro.setId_producto(rs.getInt("id_producto"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public void delete (int id_caja){
        parametros.clear();
        parametros.add(id_caja);
        
        conexion.procAlmacenado("deleteCajaComposicion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
}
