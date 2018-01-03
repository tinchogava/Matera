/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Tipocomp;
import matera.jooq.tables.records.TipocompRecord;

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
public class TipocompDao extends DAOImpl<TipocompRecord, matera.jooq.tables.pojos.Tipocomp, Integer> {

	/**
	 * Create a new TipocompDao without any configuration
	 */
	public TipocompDao() {
		super(Tipocomp.TIPOCOMP, matera.jooq.tables.pojos.Tipocomp.class);
	}

	/**
	 * Create a new TipocompDao with an attached configuration
	 */
	@Autowired
	public TipocompDao(Configuration configuration) {
		super(Tipocomp.TIPOCOMP, matera.jooq.tables.pojos.Tipocomp.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Tipocomp object) {
		return object.getIdTipocomp();
	}

	/**
	 * Fetch records that have <code>id_tipoComp IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Tipocomp> fetchByIdTipocomp(Integer... values) {
		return fetch(Tipocomp.TIPOCOMP.ID_TIPOCOMP, values);
	}

	/**
	 * Fetch a unique record that has <code>id_tipoComp = value</code>
	 */
	public matera.jooq.tables.pojos.Tipocomp fetchOneByIdTipocomp(Integer value) {
		return fetchOne(Tipocomp.TIPOCOMP.ID_TIPOCOMP, value);
	}

	/**
	 * Fetch records that have <code>codigo IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Tipocomp> fetchByCodigo(String... values) {
		return fetch(Tipocomp.TIPOCOMP.CODIGO, values);
	}

	/**
	 * Fetch records that have <code>denominacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Tipocomp> fetchByDenominacion(String... values) {
		return fetch(Tipocomp.TIPOCOMP.DENOMINACION, values);
	}

	/**
	 * Fetch records that have <code>abreviatura IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Tipocomp> fetchByAbreviatura(String... values) {
		return fetch(Tipocomp.TIPOCOMP.ABREVIATURA, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Tipocomp> fetchByHabilita(String... values) {
		return fetch(Tipocomp.TIPOCOMP.HABILITA, values);
	}
}
