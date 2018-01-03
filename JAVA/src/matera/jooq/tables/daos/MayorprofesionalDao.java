/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Mayorprofesional;
import matera.jooq.tables.records.MayorprofesionalRecord;

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
public class MayorprofesionalDao extends DAOImpl<MayorprofesionalRecord, matera.jooq.tables.pojos.Mayorprofesional, Integer> {

	/**
	 * Create a new MayorprofesionalDao without any configuration
	 */
	public MayorprofesionalDao() {
		super(Mayorprofesional.MAYORPROFESIONAL, matera.jooq.tables.pojos.Mayorprofesional.class);
	}

	/**
	 * Create a new MayorprofesionalDao with an attached configuration
	 */
	@Autowired
	public MayorprofesionalDao(Configuration configuration) {
		super(Mayorprofesional.MAYORPROFESIONAL, matera.jooq.tables.pojos.Mayorprofesional.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Mayorprofesional object) {
		return object.getIdMayorprofesional();
	}

	/**
	 * Fetch records that have <code>id_mayorProfesional IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByIdMayorprofesional(Integer... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.ID_MAYORPROFESIONAL, values);
	}

	/**
	 * Fetch a unique record that has <code>id_mayorProfesional = value</code>
	 */
	public matera.jooq.tables.pojos.Mayorprofesional fetchOneByIdMayorprofesional(Integer value) {
		return fetchOne(Mayorprofesional.MAYORPROFESIONAL.ID_MAYORPROFESIONAL, value);
	}

	/**
	 * Fetch records that have <code>fecha IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByFecha(Date... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.FECHA, values);
	}

	/**
	 * Fetch records that have <code>id_presupuesto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByIdPresupuesto(Integer... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.ID_PRESUPUESTO, values);
	}

	/**
	 * Fetch records that have <code>id_profesional IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByIdProfesional(Integer... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.ID_PROFESIONAL, values);
	}

	/**
	 * Fetch records that have <code>dc IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByDc(String... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.DC, values);
	}

	/**
	 * Fetch records that have <code>importe IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByImporte(BigDecimal... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.IMPORTE, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByObservaciones(String... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>preliquidacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByPreliquidacion(Integer... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.PRELIQUIDACION, values);
	}

	/**
	 * Fetch records that have <code>liquidacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByLiquidacion(Integer... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.LIQUIDACION, values);
	}

	/**
	 * Fetch records that have <code>transferencia IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByTransferencia(String... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.TRANSFERENCIA, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByIdEmpresa(Integer... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.ID_EMPRESA, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByUsuario(String... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorprofesional> fetchByFechareal(Timestamp... values) {
		return fetch(Mayorprofesional.MAYORPROFESIONAL.FECHAREAL, values);
	}
}