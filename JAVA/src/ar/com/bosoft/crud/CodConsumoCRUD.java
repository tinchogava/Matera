/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.CodConsumoData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class CodConsumoCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public CodConsumoCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(CodConsumoData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_codConsumo());
        parametros.add(nuevo.getCodigo());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getHabilita());
        
        conexion.procAlmacenado("insertCodConsumo", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <CodConsumoData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaCodConsumo", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <CodConsumoData> lista = new ArrayList<>();
        CodConsumoData registro;
        try {
            while (rs.next()){
                registro = new CodConsumoData();
                registro.setId_codConsumo(rs.getInt("id_codConsumo"));
                registro.setCodigo(rs.getString("codigo"));
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
