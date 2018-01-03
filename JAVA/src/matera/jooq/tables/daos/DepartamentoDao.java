/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Departamento;
import matera.jooq.tables.records.DepartamentoRecord;

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
public class DepartamentoDao extends DAOImpl<DepartamentoRecord, matera.jooq.tables.pojos.Departamento, Integer> {

	/**
	 * Create a new DepartamentoDao without any configuration
	 */
	public DepartamentoDao() {
		super(Departamento.DEPARTAMENTO, matera.jooq.tables.pojos.Departamento.class);
	}

	/**
	 * Create a new DepartamentoDao with an attached configuration
	 */
	@Autowired
	public DepartamentoDao(Configuration configuration) {
		super(Departamento.DEPARTAMENTO, matera.jooq.tables.pojos.Departamento.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Departamento object) {
		return object.getIdDepartamento();
	}

	/**
	 * Fetch records that have <code>id_departamento IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Departamento> fetchByIdDepartamento(Integer... values) {
		return fetch(Departamento.DEPARTAMENTO.ID_DEPARTAMENTO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_departamento = value</code>
	 */
	public matera.jooq.tables.pojos.Departamento fetchOneByIdDepartamento(Integer value) {
		return fetchOne(Departamento.DEPARTAMENTO.ID_DEPARTAMENTO, value);
	}

	/**
	 * Fetch records that have <code>id_provincia IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Departamento> fetchByIdProvincia(Integer... values) {
		return fetch(Departamento.DEPARTAMENTO.ID_PROVINCIA, values);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Departamento> fetchByNombre(String... values) {
		return fetch(Departamento.DEPARTAMENTO.NOMBRE, values);
	}
}