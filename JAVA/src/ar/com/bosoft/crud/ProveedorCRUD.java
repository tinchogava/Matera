/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.crud;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.ProveedorData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ProveedorCRUD {
    Conexion conexion;
    int id_empresa;
    String empresa;
    ArrayList parametros;
    
    public ProveedorCRUD(Conexion conexion, int id_empresa, String empresa) {
        this.conexion = conexion;
        this.id_empresa = id_empresa;
        this.empresa = empresa;
        this.parametros = new ArrayList();
    }
    
    public void insert(ProveedorData nuevo){
        parametros.clear();
        parametros.add(nuevo.getId_proveedor());
        parametros.add(nuevo.getNombre());
        parametros.add(nuevo.getDireccion());
        parametros.add(nuevo.getId_localidad());
        parametros.add(nuevo.getId_departamento());
        parametros.add(nuevo.getId_provincia());
        parametros.add(nuevo.getCodPostal());
        parametros.add(nuevo.getTelefono1());
        parametros.add(nuevo.getTelefono2());
        parametros.add(nuevo.getEmail());
        parametros.add(nuevo.getCuit());
        parametros.add(nuevo.getGerente());
        parametros.add(nuevo.getCuentaBanco());
        parametros.add(nuevo.getId_formaPago());
        parametros.add(nuevo.getObservaciones());
        parametros.add(nuevo.getGln());
        parametros.add(nuevo.getPagoNombre());
        parametros.add(nuevo.getPagoTelefono());
        parametros.add(nuevo.getPagoEmail());
        parametros.add(nuevo.getClienteNombre());
        parametros.add(nuevo.getClienteTelefono());
        parametros.add(nuevo.getClienteEmail());
        parametros.add(nuevo.getDepositoNombre());
        parametros.add(nuevo.getDepositoTelefono());
        parametros.add(nuevo.getDepositoEmail());
        parametros.add(nuevo.getHabilita());
        parametros.add(this.id_empresa);
        
        conexion.procAlmacenado("insertProveedor", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
    }
    
    public ArrayList <ProveedorData> consulta(boolean soloHabilitados){
        parametros.clear();
        parametros.add(this.id_empresa);
        parametros.add(soloHabilitados);
        
        ResultSet rs = conexion.procAlmacenado("consultaProveedor", parametros, 
                this.empresa, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
        
        ArrayList <ProveedorData> lista = new ArrayList<>();
        ProveedorData registro;
        try {
            while (rs.next()){
                registro = new ProveedorData();
                registro.setId_proveedor(rs.getInt("id_proveedor"));
                registro.setNombre(rs.getString("nombre"));
                registro.setDireccion(rs.getString("direccion"));
                registro.setCodPostal(rs.getString("codPostal"));
                registro.setTelefono1(rs.getString("telefono1"));
                registro.setTelefono2(rs.getString("telefono2"));
                registro.setEmail(rs.getString("email"));
                registro.setCuit(rs.getString("cuit"));                
                registro.setGerente(rs.getString("gerente"));
                registro.setCuentaBanco(rs.getString("cuentaBanco"));
                registro.setObservaciones(rs.getString("observaciones"));
                registro.setGln(rs.getString("gln"));
                registro.setHabilita(rs.getString("habilita"));
                registro.setLocalidad(rs.getString("localidad"));
                registro.setDepartamento(rs.getString("departamento"));
                registro.setProvincia(rs.getString("provincia"));
                registro.setFormaPago(rs.getString("formaPago"));
                registro.setPagoNombre(rs.getString("pagoNombre"));
                registro.setPagoTelefono(rs.getString("pagoTelefono"));
                registro.setPagoEmail(rs.getString("pagoEmail"));
                registro.setClienteNombre(rs.getString("clienteNombre"));
                registro.setClienteTelefono(rs.getString("clienteTelefono"));
                registro.setClienteEmail(rs.getString("clienteEmail"));
                registro.setDepositoNombre(rs.getString("depositoNombre"));
                registro.setDepositoTelefono(rs.getString("depositoTelefono"));
                registro.setDepositoEmail(rs.getString("depositoEmail"));
                
                lista.add(registro);
            }
            
            rs.close();
        } catch (SQLException ex) {
            Utiles.enviaError(this.empresa,ex);
        }
        return lista;
    }
}
