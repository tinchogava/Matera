/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.MotivoData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class MotivoCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public MotivoCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public ArrayList <MotivoData> consulta(){
        parametros.clear();
        
        ResultSet rs = conexion.procAlmacenado("consultaMotivo", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <MotivoData> lista = new ArrayList<>();
        MotivoData registro;
        try {
            while (rs.next()){
                registro = new MotivoData();
                registro.setId_motivo(rs.getInt("id_motivo"));
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
