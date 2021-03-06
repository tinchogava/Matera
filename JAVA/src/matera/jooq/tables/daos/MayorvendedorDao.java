/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Mayorvendedor;
import matera.jooq.tables.records.MayorvendedorRecord;

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
public class MayorvendedorDao extends DAOImpl<MayorvendedorRecord, matera.jooq.tables.pojos.Mayorvendedor, Integer> {

	/**
	 * Create a new MayorvendedorDao without any configuration
	 */
	public MayorvendedorDao() {
		super(Mayorvendedor.MAYORVENDEDOR, matera.jooq.tables.pojos.Mayorvendedor.class);
	}

	/**
	 * Create a new MayorvendedorDao with an attached configuration
	 */
	@Autowired
	public MayorvendedorDao(Configuration configuration) {
		super(Mayorvendedor.MAYORVENDEDOR, matera.jooq.tables.pojos.Mayorvendedor.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Mayorvendedor object) {
		return object.getIdMayorvendedor();
	}

	/**
	 * Fetch records that have <code>id_mayorVendedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByIdMayorvendedor(Integer... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.ID_MAYORVENDEDOR, values);
	}

	/**
	 * Fetch a unique record that has <code>id_mayorVendedor = value</code>
	 */
	public matera.jooq.tables.pojos.Mayorvendedor fetchOneByIdMayorvendedor(Integer value) {
		return fetchOne(Mayorvendedor.MAYORVENDEDOR.ID_MAYORVENDEDOR, value);
	}

	/**
	 * Fetch records that have <code>fecha IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByFecha(Date... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.FECHA, values);
	}

	/**
	 * Fetch records that have <code>id_presupuesto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByIdPresupuesto(Integer... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.ID_PRESUPUESTO, values);
	}

	/**
	 * Fetch records that have <code>id_vendedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByIdVendedor(Integer... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.ID_VENDEDOR, values);
	}

	/**
	 * Fetch records that have <code>dc IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByDc(String... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.DC, values);
	}

	/**
	 * Fetch records that have <code>importe IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByImporte(BigDecimal... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.IMPORTE, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByObservaciones(String... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>liquidacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByLiquidacion(Integer... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.LIQUIDACION, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByIdEmpresa(Integer... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.ID_EMPRESA, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByUsuario(String... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Mayorvendedor> fetchByFechareal(Timestamp... values) {
		return fetch(Mayorvendedor.MAYORVENDEDOR.FECHAREAL, values);
	}
}
