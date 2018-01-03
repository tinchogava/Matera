/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Forecast;
import matera.jooq.tables.records.ForecastRecord;

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
public class ForecastDao extends DAOImpl<ForecastRecord, matera.jooq.tables.pojos.Forecast, Integer> {

	/**
	 * Create a new ForecastDao without any configuration
	 */
	public ForecastDao() {
		super(Forecast.FORECAST, matera.jooq.tables.pojos.Forecast.class);
	}

	/**
	 * Create a new ForecastDao with an attached configuration
	 */
	@Autowired
	public ForecastDao(Configuration configuration) {
		super(Forecast.FORECAST, matera.jooq.tables.pojos.Forecast.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Forecast object) {
		return object.getIdForecast();
	}

	/**
	 * Fetch records that have <code>id_forecast IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByIdForecast(Integer... values) {
		return fetch(Forecast.FORECAST.ID_FORECAST, values);
	}

	/**
	 * Fetch a unique record that has <code>id_forecast = value</code>
	 */
	public matera.jooq.tables.pojos.Forecast fetchOneByIdForecast(Integer value) {
		return fetchOne(Forecast.FORECAST.ID_FORECAST, value);
	}

	/**
	 * Fetch records that have <code>anio IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByAnio(Integer... values) {
		return fetch(Forecast.FORECAST.ANIO, values);
	}

	/**
	 * Fetch records that have <code>id_vendedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByIdVendedor(Integer... values) {
		return fetch(Forecast.FORECAST.ID_VENDEDOR, values);
	}

	/**
	 * Fetch records that have <code>id_entidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByIdEntidad(Integer... values) {
		return fetch(Forecast.FORECAST.ID_ENTIDAD, values);
	}

	/**
	 * Fetch records that have <code>id_productoFact IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByIdProductofact(Integer... values) {
		return fetch(Forecast.FORECAST.ID_PRODUCTOFACT, values);
	}

	/**
	 * Fetch records that have <code>cantidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByCantidad(Double... values) {
		return fetch(Forecast.FORECAST.CANTIDAD, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByUsuario(String... values) {
		return fetch(Forecast.FORECAST.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Forecast> fetchByFechareal(Timestamp... values) {
		return fetch(Forecast.FORECAST.FECHAREAL, values);
	}
}