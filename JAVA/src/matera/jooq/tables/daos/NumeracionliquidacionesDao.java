/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Numeracionliquidaciones;
import matera.jooq.tables.records.NumeracionliquidacionesRecord;

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
public class NumeracionliquidacionesDao extends DAOImpl<NumeracionliquidacionesRecord, matera.jooq.tables.pojos.Numeracionliquidaciones, Integer> {

	/**
	 * Create a new NumeracionliquidacionesDao without any configuration
	 */
	public NumeracionliquidacionesDao() {
		super(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES, matera.jooq.tables.pojos.Numeracionliquidaciones.class);
	}

	/**
	 * Create a new NumeracionliquidacionesDao with an attached configuration
	 */
	@Autowired
	public NumeracionliquidacionesDao(Configuration configuration) {
		super(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES, matera.jooq.tables.pojos.Numeracionliquidaciones.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Numeracionliquidaciones object) {
		return object.getIdNumeracionliquidaciones();
	}

	/**
	 * Fetch records that have <code>id_numeracionLiquidaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Numeracionliquidaciones> fetchByIdNumeracionliquidaciones(Integer... values) {
		return fetch(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES.ID_NUMERACIONLIQUIDACIONES, values);
	}

	/**
	 * Fetch a unique record that has <code>id_numeracionLiquidaciones = value</code>
	 */
	public matera.jooq.tables.pojos.Numeracionliquidaciones fetchOneByIdNumeracionliquidaciones(Integer value) {
		return fetchOne(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES.ID_NUMERACIONLIQUIDACIONES, value);
	}

	/**
	 * Fetch records that have <code>tecnico IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Numeracionliquidaciones> fetchByTecnico(Integer... values) {
		return fetch(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES.TECNICO, values);
	}

	/**
	 * Fetch records that have <code>profesional IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Numeracionliquidaciones> fetchByProfesional(Integer... values) {
		return fetch(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES.PROFESIONAL, values);
	}

	/**
	 * Fetch records that have <code>vendedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Numeracionliquidaciones> fetchByVendedor(Integer... values) {
		return fetch(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES.VENDEDOR, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Numeracionliquidaciones> fetchByIdEmpresa(Integer... values) {
		return fetch(Numeracionliquidaciones.NUMERACIONLIQUIDACIONES.ID_EMPRESA, values);
	}
}
