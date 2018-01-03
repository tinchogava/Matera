/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Motivo;
import matera.jooq.tables.records.MotivoRecord;

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
public class MotivoDao extends DAOImpl<MotivoRecord, matera.jooq.tables.pojos.Motivo, Integer> {

	/**
	 * Create a new MotivoDao without any configuration
	 */
	public MotivoDao() {
		super(Motivo.MOTIVO, matera.jooq.tables.pojos.Motivo.class);
	}

	/**
	 * Create a new MotivoDao with an attached configuration
	 */
	@Autowired
	public MotivoDao(Configuration configuration) {
		super(Motivo.MOTIVO, matera.jooq.tables.pojos.Motivo.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Motivo object) {
		return object.getIdMotivo();
	}

	/**
	 * Fetch records that have <code>id_motivo IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Motivo> fetchByIdMotivo(Integer... values) {
		return fetch(Motivo.MOTIVO.ID_MOTIVO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_motivo = value</code>
	 */
	public matera.jooq.tables.pojos.Motivo fetchOneByIdMotivo(Integer value) {
		return fetchOne(Motivo.MOTIVO.ID_MOTIVO, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Motivo> fetchByNombre(String... values) {
		return fetch(Motivo.MOTIVO.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Motivo> fetchByHabilita(String... values) {
		return fetch(Motivo.MOTIVO.HABILITA, values);
	}
}
