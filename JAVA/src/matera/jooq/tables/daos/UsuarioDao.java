/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.sql.Date;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Usuario;
import matera.jooq.tables.records.UsuarioRecord;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class UsuarioDao extends DAOImpl<UsuarioRecord, matera.jooq.tables.pojos.Usuario, UInteger> {

	/**
	 * Create a new UsuarioDao without any configuration
	 */
	public UsuarioDao() {
		super(Usuario.USUARIO, matera.jooq.tables.pojos.Usuario.class);
	}

	/**
	 * Create a new UsuarioDao with an attached configuration
	 */
	@Autowired
	public UsuarioDao(Configuration configuration) {
		super(Usuario.USUARIO, matera.jooq.tables.pojos.Usuario.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UInteger getId(matera.jooq.tables.pojos.Usuario object) {
		return object.getIdUsuario();
	}

	/**
	 * Fetch records that have <code>id_usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByIdUsuario(UInteger... values) {
		return fetch(Usuario.USUARIO.ID_USUARIO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_usuario = value</code>
	 */
	public matera.jooq.tables.pojos.Usuario fetchOneByIdUsuario(UInteger value) {
		return fetchOne(Usuario.USUARIO.ID_USUARIO, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByNombre(String... values) {
		return fetch(Usuario.USUARIO.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>documento IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByDocumento(String... values) {
		return fetch(Usuario.USUARIO.DOCUMENTO, values);
	}

	/**
	 * Fetch records that have <code>cuil IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByCuil(String... values) {
		return fetch(Usuario.USUARIO.CUIL, values);
	}

	/**
	 * Fetch records that have <code>fechaNac IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByFechanac(Date... values) {
		return fetch(Usuario.USUARIO.FECHANAC, values);
	}

	/**
	 * Fetch records that have <code>direccion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByDireccion(String... values) {
		return fetch(Usuario.USUARIO.DIRECCION, values);
	}

	/**
	 * Fetch records that have <code>codPostal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByCodpostal(String... values) {
		return fetch(Usuario.USUARIO.CODPOSTAL, values);
	}

	/**
	 * Fetch records that have <code>id_provincia IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByIdProvincia(Integer... values) {
		return fetch(Usuario.USUARIO.ID_PROVINCIA, values);
	}

	/**
	 * Fetch records that have <code>id_departamento IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByIdDepartamento(Integer... values) {
		return fetch(Usuario.USUARIO.ID_DEPARTAMENTO, values);
	}

	/**
	 * Fetch records that have <code>id_localidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByIdLocalidad(Integer... values) {
		return fetch(Usuario.USUARIO.ID_LOCALIDAD, values);
	}

	/**
	 * Fetch records that have <code>fechaIngreso IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByFechaingreso(Date... values) {
		return fetch(Usuario.USUARIO.FECHAINGRESO, values);
	}

	/**
	 * Fetch records that have <code>fechaEgreso IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByFechaegreso(Date... values) {
		return fetch(Usuario.USUARIO.FECHAEGRESO, values);
	}

	/**
	 * Fetch records that have <code>usaSistema IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByUsasistema(String... values) {
		return fetch(Usuario.USUARIO.USASISTEMA, values);
	}

	/**
	 * Fetch records that have <code>contraseña IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByContraseña(String... values) {
		return fetch(Usuario.USUARIO.CONTRASEÑA, values);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByEmail(String... values) {
		return fetch(Usuario.USUARIO.EMAIL, values);
	}

	/**
	 * Fetch records that have <code>id_zona IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByIdZona(Integer... values) {
		return fetch(Usuario.USUARIO.ID_ZONA, values);
	}

	/**
	 * Fetch records that have <code>id_zonaSistema IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByIdZonasistema(Integer... values) {
		return fetch(Usuario.USUARIO.ID_ZONASISTEMA, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByHabilita(String... values) {
		return fetch(Usuario.USUARIO.HABILITA, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Usuario> fetchByIdEmpresa(Integer... values) {
		return fetch(Usuario.USUARIO.ID_EMPRESA, values);
	}
}