package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.OpcionData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class OpcionCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public OpcionCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public ArrayList <OpcionData> consulta(int grupo){
        parametros.clear();
        parametros.add(grupo);
        
        ResultSet rs = conexion.procAlmacenado("consultaOpcion", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <OpcionData> lista = new ArrayList<>();
        OpcionData registro;
        try {
            while (rs.next()){
                registro = new OpcionData();
                registro.setId_opcion(rs.getInt("id_opcion"));
                registro.setNombre(rs.getString("nombre"));
                registro.setAbreviado(rs.getString("abreviado"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
