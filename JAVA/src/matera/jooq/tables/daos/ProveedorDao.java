/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Proveedor;
import matera.jooq.tables.records.ProveedorRecord;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
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
public class ProveedorDao extends DAOImpl<ProveedorRecord, matera.jooq.tables.pojos.Proveedor, Integer> {

	/**
	 * Create a new ProveedorDao without any configuration
	 */
	public ProveedorDao() {
		super(Proveedor.PROVEEDOR, matera.jooq.tables.pojos.Proveedor.class);
	}

	/**
	 * Create a new ProveedorDao with an attached configuration
	 */
	@Autowired
	public ProveedorDao(Configuration configuration) {
		super(Proveedor.PROVEEDOR, matera.jooq.tables.pojos.Proveedor.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Proveedor object) {
		return object.getIdProveedor();
	}

	/**
	 * Fetch records that have <code>id_proveedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByIdProveedor(Integer... values) {
		return fetch(Proveedor.PROVEEDOR.ID_PROVEEDOR, values);
	}

	/**
	 * Fetch a unique record that has <code>id_proveedor = value</code>
	 */
	public matera.jooq.tables.pojos.Proveedor fetchOneByIdProveedor(Integer value) {
		return fetchOne(Proveedor.PROVEEDOR.ID_PROVEEDOR, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByNombre(String... values) {
		return fetch(Proveedor.PROVEEDOR.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>direccion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByDireccion(String... values) {
		return fetch(Proveedor.PROVEEDOR.DIRECCION, values);
	}

	/**
	 * Fetch records that have <code>id_localidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByIdLocalidad(Integer... values) {
		return fetch(Proveedor.PROVEEDOR.ID_LOCALIDAD, values);
	}

	/**
	 * Fetch records that have <code>id_departamento IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByIdDepartamento(Integer... values) {
		return fetch(Proveedor.PROVEEDOR.ID_DEPARTAMENTO, values);
	}

	/**
	 * Fetch records that have <code>id_provincia IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByIdProvincia(Integer... values) {
		return fetch(Proveedor.PROVEEDOR.ID_PROVINCIA, values);
	}

	/**
	 * Fetch records that have <code>codPostal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByCodpostal(String... values) {
		return fetch(Proveedor.PROVEEDOR.CODPOSTAL, values);
	}

	/**
	 * Fetch records that have <code>telefono1 IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByTelefono1(String... values) {
		return fetch(Proveedor.PROVEEDOR.TELEFONO1, values);
	}

	/**
	 * Fetch records that have <code>telefono2 IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByTelefono2(String... values) {
		return fetch(Proveedor.PROVEEDOR.TELEFONO2, values);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByEmail(String... values) {
		return fetch(Proveedor.PROVEEDOR.EMAIL, values);
	}

	/**
	 * Fetch records that have <code>cuit IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByCuit(String... values) {
		return fetch(Proveedor.PROVEEDOR.CUIT, values);
	}

	/**
	 * Fetch records that have <code>gerente IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByGerente(String... values) {
		return fetch(Proveedor.PROVEEDOR.GERENTE, values);
	}

	/**
	 * Fetch records that have <code>cuentaBanco IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByCuentabanco(String... values) {
		return fetch(Proveedor.PROVEEDOR.CUENTABANCO, values);
	}

	/**
	 * Fetch records that have <code>id_formaPago IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByIdFormapago(Integer... values) {
		return fetch(Proveedor.PROVEEDOR.ID_FORMAPAGO, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByObservaciones(String... values) {
		return fetch(Proveedor.PROVEEDOR.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>gln IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByGln(String... values) {
		return fetch(Proveedor.PROVEEDOR.GLN, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByHabilita(String... values) {
		return fetch(Proveedor.PROVEEDOR.HABILITA, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Proveedor> fetchByIdEmpresa(Integer... values) {
		return fetch(Proveedor.PROVEEDOR.ID_EMPRESA, values);
	}
}
