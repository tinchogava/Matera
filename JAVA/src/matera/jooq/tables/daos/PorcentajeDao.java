/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Porcentaje;
import matera.jooq.tables.records.PorcentajeRecord;

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
public class PorcentajeDao extends DAOImpl<PorcentajeRecord, matera.jooq.tables.pojos.Porcentaje, Integer> {

	/**
	 * Create a new PorcentajeDao without any configuration
	 */
	public PorcentajeDao() {
		super(Porcentaje.PORCENTAJE, matera.jooq.tables.pojos.Porcentaje.class);
	}

	/**
	 * Create a new PorcentajeDao with an attached configuration
	 */
	@Autowired
	public PorcentajeDao(Configuration configuration) {
		super(Porcentaje.PORCENTAJE, matera.jooq.tables.pojos.Porcentaje.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Porcentaje object) {
		return object.getIdPorcentaje();
	}

	/**
	 * Fetch records that have <code>id_porcentaje IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByIdPorcentaje(Integer... values) {
		return fetch(Porcentaje.PORCENTAJE.ID_PORCENTAJE, values);
	}

	/**
	 * Fetch a unique record that has <code>id_porcentaje = value</code>
	 */
	public matera.jooq.tables.pojos.Porcentaje fetchOneByIdPorcentaje(Integer value) {
		return fetchOne(Porcentaje.PORCENTAJE.ID_PORCENTAJE, value);
	}

	/**
	 * Fetch records that have <code>id_grupoPorcentaje IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByIdGrupoporcentaje(Integer... values) {
		return fetch(Porcentaje.PORCENTAJE.ID_GRUPOPORCENTAJE, values);
	}

	/**
	 * Fetch records that have <code>id_especialidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByIdEspecialidad(Integer... values) {
		return fetch(Porcentaje.PORCENTAJE.ID_ESPECIALIDAD, values);
	}

	/**
	 * Fetch records that have <code>id_zona IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByIdZona(Integer... values) {
		return fetch(Porcentaje.PORCENTAJE.ID_ZONA, values);
	}

	/**
	 * Fetch records that have <code>valor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByValor(BigDecimal... values) {
		return fetch(Porcentaje.PORCENTAJE.VALOR, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByIdEmpresa(Integer... values) {
		return fetch(Porcentaje.PORCENTAJE.ID_EMPRESA, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByUsuario(String... values) {
		return fetch(Porcentaje.PORCENTAJE.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Porcentaje> fetchByFechareal(Timestamp... values) {
		return fetch(Porcentaje.PORCENTAJE.FECHAREAL, values);
	}
}