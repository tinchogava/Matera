/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.EntidadData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class EntidadCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public EntidadCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(EntidadData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_entidad());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getDireccion());
        parametros.add(nuevo.getId_provincia());
        parametros.add(nuevo.getId_departamento());
        parametros.add(nuevo.getId_localidad());
        parametros.add(nuevo.getCodPostal());
        parametros.add(nuevo.getEmail());
        parametros.add(nuevo.getTelefono1());
        parametros.add(nuevo.getTelefono2());
        parametros.add(nuevo.getAuditor());
        parametros.add(nuevo.getSecretaria());
        parametros.add(nuevo.getId_zona());
        parametros.add(nuevo.getCertImplante());
        parametros.add(nuevo.getRecomendaciones());
        parametros.add(nuevo.getId_formaPago());
        parametros.add(nuevo.getId_opcion());
        parametros.add(nuevo.getCuit());
        parametros.add(nuevo.getId_clasiEntidad());
        parametros.add(nuevo.getRiesgoCredito());
        parametros.add(nuevo.getReqFacturacion());        
        parametros.add(nuevo.getObservaciones());
        parametros.add(nuevo.getComprasNombre());
        parametros.add(nuevo.getComprasTelefono());
        parametros.add(nuevo.getComprasEmail());
        parametros.add(nuevo.getTesoreriaNombre());
        parametros.add(nuevo.getTesoreriaTelefono());
        parametros.add(nuevo.getTesoreriaEmail());
        parametros.add(nuevo.getContableNombre());
        parametros.add(nuevo.getContableTelefono());
        parametros.add(nuevo.getContableEmail());
        parametros.add(nuevo.getFarmaciaNombre());
        parametros.add(nuevo.getFarmaciaTelefono());
        parametros.add(nuevo.getFarmaciaEmail());        
        parametros.add(nuevo.getGln());        
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertEntidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <EntidadData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("selectEntidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <EntidadData> lista = new ArrayList<>();
        EntidadData registro;
        try {
            while (rs.next()){
                registro = new EntidadData();
                registro.setId_entidad(rs.getInt("id_entidad"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setEmail(rs.getString("email"));
                registro.setTelefono1(rs.getString("telefono1"));
                registro.setTelefono2(rs.getString("telefono2"));
                registro.setAuditor(rs.getString("auditor"));
                registro.setSecretaria(rs.getString("secretaria"));
                registro.setCertImplante(rs.getString("certImplante"));
                registro.setRecomendaciones(rs.getString("recomendaciones"));                
                registro.setCuit(rs.getString("cuit"));                
                registro.setRiesgoCredito(rs.getDouble("riesgoCredito"));
                registro.setReqFacturacion(rs.getString("reqFacturacion"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setZona(rs.getString("zona"));
                registro.setFormaPago(rs.getString("formaPago"));
                registro.setPosicionIva(rs.getString("posicionIva"));
                registro.setClasiEntidad(rs.getString("clasiEntidad"));                
                registro.setComprasNombre(rs.getString("comprasNombre"));
                registro.setComprasTelefono(rs.getString("comprasTelefono"));
                registro.setComprasEmail(rs.getString("comprasEmail"));
                registro.setTesoreriaNombre(rs.getString("tesoreriaNombre"));
                registro.setTesoreriaTelefono(rs.getString("tesoreriaTelefono"));
                registro.setTesoreriaEmail(rs.getString("tesoreriaEmail"));
                registro.setContableNombre(rs.getString("contableNombre"));
                registro.setContableTelefono(rs.getString("contableTelefono"));
                registro.setContableEmail(rs.getString("contableEmail"));
                registro.setFarmaciaNombre(rs.getString("farmaciaNombre"));
                registro.setFarmaciaTelefono(rs.getString("farmaciaTelefono"));
                registro.setFarmaciaEmail(rs.getString("farmaciaEmail"));
                registro.setId_zona(rs.getInt("id_zona"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    public ArrayList <EntidadData> consulta(int id_zona, boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(id_zona);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaEntidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <EntidadData> lista = new ArrayList<>();
        EntidadData registro;
        try {
            while (rs.next()){
                registro = new EntidadData();
                registro.setId_entidad(rs.getInt("id_entidad"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setEmail(rs.getString("email"));
                registro.setTelefono1(rs.getString("telefono1"));
                registro.setTelefono2(rs.getString("telefono2"));
                registro.setAuditor(rs.getString("auditor"));
                registro.setSecretaria(rs.getString("secretaria"));
                registro.setCertImplante(rs.getString("certImplante"));
                registro.setRecomendaciones(rs.getString("recomendaciones"));                
                registro.setCuit(rs.getString("cuit"));                
                registro.setRiesgoCredito(rs.getDouble("riesgoCredito"));
                registro.setReqFacturacion(rs.getString("reqFacturacion"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setZona(rs.getString("zona"));
                registro.setFormaPago(rs.getString("formaPago"));
                registro.setPosicionIva(rs.getString("posicionIva"));
                registro.setClasiEntidad(rs.getString("clasiEntidad"));                
                registro.setComprasNombre(rs.getString("comprasNombre"));
                registro.setComprasTelefono(rs.getString("comprasTelefono"));
                registro.setComprasEmail(rs.getString("comprasEmail"));
                registro.setTesoreriaNombre(rs.getString("tesoreriaNombre"));
                registro.setTesoreriaTelefono(rs.getString("tesoreriaTelefono"));
                registro.setTesoreriaEmail(rs.getString("tesoreriaEmail"));
                registro.setContableNombre(rs.getString("contableNombre"));
                registro.setContableTelefono(rs.getString("contableTelefono"));
                registro.setContableEmail(rs.getString("contableEmail"));
                registro.setFarmaciaNombre(rs.getString("farmaciaNombre"));
                registro.setFarmaciaTelefono(rs.getString("farmaciaTelefono"));
                registro.setFarmaciaEmail(rs.getString("farmaciaEmail"));
                registro.setId_zona(rs.getInt("id_zona"));
                registro.setGln(rs.getString("gln"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
    
    public EntidadData trae(int id_entidad){
        parametros.clear();
        parametros.add(id_entidad);
        
        ResultSet rs = conexion.procAlmacenado("traeEntidad", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        EntidadData registro = null;
        try {
            while (rs.next()){
                registro = new EntidadData();
                registro.setId_entidad(rs.getInt("id_entidad"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setEmail(rs.getString("email"));
                registro.setTelefono1(rs.getString("telefono1"));
                registro.setTelefono2(rs.getString("telefono2"));
                registro.setAuditor(rs.getString("auditor"));
                registro.setSecretaria(rs.getString("secretaria"));
                registro.setCertImplante(rs.getString("certImplante"));
                registro.setRecomendaciones(rs.getString("recomendaciones"));                
                registro.setCuit(rs.getString("cuit"));                
                registro.setRiesgoCredito(rs.getDouble("riesgoCredito"));
                registro.setReqFacturacion(rs.getString("reqFacturacion"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setZona(rs.getString("zona"));
                registro.setFormaPago(rs.getString("formaPago"));
                registro.setPosicionIva(rs.getString("posicionIva"));
                registro.setClasiEntidad(rs.getString("clasiEntidad"));                
                registro.setComprasNombre(rs.getString("comprasNombre"));
                registro.setComprasTelefono(rs.getString("comprasTelefono"));
                registro.setComprasEmail(rs.getString("comprasEmail"));
                registro.setTesoreriaNombre(rs.getString("tesoreriaNombre"));
                registro.setTesoreriaTelefono(rs.getString("tesoreriaTelefono"));
                registro.setTesoreriaEmail(rs.getString("tesoreriaEmail"));
                registro.setContableNombre(rs.getString("contableNombre"));
                registro.setContableTelefono(rs.getString("contableTelefono"));
                registro.setContableEmail(rs.getString("contableEmail"));
                registro.setFarmaciaNombre(rs.getString("farmaciaNombre"));
                registro.setFarmaciaTelefono(rs.getString("farmaciaTelefono"));
                registro.setFarmaciaEmail(rs.getString("farmaciaEmail"));
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return registro;
    }
}
