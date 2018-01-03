/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.DiariaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class DiariaCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public DiariaCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(DiariaData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_diaria());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getDireccion());
        parametros.add(nuevo.getId_provincia());
        parametros.add(nuevo.getId_departamento());
        parametros.add(nuevo.getId_localidad());
        parametros.add(nuevo.getCodPostal());
        parametros.add(nuevo.getId_opcion());
        parametros.add(nuevo.getCuit());
        parametros.add(nuevo.getTelefono1());
        parametros.add(nuevo.getTelefono2());
        parametros.add(nuevo.getTelefono3());
        parametros.add(nuevo.getTelefono4());
        parametros.add(nuevo.getEmail());
        parametros.add(nuevo.getSecretaria());
        parametros.add(nuevo.getContacto());
        parametros.add(nuevo.getTelContacto());
        parametros.add(nuevo.getMovContacto());
        parametros.add(nuevo.getEmailContacto());
        parametros.add(nuevo.getId_zona());
        parametros.add(nuevo.getId_formaPago());
        parametros.add(nuevo.getObservaciones());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertDiaria", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <DiariaData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaDiaria", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <DiariaData> lista = new ArrayList<>();
        DiariaData registro;
        try {
            while (rs.next()){
                registro = new DiariaData();
                registro.setId_diaria(rs.getInt("id_diaria"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setCuit(rs.getString("cuit"));
                registro.setTelefono1(rs.getString("telefono1"));
                registro.setTelefono2(rs.getString("telefono2"));
                registro.setTelefono3(rs.getString("telefono3"));
                registro.setTelefono4(rs.getString("telefono4"));
                registro.setEmail(rs.getString("email"));
                registro.setSecretaria(rs.getString("secretaria"));
                registro.setContacto(rs.getString("contacto"));
                registro.setTelContacto(rs.getString("telContacto"));
                registro.setMovContacto(rs.getString("movContacto"));
                registro.setEmailContacto(rs.getString("emailContacto"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setPosicionIva(rs.getString("posicionIva"));
                registro.setZona(rs.getString("zona"));
                registro.setFormaPago(rs.getString("formaPago"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
