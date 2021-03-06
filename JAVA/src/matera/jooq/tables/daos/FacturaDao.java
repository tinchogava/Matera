/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Factura;
import matera.jooq.tables.records.FacturaRecord;

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
public class FacturaDao extends DAOImpl<FacturaRecord, matera.jooq.tables.pojos.Factura, Integer> {

	/**
	 * Create a new FacturaDao without any configuration
	 */
	public FacturaDao() {
		super(Factura.FACTURA, matera.jooq.tables.pojos.Factura.class);
	}

	/**
	 * Create a new FacturaDao with an attached configuration
	 */
	@Autowired
	public FacturaDao(Configuration configuration) {
		super(Factura.FACTURA, matera.jooq.tables.pojos.Factura.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Factura object) {
		return object.getIdFactura();
	}

	/**
	 * Fetch records that have <code>id_factura IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdFactura(Integer... values) {
		return fetch(Factura.FACTURA.ID_FACTURA, values);
	}

	/**
	 * Fetch a unique record that has <code>id_factura = value</code>
	 */
	public matera.jooq.tables.pojos.Factura fetchOneByIdFactura(Integer value) {
		return fetchOne(Factura.FACTURA.ID_FACTURA, value);
	}

	/**
	 * Fetch records that have <code>fecha IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByFecha(Date... values) {
		return fetch(Factura.FACTURA.FECHA, values);
	}

	/**
	 * Fetch records that have <code>id_presupuesto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdPresupuesto(Integer... values) {
		return fetch(Factura.FACTURA.ID_PRESUPUESTO, values);
	}

	/**
	 * Fetch records that have <code>id_tipoComp IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdTipocomp(Integer... values) {
		return fetch(Factura.FACTURA.ID_TIPOCOMP, values);
	}

	/**
	 * Fetch records that have <code>numComp IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByNumcomp(String... values) {
		return fetch(Factura.FACTURA.NUMCOMP, values);
	}

	/**
	 * Fetch records that have <code>id_tipoCompRel IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdTipocomprel(Integer... values) {
		return fetch(Factura.FACTURA.ID_TIPOCOMPREL, values);
	}

	/**
	 * Fetch records that have <code>numCompRel IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByNumcomprel(String... values) {
		return fetch(Factura.FACTURA.NUMCOMPREL, values);
	}

	/**
	 * Fetch records that have <code>vencimiento IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByVencimiento(Date... values) {
		return fetch(Factura.FACTURA.VENCIMIENTO, values);
	}

	/**
	 * Fetch records that have <code>id_formaPago IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdFormapago(Integer... values) {
		return fetch(Factura.FACTURA.ID_FORMAPAGO, values);
	}

	/**
	 * Fetch records that have <code>dc IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByDc(String... values) {
		return fetch(Factura.FACTURA.DC, values);
	}

	/**
	 * Fetch records that have <code>bonificacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByBonificacion(BigDecimal... values) {
		return fetch(Factura.FACTURA.BONIFICACION, values);
	}

	/**
	 * Fetch records that have <code>neto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByNeto(BigDecimal... values) {
		return fetch(Factura.FACTURA.NETO, values);
	}

	/**
	 * Fetch records that have <code>aliPercIIBB IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByAliperciibb(BigDecimal... values) {
		return fetch(Factura.FACTURA.ALIPERCIIBB, values);
	}

	/**
	 * Fetch records that have <code>percIIBB IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByPerciibb(BigDecimal... values) {
		return fetch(Factura.FACTURA.PERCIIBB, values);
	}

	/**
	 * Fetch records that have <code>aliIva IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByAliiva(BigDecimal... values) {
		return fetch(Factura.FACTURA.ALIIVA, values);
	}

	/**
	 * Fetch records that have <code>iva IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIva(BigDecimal... values) {
		return fetch(Factura.FACTURA.IVA, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByObservaciones(String... values) {
		return fetch(Factura.FACTURA.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>id_vendedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdVendedor(Integer... values) {
		return fetch(Factura.FACTURA.ID_VENDEDOR, values);
	}

	/**
	 * Fetch records that have <code>comparte IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByComparte(String... values) {
		return fetch(Factura.FACTURA.COMPARTE, values);
	}

	/**
	 * Fetch records that have <code>id_vendedorComparte IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdVendedorcomparte(Integer... values) {
		return fetch(Factura.FACTURA.ID_VENDEDORCOMPARTE, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByIdEmpresa(Integer... values) {
		return fetch(Factura.FACTURA.ID_EMPRESA, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByUsuario(String... values) {
		return fetch(Factura.FACTURA.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Factura> fetchByFechareal(Timestamp... values) {
		return fetch(Factura.FACTURA.FECHAREAL, values);
	}
}
