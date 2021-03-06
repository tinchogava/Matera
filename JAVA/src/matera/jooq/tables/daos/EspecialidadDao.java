/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Especialidad;
import matera.jooq.tables.records.EspecialidadRecord;

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
public class EspecialidadDao extends DAOImpl<EspecialidadRecord, matera.jooq.tables.pojos.Especialidad, Integer> {

	/**
	 * Create a new EspecialidadDao without any configuration
	 */
	public EspecialidadDao() {
		super(Especialidad.ESPECIALIDAD, matera.jooq.tables.pojos.Especialidad.class);
	}

	/**
	 * Create a new EspecialidadDao with an attached configuration
	 */
	@Autowired
	public EspecialidadDao(Configuration configuration) {
		super(Especialidad.ESPECIALIDAD, matera.jooq.tables.pojos.Especialidad.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Especialidad object) {
		return object.getIdEspecialidad();
	}

	/**
	 * Fetch records that have <code>id_especialidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Especialidad> fetchByIdEspecialidad(Integer... values) {
		return fetch(Especialidad.ESPECIALIDAD.ID_ESPECIALIDAD, values);
	}

	/**
	 * Fetch a unique record that has <code>id_especialidad = value</code>
	 */
	public matera.jooq.tables.pojos.Especialidad fetchOneByIdEspecialidad(Integer value) {
		return fetchOne(Especialidad.ESPECIALIDAD.ID_ESPECIALIDAD, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Especialidad> fetchByNombre(String... values) {
		return fetch(Especialidad.ESPECIALIDAD.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Especialidad> fetchByHabilita(String... values) {
		return fetch(Especialidad.ESPECIALIDAD.HABILITA, values);
	}
}
