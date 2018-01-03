/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Encargadotesoreria;
import matera.jooq.tables.records.EncargadotesoreriaRecord;

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
public class EncargadotesoreriaDao extends DAOImpl<EncargadotesoreriaRecord, matera.jooq.tables.pojos.Encargadotesoreria, Integer> {

	/**
	 * Create a new EncargadotesoreriaDao without any configuration
	 */
	public EncargadotesoreriaDao() {
		super(Encargadotesoreria.ENCARGADOTESORERIA, matera.jooq.tables.pojos.Encargadotesoreria.class);
	}

	/**
	 * Create a new EncargadotesoreriaDao with an attached configuration
	 */
	@Autowired
	public EncargadotesoreriaDao(Configuration configuration) {
		super(Encargadotesoreria.ENCARGADOTESORERIA, matera.jooq.tables.pojos.Encargadotesoreria.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Encargadotesoreria object) {
		return object.getIdEncargadotesoreria();
	}

	/**
	 * Fetch records that have <code>id_encargadoTesoreria IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadotesoreria> fetchByIdEncargadotesoreria(Integer... values) {
		return fetch(Encargadotesoreria.ENCARGADOTESORERIA.ID_ENCARGADOTESORERIA, values);
	}

	/**
	 * Fetch a unique record that has <code>id_encargadoTesoreria = value</code>
	 */
	public matera.jooq.tables.pojos.Encargadotesoreria fetchOneByIdEncargadotesoreria(Integer value) {
		return fetchOne(Encargadotesoreria.ENCARGADOTESORERIA.ID_ENCARGADOTESORERIA, value);
	}

	/**
	 * Fetch records that have <code>id_entidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadotesoreria> fetchByIdEntidad(Integer... values) {
		return fetch(Encargadotesoreria.ENCARGADOTESORERIA.ID_ENTIDAD, values);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadotesoreria> fetchByNombre(String... values) {
		return fetch(Encargadotesoreria.ENCARGADOTESORERIA.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>telefono IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadotesoreria> fetchByTelefono(String... values) {
		return fetch(Encargadotesoreria.ENCARGADOTESORERIA.TELEFONO, values);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Encargadotesoreria> fetchByEmail(String... values) {
		return fetch(Encargadotesoreria.ENCARGADOTESORERIA.EMAIL, values);
	}
}
