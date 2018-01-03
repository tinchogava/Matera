/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.NotaPresuData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class NotaPresuCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa, usuario;
    ArrayList parametros;
    
    public NotaPresuCRUD(Conexion conexion, int id_empresa, String empresa, String usuario) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.usuario = usuario;
        this.parametros = new ArrayList();
    }
    
    public void insert(NotaPresuData nuevo){
        parametros.clear();
        parametros.add(nuevo.getNota());
        parametros.add(this.id_empresa);
        parametros.add(this.usuario);
        
        conexion.procAlmacenado("insertNotaPresu", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public NotaPresuData consulta(){
        parametros.clear();
        parametros.add(this.id_empresa);
        
        ResultSet rs = conexion.procAlmacenado("consultaNotaPresu", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <NotaPresuData> lista = new ArrayList<>();
        NotaPresuData registro = null;
        try {
            while (rs.next()){
                registro = new NotaPresuData();
                registro.setNota(rs.getString("nota"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return registro;
    }
}
