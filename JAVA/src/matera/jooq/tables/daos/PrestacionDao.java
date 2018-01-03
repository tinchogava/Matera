/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Prestacion;
import matera.jooq.tables.records.PrestacionRecord;

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
public class PrestacionDao extends DAOImpl<PrestacionRecord, matera.jooq.tables.pojos.Prestacion, Integer> {

	/**
	 * Create a new PrestacionDao without any configuration
	 */
	public PrestacionDao() {
		super(Prestacion.PRESTACION, matera.jooq.tables.pojos.Prestacion.class);
	}

	/**
	 * Create a new PrestacionDao with an attached configuration
	 */
	@Autowired
	public PrestacionDao(Configuration configuration) {
		super(Prestacion.PRESTACION, matera.jooq.tables.pojos.Prestacion.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Prestacion object) {
		return object.getIdPrestacion();
	}

	/**
	 * Fetch records that have <code>id_prestacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Prestacion> fetchByIdPrestacion(Integer... values) {
		return fetch(Prestacion.PRESTACION.ID_PRESTACION, values);
	}

	/**
	 * Fetch a unique record that has <code>id_prestacion = value</code>
	 */
	public matera.jooq.tables.pojos.Prestacion fetchOneByIdPrestacion(Integer value) {
		return fetchOne(Prestacion.PRESTACION.ID_PRESTACION, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Prestacion> fetchByNombre(String... values) {
		return fetch(Prestacion.PRESTACION.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>importe IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Prestacion> fetchByImporte(BigDecimal... values) {
		return fetch(Prestacion.PRESTACION.IMPORTE, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Prestacion> fetchByHabilita(String... values) {
		return fetch(Prestacion.PRESTACION.HABILITA, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Prestacion> fetchByIdEmpresa(Integer... values) {
		return fetch(Prestacion.PRESTACION.ID_EMPRESA, values);
	}
}
