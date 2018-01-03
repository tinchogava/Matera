package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.EmpresaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class EmpresaCRUD {
    Conexion conexion;
    String empresa;
    ArrayList parametros;
    
    public EmpresaCRUD(Conexion conexion, String empresa) {
        this.conexion = conexion;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public ArrayList <EmpresaData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaEmpresa", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <EmpresaData> lista = new ArrayList<>();
        EmpresaData registro;
        try {
            while (rs.next()){
                registro = new EmpresaData();
                registro.setId_empresa(rs.getInt("id_empresa"));
                registro.setRazonSocial(rs.getString("razonSocial"));
                registro.setFantasia(rs.getString("fantasia"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setPais(rs.getString("pais"));
                registro.setPosicionIva(rs.getInt("posicionIva"));
                registro.setCuit(rs.getString("cuit"));
                registro.setIngresosBrutos(rs.getString("ingresosBrutos"));
                registro.setEstablecimiento(rs.getString("establecimiento"));
                registro.setSedeTimbrado(rs.getString("sedeTimbrado"));
                registro.setInicioActividades(rs.getDate("inicioActividades"));
                registro.setClaveAccesos(rs.getString("claveAccesos"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setDescIva(rs.getString("descIva"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
