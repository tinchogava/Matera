/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Zona;
import matera.jooq.tables.records.ZonaRecord;

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
public class ZonaDao extends DAOImpl<ZonaRecord, matera.jooq.tables.pojos.Zona, Integer> {

	/**
	 * Create a new ZonaDao without any configuration
	 */
	public ZonaDao() {
		super(Zona.ZONA, matera.jooq.tables.pojos.Zona.class);
	}

	/**
	 * Create a new ZonaDao with an attached configuration
	 */
	@Autowired
	public ZonaDao(Configuration configuration) {
		super(Zona.ZONA, matera.jooq.tables.pojos.Zona.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Zona object) {
		return object.getIdZona();
	}

	/**
	 * Fetch records that have <code>id_zona IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Zona> fetchByIdZona(Integer... values) {
		return fetch(Zona.ZONA.ID_ZONA, values);
	}

	/**
	 * Fetch a unique record that has <code>id_zona = value</code>
	 */
	public matera.jooq.tables.pojos.Zona fetchOneByIdZona(Integer value) {
		return fetchOne(Zona.ZONA.ID_ZONA, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Zona> fetchByNombre(String... values) {
		return fetch(Zona.ZONA.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Zona> fetchByHabilita(String... values) {
		return fetch(Zona.ZONA.HABILITA, values);
	}
}
