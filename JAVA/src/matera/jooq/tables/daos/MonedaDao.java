/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Moneda;
import matera.jooq.tables.records.MonedaRecord;

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
public class MonedaDao extends DAOImpl<MonedaRecord, matera.jooq.tables.pojos.Moneda, Integer> {

	/**
	 * Create a new MonedaDao without any configuration
	 */
	public MonedaDao() {
		super(Moneda.MONEDA, matera.jooq.tables.pojos.Moneda.class);
	}

	/**
	 * Create a new MonedaDao with an attached configuration
	 */
	@Autowired
	public MonedaDao(Configuration configuration) {
		super(Moneda.MONEDA, matera.jooq.tables.pojos.Moneda.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Moneda object) {
		return object.getIdMoneda();
	}

	/**
	 * Fetch records that have <code>id_moneda IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Moneda> fetchByIdMoneda(Integer... values) {
		return fetch(Moneda.MONEDA.ID_MONEDA, values);
	}

	/**
	 * Fetch a unique record that has <code>id_moneda = value</code>
	 */
	public matera.jooq.tables.pojos.Moneda fetchOneByIdMoneda(Integer value) {
		return fetchOne(Moneda.MONEDA.ID_MONEDA, value);
	}

	/**
	 * Fetch records that have <code>codigo IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Moneda> fetchByCodigo(String... values) {
		return fetch(Moneda.MONEDA.CODIGO, values);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Moneda> fetchByNombre(String... values) {
		return fetch(Moneda.MONEDA.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>simbolo IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Moneda> fetchBySimbolo(String... values) {
		return fetch(Moneda.MONEDA.SIMBOLO, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Moneda> fetchByHabilita(String... values) {
		return fetch(Moneda.MONEDA.HABILITA, values);
	}

	/**
	 * Fetch records that have <code>cotizacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Moneda> fetchByCotizacion(BigDecimal... values) {
		return fetch(Moneda.MONEDA.COTIZACION, values);
	}
}