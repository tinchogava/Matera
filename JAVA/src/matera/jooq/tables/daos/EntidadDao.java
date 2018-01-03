/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Entidad;
import matera.jooq.tables.records.EntidadRecord;

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
public class EntidadDao extends DAOImpl<EntidadRecord, matera.jooq.tables.pojos.Entidad, Integer> {

	/**
	 * Create a new EntidadDao without any configuration
	 */
	public EntidadDao() {
		super(Entidad.ENTIDAD, matera.jooq.tables.pojos.Entidad.class);
	}

	/**
	 * Create a new EntidadDao with an attached configuration
	 */
	@Autowired
	public EntidadDao(Configuration configuration) {
		super(Entidad.ENTIDAD, matera.jooq.tables.pojos.Entidad.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Entidad object) {
		return object.getIdEntidad();
	}

	/**
	 * Fetch records that have <code>id_entidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdEntidad(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_ENTIDAD, values);
	}

	/**
	 * Fetch a unique record that has <code>id_entidad = value</code>
	 */
	public matera.jooq.tables.pojos.Entidad fetchOneByIdEntidad(Integer value) {
		return fetchOne(Entidad.ENTIDAD.ID_ENTIDAD, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByNombre(String... values) {
		return fetch(Entidad.ENTIDAD.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>direccion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByDireccion(String... values) {
		return fetch(Entidad.ENTIDAD.DIRECCION, values);
	}

	/**
	 * Fetch records that have <code>id_provincia IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdProvincia(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_PROVINCIA, values);
	}

	/**
	 * Fetch records that have <code>id_departamento IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdDepartamento(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_DEPARTAMENTO, values);
	}

	/**
	 * Fetch records that have <code>id_localidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdLocalidad(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_LOCALIDAD, values);
	}

	/**
	 * Fetch records that have <code>codPostal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByCodpostal(String... values) {
		return fetch(Entidad.ENTIDAD.CODPOSTAL, values);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByEmail(String... values) {
		return fetch(Entidad.ENTIDAD.EMAIL, values);
	}

	/**
	 * Fetch records that have <code>telefono1 IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByTelefono1(String... values) {
		return fetch(Entidad.ENTIDAD.TELEFONO1, values);
	}

	/**
	 * Fetch records that have <code>telefono2 IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByTelefono2(String... values) {
		return fetch(Entidad.ENTIDAD.TELEFONO2, values);
	}

	/**
	 * Fetch records that have <code>auditor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByAuditor(String... values) {
		return fetch(Entidad.ENTIDAD.AUDITOR, values);
	}

	/**
	 * Fetch records that have <code>secretaria IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchBySecretaria(String... values) {
		return fetch(Entidad.ENTIDAD.SECRETARIA, values);
	}

	/**
	 * Fetch records that have <code>id_zona IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdZona(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_ZONA, values);
	}

	/**
	 * Fetch records that have <code>certImplante IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByCertimplante(String... values) {
		return fetch(Entidad.ENTIDAD.CERTIMPLANTE, values);
	}

	/**
	 * Fetch records that have <code>recomendaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByRecomendaciones(String... values) {
		return fetch(Entidad.ENTIDAD.RECOMENDACIONES, values);
	}

	/**
	 * Fetch records that have <code>id_formaPago IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdFormapago(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_FORMAPAGO, values);
	}

	/**
	 * Fetch records that have <code>id_opcion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdOpcion(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_OPCION, values);
	}

	/**
	 * Fetch records that have <code>cuit IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByCuit(String... values) {
		return fetch(Entidad.ENTIDAD.CUIT, values);
	}

	/**
	 * Fetch records that have <code>id_clasiEntidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdClasientidad(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_CLASIENTIDAD, values);
	}

	/**
	 * Fetch records that have <code>riesgoCredito IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByRiesgocredito(BigDecimal... values) {
		return fetch(Entidad.ENTIDAD.RIESGOCREDITO, values);
	}

	/**
	 * Fetch records that have <code>reqFacturacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByReqfacturacion(String... values) {
		return fetch(Entidad.ENTIDAD.REQFACTURACION, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByObservaciones(String... values) {
		return fetch(Entidad.ENTIDAD.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>gln IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByGln(String... values) {
		return fetch(Entidad.ENTIDAD.GLN, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByHabilita(String... values) {
		return fetch(Entidad.ENTIDAD.HABILITA, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Entidad> fetchByIdEmpresa(Integer... values) {
		return fetch(Entidad.ENTIDAD.ID_EMPRESA, values);
	}
}