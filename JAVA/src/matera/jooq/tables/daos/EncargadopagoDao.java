/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Encargadopago;
import matera.jooq.tables.records.EncargadopagoRecord;

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
public class EncargadopagoDao extends DAOImpl<EncargadopagoRecord, matera.jooq.tables.pojos.Encargadopago, Integer> {

	/**
	 * Create a new EncargadopagoDao without any configuration
	 */
	public EncargadopagoDao() {
		super(Encargadopago.ENCARGADOPAGO, matera.jooq.tables.pojos.Encargadopago.class);
	}

	/**
	 * Create a new EncargadopagoDao with an attached configuration
	 */
	@Autowired
	public EncargadopagoDao(Configuration configuration) {
		super(Encargadopago.ENCARGADOPAGO, matera.jooq.tables.pojos.Encargadopago.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Encargadopago object) {
		return object.getIdEncargadopago();
	}

	/**
	 * Fetch records that have <code>id_encargadopago IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadopago> fetchByIdEncargadopago(Integer... values) {
		return fetch(Encargadopago.ENCARGADOPAGO.ID_ENCARGADOPAGO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_encargadopago = value</code>
	 */
	public matera.jooq.tables.pojos.Encargadopago fetchOneByIdEncargadopago(Integer value) {
		return fetchOne(Encargadopago.ENCARGADOPAGO.ID_ENCARGADOPAGO, value);
	}

	/**
	 * Fetch records that have <code>id_proveedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadopago> fetchByIdProveedor(Integer... values) {
		return fetch(Encargadopago.ENCARGADOPAGO.ID_PROVEEDOR, values);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadopago> fetchByNombre(String... values) {
		return fetch(Encargadopago.ENCARGADOPAGO.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>telefono IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadopago> fetchByTelefono(String... values) {
		return fetch(Encargadopago.ENCARGADOPAGO.TELEFONO, values);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadopago> fetchByEmail(String... values) {
		return fetch(Encargadopago.ENCARGADOPAGO.EMAIL, values);
	}
}
