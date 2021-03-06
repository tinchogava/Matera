/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Mayortecnico;
import matera.jooq.tables.records.MayortecnicoRecord;

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
public class MayortecnicoDao extends DAOImpl<MayortecnicoRecord, matera.jooq.tables.pojos.Mayortecnico, Integer> {

	/**
	 * Create a new MayortecnicoDao without any configuration
	 */
	public MayortecnicoDao() {
		super(Mayortecnico.MAYORTECNICO, matera.jooq.tables.pojos.Mayortecnico.class);
	}

	/**
	 * Create a new MayortecnicoDao with an attached configuration
	 */
	@Autowired
	public MayortecnicoDao(Configuration configuration) {
		super(Mayortecnico.MAYORTECNICO, matera.jooq.tables.pojos.Mayortecnico.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Mayortecnico object) {
		return object.getIdMayortecnico();
	}

	/**
	 * Fetch records that have <code>id_mayorTecnico IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByIdMayortecnico(Integer... values) {
		return fetch(Mayortecnico.MAYORTECNICO.ID_MAYORTECNICO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_mayorTecnico = value</code>
	 */
	public matera.jooq.tables.pojos.Mayortecnico fetchOneByIdMayortecnico(Integer value) {
		return fetchOne(Mayortecnico.MAYORTECNICO.ID_MAYORTECNICO, value);
	}

	/**
	 * Fetch records that have <code>fecha IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByFecha(Date... values) {
		return fetch(Mayortecnico.MAYORTECNICO.FECHA, values);
	}

	/**
	 * Fetch records that have <code>id_presupuesto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByIdPresupuesto(Integer... values) {
		return fetch(Mayortecnico.MAYORTECNICO.ID_PRESUPUESTO, values);
	}

	/**
	 * Fetch records that have <code>id_tecnico IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByIdTecnico(Integer... values) {
		return fetch(Mayortecnico.MAYORTECNICO.ID_TECNICO, values);
	}

	/**
	 * Fetch records that have <code>dc IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByDc(String... values) {
		return fetch(Mayortecnico.MAYORTECNICO.DC, values);
	}

	/**
	 * Fetch records that have <code>importe IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByImporte(BigDecimal... values) {
		return fetch(Mayortecnico.MAYORTECNICO.IMPORTE, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByObservaciones(String... values) {
		return fetch(Mayortecnico.MAYORTECNICO.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>liquidacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByLiquidacion(Integer... values) {
		return fetch(Mayortecnico.MAYORTECNICO.LIQUIDACION, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByIdEmpresa(Integer... values) {
		return fetch(Mayortecnico.MAYORTECNICO.ID_EMPRESA, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByUsuario(String... values) {
		return fetch(Mayortecnico.MAYORTECNICO.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayortecnico> fetchByFechareal(Timestamp... values) {
		return fetch(Mayortecnico.MAYORTECNICO.FECHAREAL, values);
	}
}
