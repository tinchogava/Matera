/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Cajaasignada;
import matera.jooq.tables.records.CajaasignadaRecord;

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
public class CajaasignadaDao extends DAOImpl<CajaasignadaRecord, matera.jooq.tables.pojos.Cajaasignada, Integer> {

	/**
	 * Create a new CajaasignadaDao without any configuration
	 */
	public CajaasignadaDao() {
		super(Cajaasignada.CAJAASIGNADA, matera.jooq.tables.pojos.Cajaasignada.class);
	}

	/**
	 * Create a new CajaasignadaDao with an attached configuration
	 */
	@Autowired
	public CajaasignadaDao(Configuration configuration) {
		super(Cajaasignada.CAJAASIGNADA, matera.jooq.tables.pojos.Cajaasignada.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Cajaasignada object) {
		return object.getIdCajaasignada();
	}

	/**
	 * Fetch records that have <code>id_cajaAsignada IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajaasignada> fetchByIdCajaasignada(Integer... values) {
		return fetch(Cajaasignada.CAJAASIGNADA.ID_CAJAASIGNADA, values);
	}

	/**
	 * Fetch a unique record that has <code>id_cajaAsignada = value</code>
	 */
	public matera.jooq.tables.pojos.Cajaasignada fetchOneByIdCajaasignada(Integer value) {
		return fetchOne(Cajaasignada.CAJAASIGNADA.ID_CAJAASIGNADA, value);
	}

	/**
	 * Fetch records that have <code>id_presupuesto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajaasignada> fetchByIdPresupuesto(Integer... values) {
		return fetch(Cajaasignada.CAJAASIGNADA.ID_PRESUPUESTO, values);
	}

	/**
	 * Fetch records that have <code>id_cajaDeposito IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajaasignada> fetchByIdCajadeposito(Integer... values) {
		return fetch(Cajaasignada.CAJAASIGNADA.ID_CAJADEPOSITO, values);
	}
}
