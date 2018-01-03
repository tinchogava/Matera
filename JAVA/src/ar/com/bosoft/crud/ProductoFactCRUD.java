/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.ProductoFactData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ProductoFactCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public ProductoFactCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(ProductoFactData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_productoFact());
        parametros.add(nuevo.getCodigo());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getId_especialidad());
        parametros.add(nuevo.getId_subespecialidad());
        parametros.add(nuevo.getId_forecastGrupo());
        parametros.add(nuevo.getHabilita());
        parametros.add(nuevo.getDescripcion());
        parametros.add(nuevo.getPrecio());
        
        conexion.procAlmacenado("insertProductoFact", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <ProductoFactData> consulta(boolean soloHabilitados, int ordena){
        parametros.clear();
        parametros.add(soloHabilitados);
        parametros.add(ordena);
        
        ResultSet rs = conexion.procAlmacenado("consultaProductoFact", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ProductoFactData> lista = new ArrayList<>();
        ProductoFactData registro;
        try {
            while (rs.next()){
                registro = new ProductoFactData();
                registro.setId_productoFact(rs.getInt("id_productoFact"));
                registro.setCodigo(rs.getString("codigo"));
                registro.setNombre(rs.getString("nombre"));
                registro.setId_especialidad(rs.getInt("id_especialidad"));
                registro.setId_subespecialidad(rs.getInt("id_subespecialidad"));
                registro.setId_forecastGrupo(rs.getInt("id_forecastGrupo"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setEspecialidad(rs.getString("especialidad"));
                registro.setSubespecialidad(rs.getString("subespecialidad"));
                registro.setForecastGrupo(rs.getString("forecastGrupo"));
                registro.setDescripcion(rs.getString("descripcion"));
                registro.setPrecio(rs.getBigDecimal("precio").doubleValue());
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
