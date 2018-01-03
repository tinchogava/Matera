/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.CajaDepositoData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class CajaDepositoCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public CajaDepositoCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(CajaDepositoData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_cajaDeposito());
        parametros.add(nuevo.getId_zona());
        parametros.add(nuevo.getId_caja());
        parametros.add(nuevo.getCodigo());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertCajaDeposito", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <CajaDepositoData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaCajaDeposito", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <CajaDepositoData> lista = new ArrayList<>();
        CajaDepositoData registro;
        try {
            while (rs.next()){
                registro = new CajaDepositoData();
                registro.setId_cajaDeposito(rs.getInt("id_cajaDeposito"));
                registro.setId_zona(rs.getInt("id_zona"));
                registro.setId_caja(rs.getInt("id_caja"));
                registro.setCodigo(rs.getString("codigo"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setZona(rs.getString("zona"));
                registro.setCaja(rs.getString("caja"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
