/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Reclamointerno;
import matera.jooq.tables.records.ReclamointernoRecord;

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
public class ReclamointernoDao extends DAOImpl<ReclamointernoRecord, matera.jooq.tables.pojos.Reclamointerno, Integer> {

	/**
	 * Create a new ReclamointernoDao without any configuration
	 */
	public ReclamointernoDao() {
		super(Reclamointerno.RECLAMOINTERNO, matera.jooq.tables.pojos.Reclamointerno.class);
	}

	/**
	 * Create a new ReclamointernoDao with an attached configuration
	 */
	@Autowired
	public ReclamointernoDao(Configuration configuration) {
		super(Reclamointerno.RECLAMOINTERNO, matera.jooq.tables.pojos.Reclamointerno.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Reclamointerno object) {
		return object.getIdReclamointerno();
	}

	/**
	 * Fetch records that have <code>id_reclamoInterno IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Reclamointerno> fetchByIdReclamointerno(Integer... values) {
		return fetch(Reclamointerno.RECLAMOINTERNO.ID_RECLAMOINTERNO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_reclamoInterno = value</code>
	 */
	public matera.jooq.tables.pojos.Reclamointerno fetchOneByIdReclamointerno(Integer value) {
		return fetchOne(Reclamointerno.RECLAMOINTERNO.ID_RECLAMOINTERNO, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Reclamointerno> fetchByNombre(String... values) {
		return fetch(Reclamointerno.RECLAMOINTERNO.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Reclamointerno> fetchByHabilita(String... values) {
		return fetch(Reclamointerno.RECLAMOINTERNO.HABILITA, values);
	}
}
