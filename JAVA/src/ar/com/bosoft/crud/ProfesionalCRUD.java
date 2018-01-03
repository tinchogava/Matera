/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.ProfesionalData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ProfesionalCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public ProfesionalCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(ProfesionalData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_profesional());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getDireccion());
        parametros.add(nuevo.getId_provincia());
        parametros.add(nuevo.getId_departamento());
        parametros.add(nuevo.getId_localidad());
        parametros.add(nuevo.getCodPostal());
        parametros.add(nuevo.getFechaNac() == null ? (long)0 : nuevo.getFechaNac().getTime());
        parametros.add(nuevo.getDni());
        parametros.add(nuevo.getMatricula());
        parametros.add(nuevo.getContacto());
        parametros.add(nuevo.getPerfil());
        parametros.add(nuevo.getEmail());
        parametros.add(nuevo.getTelParticular());
        parametros.add(nuevo.getTelMovil());
        parametros.add(nuevo.getTelOtros());
        parametros.add(nuevo.getTelConsultorio());
        parametros.add(nuevo.getSecretaria());
        parametros.add(nuevo.getDirConsultorio());
        parametros.add(nuevo.getId_especialidad());
        parametros.add(nuevo.getId_subespecialidad());
        parametros.add(nuevo.getId_zona());
        parametros.add(nuevo.getId_vendedor());
        parametros.add(nuevo.getId_entidad());
        parametros.add(nuevo.getObservaciones());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertProfesional", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <ProfesionalData> consulta(int id_zona, boolean soloHabilitados){
        parametros.clear();
        parametros.add(id_zona);
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaProfesional", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ProfesionalData> lista = new ArrayList<>();
        ProfesionalData registro;
        try {
            while (rs.next()){
                registro = new ProfesionalData();
                registro.setId_profesional(rs.getInt("id_profesional"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setFechaNac(rs.getDate("fechaNac"));
                registro.setDni(rs.getString("dni"));
                registro.setMatricula(rs.getString("matricula"));
                registro.setContacto(rs.getString("contacto"));
                registro.setPerfil(rs.getString("perfil"));
                registro.setEmail(rs.getString("email"));
                registro.setTelParticular(rs.getString("telParticular"));
                registro.setTelMovil(rs.getString("telMovil"));
                registro.setTelOtros(rs.getString("telOtros"));
                registro.setTelConsultorio(rs.getString("telConsultorio"));
                registro.setSecretaria(rs.getString("secretaria"));
                registro.setDirConsultorio(rs.getString("dirConsultorio"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setEspecialidad(rs.getString("especialidad"));
                registro.setSubespecialidad(rs.getString("subespecialidad"));
                registro.setZona(rs.getString("zona"));
                registro.setVendedor(rs.getString("vendedor"));
                registro.setEntidad(rs.getString("entidad"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ArrayList <ProfesionalData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("selectProfesional", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ProfesionalData> lista = new ArrayList<>();
        ProfesionalData registro;
        try {
            while (rs.next()){
                registro = new ProfesionalData();
                registro.setId_profesional(rs.getInt("id_profesional"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setFechaNac(rs.getDate("fechaNac"));
                registro.setDni(rs.getString("dni"));
                registro.setMatricula(rs.getString("matricula"));
                registro.setContacto(rs.getString("contacto"));
                registro.setPerfil(rs.getString("perfil"));
                registro.setEmail(rs.getString("email"));
                registro.setTelParticular(rs.getString("telParticular"));
                registro.setTelMovil(rs.getString("telMovil"));
                registro.setTelOtros(rs.getString("telOtros"));
                registro.setTelConsultorio(rs.getString("telConsultorio"));
                registro.setSecretaria(rs.getString("secretaria"));
                registro.setDirConsultorio(rs.getString("dirConsultorio"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setEspecialidad(rs.getString("especialidad"));
                registro.setSubespecialidad(rs.getString("subespecialidad"));
                registro.setZona(rs.getString("zona"));
                registro.setVendedor(rs.getString("vendedor"));
                registro.setEntidad(rs.getString("entidad"));
                registro.setId_zona(rs.getInt("id_zona"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public ProfesionalData trae(int id_profesional){
        parametros.clear();
        parametros.add(id_profesional);
        
        ResultSet rs = conexion.procAlmacenado("traeProfesional", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ProfesionalData registro = null;
        try {
            while (rs.next()){
                registro = new ProfesionalData();
                registro.setId_profesional(rs.getInt("id_profesional"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setFechaNac(rs.getDate("fechaNac"));
                registro.setDni(rs.getString("dni"));
                registro.setMatricula(rs.getString("matricula"));
                registro.setContacto(rs.getString("contacto"));
                registro.setPerfil(rs.getString("perfil"));
                registro.setEmail(rs.getString("email"));
                registro.setTelParticular(rs.getString("telParticular"));
                registro.setTelMovil(rs.getString("telMovil"));
                registro.setTelOtros(rs.getString("telOtros"));
                registro.setTelConsultorio(rs.getString("telConsultorio"));
                registro.setSecretaria(rs.getString("secretaria"));
                registro.setDirConsultorio(rs.getString("dirConsultorio"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setEspecialidad(rs.getString("especialidad"));
                registro.setSubespecialidad(rs.getString("subespecialidad"));
                registro.setZona(rs.getString("zona"));
                registro.setVendedor(rs.getString("vendedor"));
                registro.setEntidad(rs.getString("entidad"));
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return registro;
    }
}
