/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.LiquidacionOlp;
import matera.jooq.tables.records.LiquidacionOlpRecord;

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
public class LiquidacionOlpDao extends DAOImpl<LiquidacionOlpRecord, matera.jooq.tables.pojos.LiquidacionOlp, Integer> {

	/**
	 * Create a new LiquidacionOlpDao without any configuration
	 */
	public LiquidacionOlpDao() {
		super(LiquidacionOlp.LIQUIDACION_OLP, matera.jooq.tables.pojos.LiquidacionOlp.class);
	}

	/**
	 * Create a new LiquidacionOlpDao with an attached configuration
	 */
	@Autowired
	public LiquidacionOlpDao(Configuration configuration) {
		super(LiquidacionOlp.LIQUIDACION_OLP, matera.jooq.tables.pojos.LiquidacionOlp.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.LiquidacionOlp object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.LiquidacionOlp> fetchById(Integer... values) {
		return fetch(LiquidacionOlp.LIQUIDACION_OLP.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public matera.jooq.tables.pojos.LiquidacionOlp fetchOneById(Integer value) {
		return fetchOne(LiquidacionOlp.LIQUIDACION_OLP.ID, value);
	}

	/**
	 * Fetch records that have <code>olp IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.LiquidacionOlp> fetchByOlp(String... values) {
		return fetch(LiquidacionOlp.LIQUIDACION_OLP.OLP, values);
	}
}