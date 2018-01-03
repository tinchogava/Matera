/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.ProductoData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ProductoCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public ProductoCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(ProductoData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_producto());
        parametros.add(nuevo.getCodigo());
        parametros.add(nuevo.getGtin());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getId_codConsumo());
        parametros.add(nuevo.getId_moneda());
        parametros.add(nuevo.getCosto());
        parametros.add(nuevo.getStockMinimo());
        parametros.add(nuevo.getId_clasiProducto());
        parametros.add(nuevo.getPm());
        parametros.add(nuevo.getId_proveedor());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertProducto", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <ProductoData> consulta(int id_clasiProducto, boolean soloHabilitados){
        parametros.clear();
        parametros.add(id_clasiProducto);
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaProducto", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ProductoData> lista = new ArrayList<>();
        ProductoData registro;
        try {
            while (rs.next()){
                registro = new ProductoData(rs);
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <ProductoData> consultaPorNombre(int id_clasiProducto, boolean soloHabilitados){
        parametros.clear();
        parametros.add(id_clasiProducto);
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaProductoDesc", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ProductoData> lista = new ArrayList<>();
        ProductoData registro;
        try {
            while (rs.next()){
                registro = new ProductoData(rs);
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ProductoData trae(Object busca){
        parametros.clear();
        parametros.add(busca);
        parametros.add(this.id_empresa);
        
        ResultSet rs = null;
        
        if (busca.getClass() == String.class){
            rs = conexion.procAlmacenado("traeProductoCodigo", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        } else if (busca.getClass() == Integer.class){
            rs = conexion.procAlmacenado("traeProductoId", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        }
        
        ProductoData registro = null;
        try {
            rs.beforeFirst();
            while (rs.next()){
                registro = new ProductoData(rs);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return registro;
    }
}
