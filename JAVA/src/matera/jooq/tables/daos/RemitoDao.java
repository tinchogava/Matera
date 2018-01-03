/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Remito;
import matera.jooq.tables.records.RemitoRecord;

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
public class RemitoDao extends DAOImpl<RemitoRecord, matera.jooq.tables.pojos.Remito, Integer> {

	/**
	 * Create a new RemitoDao without any configuration
	 */
	public RemitoDao() {
		super(Remito.REMITO, matera.jooq.tables.pojos.Remito.class);
	}

	/**
	 * Create a new RemitoDao with an attached configuration
	 */
	@Autowired
	public RemitoDao(Configuration configuration) {
		super(Remito.REMITO, matera.jooq.tables.pojos.Remito.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Remito object) {
		return object.getIdRemito();
	}

	/**
	 * Fetch records that have <code>id_remito IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdRemito(Integer... values) {
		return fetch(Remito.REMITO.ID_REMITO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_remito = value</code>
	 */
	public matera.jooq.tables.pojos.Remito fetchOneByIdRemito(Integer value) {
		return fetchOne(Remito.REMITO.ID_REMITO, value);
	}

	/**
	 * Fetch records that have <code>id_presupuesto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdPresupuesto(Integer... values) {
		return fetch(Remito.REMITO.ID_PRESUPUESTO, values);
	}

	/**
	 * Fetch records that have <code>fecha IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByFecha(Date... values) {
		return fetch(Remito.REMITO.FECHA, values);
	}

	/**
	 * Fetch records that have <code>id_tipoComp IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdTipocomp(Integer... values) {
		return fetch(Remito.REMITO.ID_TIPOCOMP, values);
	}

	/**
	 * Fetch records that have <code>numComp IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByNumcomp(String... values) {
		return fetch(Remito.REMITO.NUMCOMP, values);
	}

	/**
	 * Fetch records that have <code>id_entidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdEntidad(Integer... values) {
		return fetch(Remito.REMITO.ID_ENTIDAD, values);
	}

	/**
	 * Fetch records that have <code>id_proveedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdProveedor(Integer... values) {
		return fetch(Remito.REMITO.ID_PROVEEDOR, values);
	}

	/**
	 * Fetch records that have <code>id_destino IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdDestino(Integer... values) {
		return fetch(Remito.REMITO.ID_DESTINO, values);
	}

	/**
	 * Fetch records that have <code>id_zonaDestino IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdZonadestino(Integer... values) {
		return fetch(Remito.REMITO.ID_ZONADESTINO, values);
	}

	/**
	 * Fetch records that have <code>id_zona IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdZona(Integer... values) {
		return fetch(Remito.REMITO.ID_ZONA, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByObservaciones(String... values) {
		return fetch(Remito.REMITO.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>cajas IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByCajas(String... values) {
		return fetch(Remito.REMITO.CAJAS, values);
	}

	/**
	 * Fetch records that have <code>sets IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchBySets(String... values) {
		return fetch(Remito.REMITO.SETS, values);
	}

	/**
	 * Fetch records that have <code>recibido IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByRecibido(String... values) {
		return fetch(Remito.REMITO.RECIBIDO, values);
	}

	/**
	 * Fetch records that have <code>fechaConsumido IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByFechaconsumido(Date... values) {
		return fetch(Remito.REMITO.FECHACONSUMIDO, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByIdEmpresa(Integer... values) {
		return fetch(Remito.REMITO.ID_EMPRESA, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByUsuario(String... values) {
		return fetch(Remito.REMITO.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Remito> fetchByFechareal(Timestamp... values) {
		return fetch(Remito.REMITO.FECHAREAL, values);
	}
}
