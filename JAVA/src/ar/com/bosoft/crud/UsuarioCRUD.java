package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.UsuarioData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class UsuarioCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public UsuarioCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(UsuarioData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_usuario());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getDocumento());
        parametros.add(nuevo.getCuil());
        parametros.add(nuevo.getFechaNac() == null ? (long)0 : nuevo.getFechaNac().getTime());
        parametros.add(nuevo.getDireccion());
        parametros.add(nuevo.getCodPostal());
        parametros.add(nuevo.getId_provincia());
        parametros.add(nuevo.getId_departamento());
        parametros.add(nuevo.getId_localidad());
        parametros.add(nuevo.getFechaIngreso() == null ? (long)0 : nuevo.getFechaIngreso().getTime());
        parametros.add(nuevo.getFechaEgreso() == null ? (long)0 : nuevo.getFechaEgreso().getTime());
        parametros.add(nuevo.getUsaSistema());        
        parametros.add(nuevo.getContraseña());
        parametros.add(nuevo.getEmail());
        parametros.add(nuevo.getId_zona());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertUsuario", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <UsuarioData> consulta(boolean soloSistema,boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloSistema);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaUsuario", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <UsuarioData> lista = new ArrayList<>();
        UsuarioData registro;
        try {
            while (rs.next()){
                registro = new UsuarioData();
                registro.setId_usuario(rs.getInt("id_usuario"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDocumento(rs.getString("documento"));
                registro.setCuil(rs.getString("cuil"));
                registro.setFechaNac(rs.getDate("fechaNac"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setId_provincia(rs.getInt("id_provincia"));
                registro.setId_departamento(rs.getInt("id_departamento"));
                registro.setId_localidad(rs.getInt("id_localidad"));
                registro.setFechaIngreso(rs.getDate("fechaIngreso"));
                registro.setFechaEgreso(rs.getDate("fechaEgreso"));
                registro.setUsaSistema(rs.getString("usaSistema"));
                registro.setContraseña(rs.getString("contraseña"));
                registro.setEmail(rs.getString("email"));
                registro.setId_zona(rs.getInt("id_zona"));
                registro.setId_zonaSistema(rs.getInt("id_zonaSistema"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
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
