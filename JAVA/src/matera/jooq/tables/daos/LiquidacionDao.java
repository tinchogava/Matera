/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Liquidacion;
import matera.jooq.tables.records.LiquidacionRecord;

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
public class LiquidacionDao extends DAOImpl<LiquidacionRecord, matera.jooq.tables.pojos.Liquidacion, Integer> {

	/**
	 * Create a new LiquidacionDao without any configuration
	 */
	public LiquidacionDao() {
		super(Liquidacion.LIQUIDACION, matera.jooq.tables.pojos.Liquidacion.class);
	}

	/**
	 * Create a new LiquidacionDao with an attached configuration
	 */
	@Autowired
	public LiquidacionDao(Configuration configuration) {
		super(Liquidacion.LIQUIDACION, matera.jooq.tables.pojos.Liquidacion.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Liquidacion object) {
		return object.getIdLiquidacion();
	}

	/**
	 * Fetch records that have <code>id_liquidacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Liquidacion> fetchByIdLiquidacion(Integer... values) {
		return fetch(Liquidacion.LIQUIDACION.ID_LIQUIDACION, values);
	}

	/**
	 * Fetch a unique record that has <code>id_liquidacion = value</code>
	 */
	public matera.jooq.tables.pojos.Liquidacion fetchOneByIdLiquidacion(Integer value) {
		return fetchOne(Liquidacion.LIQUIDACION.ID_LIQUIDACION, value);
	}

	/**
	 * Fetch records that have <code>fecha IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Liquidacion> fetchByFecha(Date... values) {
		return fetch(Liquidacion.LIQUIDACION.FECHA, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Liquidacion> fetchByObservaciones(String... values) {
		return fetch(Liquidacion.LIQUIDACION.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Liquidacion> fetchByIdEmpresa(Integer... values) {
		return fetch(Liquidacion.LIQUIDACION.ID_EMPRESA, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Liquidacion> fetchByUsuario(String... values) {
		return fetch(Liquidacion.LIQUIDACION.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Liquidacion> fetchByFechareal(Timestamp... values) {
		return fetch(Liquidacion.LIQUIDACION.FECHAREAL, values);
	}
}
