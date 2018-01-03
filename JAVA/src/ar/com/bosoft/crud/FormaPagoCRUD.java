/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.FormaPagoData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class FormaPagoCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public FormaPagoCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(FormaPagoData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_formaPago());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getHabilita());
        
        conexion.procAlmacenado("insertFormaPago", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <FormaPagoData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaFormaPago", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <FormaPagoData> lista = new ArrayList<>();
        FormaPagoData registro;
        try {
            while (rs.next()){
                registro = new FormaPagoData();
                registro.setId_formaPago(rs.getInt("id_formapago"));
                registro.setNombre(rs.getString("nombre"));
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
