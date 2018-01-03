/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Codconsumo;
import matera.jooq.tables.records.CodconsumoRecord;

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
public class CodconsumoDao extends DAOImpl<CodconsumoRecord, matera.jooq.tables.pojos.Codconsumo, Integer> {

	/**
	 * Create a new CodconsumoDao without any configuration
	 */
	public CodconsumoDao() {
		super(Codconsumo.CODCONSUMO, matera.jooq.tables.pojos.Codconsumo.class);
	}

	/**
	 * Create a new CodconsumoDao with an attached configuration
	 */
	@Autowired
	public CodconsumoDao(Configuration configuration) {
		super(Codconsumo.CODCONSUMO, matera.jooq.tables.pojos.Codconsumo.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Codconsumo object) {
		return object.getIdCodconsumo();
	}

	/**
	 * Fetch records that have <code>id_codConsumo IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Codconsumo> fetchByIdCodconsumo(Integer... values) {
		return fetch(Codconsumo.CODCONSUMO.ID_CODCONSUMO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_codConsumo = value</code>
	 */
	public matera.jooq.tables.pojos.Codconsumo fetchOneByIdCodconsumo(Integer value) {
		return fetchOne(Codconsumo.CODCONSUMO.ID_CODCONSUMO, value);
	}

	/**
	 * Fetch records that have <code>codigo IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Codconsumo> fetchByCodigo(String... values) {
		return fetch(Codconsumo.CODCONSUMO.CODIGO, values);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Codconsumo> fetchByNombre(String... values) {
		return fetch(Codconsumo.CODCONSUMO.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Codconsumo> fetchByHabilita(String... values) {
		return fetch(Codconsumo.CODCONSUMO.HABILITA, values);
	}
}