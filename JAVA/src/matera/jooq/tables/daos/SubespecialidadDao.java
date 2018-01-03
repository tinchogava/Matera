/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Subespecialidad;
import matera.jooq.tables.records.SubespecialidadRecord;

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
public class SubespecialidadDao extends DAOImpl<SubespecialidadRecord, matera.jooq.tables.pojos.Subespecialidad, Integer> {

	/**
	 * Create a new SubespecialidadDao without any configuration
	 */
	public SubespecialidadDao() {
		super(Subespecialidad.SUBESPECIALIDAD, matera.jooq.tables.pojos.Subespecialidad.class);
	}

	/**
	 * Create a new SubespecialidadDao with an attached configuration
	 */
	@Autowired
	public SubespecialidadDao(Configuration configuration) {
		super(Subespecialidad.SUBESPECIALIDAD, matera.jooq.tables.pojos.Subespecialidad.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Subespecialidad object) {
		return object.getIdSubespecialidad();
	}

	/**
	 * Fetch records that have <code>id_subespecialidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Subespecialidad> fetchByIdSubespecialidad(Integer... values) {
		return fetch(Subespecialidad.SUBESPECIALIDAD.ID_SUBESPECIALIDAD, values);
	}

	/**
	 * Fetch a unique record that has <code>id_subespecialidad = value</code>
	 */
	public matera.jooq.tables.pojos.Subespecialidad fetchOneByIdSubespecialidad(Integer value) {
		return fetchOne(Subespecialidad.SUBESPECIALIDAD.ID_SUBESPECIALIDAD, value);
	}

	/**
	 * Fetch records that have <code>id_especialidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Subespecialidad> fetchByIdEspecialidad(Integer... values) {
		return fetch(Subespecialidad.SUBESPECIALIDAD.ID_ESPECIALIDAD, values);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Subespecialidad> fetchByNombre(String... values) {
		return fetch(Subespecialidad.SUBESPECIALIDAD.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Subespecialidad> fetchByHabilita(String... values) {
		return fetch(Subespecialidad.SUBESPECIALIDAD.HABILITA, values);
	}
}