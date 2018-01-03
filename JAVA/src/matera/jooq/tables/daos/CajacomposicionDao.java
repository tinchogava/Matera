/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Cajacomposicion;
import matera.jooq.tables.records.CajacomposicionRecord;

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
public class CajacomposicionDao extends DAOImpl<CajacomposicionRecord, matera.jooq.tables.pojos.Cajacomposicion, Integer> {

	/**
	 * Create a new CajacomposicionDao without any configuration
	 */
	public CajacomposicionDao() {
		super(Cajacomposicion.CAJACOMPOSICION, matera.jooq.tables.pojos.Cajacomposicion.class);
	}

	/**
	 * Create a new CajacomposicionDao with an attached configuration
	 */
	@Autowired
	public CajacomposicionDao(Configuration configuration) {
		super(Cajacomposicion.CAJACOMPOSICION, matera.jooq.tables.pojos.Cajacomposicion.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Cajacomposicion object) {
		return object.getIdCajacomposicion();
	}

	/**
	 * Fetch records that have <code>id_cajaComposicion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajacomposicion> fetchByIdCajacomposicion(Integer... values) {
		return fetch(Cajacomposicion.CAJACOMPOSICION.ID_CAJACOMPOSICION, values);
	}

	/**
	 * Fetch a unique record that has <code>id_cajaComposicion = value</code>
	 */
	public matera.jooq.tables.pojos.Cajacomposicion fetchOneByIdCajacomposicion(Integer value) {
		return fetchOne(Cajacomposicion.CAJACOMPOSICION.ID_CAJACOMPOSICION, value);
	}

	/**
	 * Fetch records that have <code>id_cajaDeposito IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajacomposicion> fetchByIdCajadeposito(Integer... values) {
		return fetch(Cajacomposicion.CAJACOMPOSICION.ID_CAJADEPOSITO, values);
	}

	/**
	 * Fetch records that have <code>id_producto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajacomposicion> fetchByIdProducto(Integer... values) {
		return fetch(Cajacomposicion.CAJACOMPOSICION.ID_PRODUCTO, values);
	}

	/**
	 * Fetch records that have <code>cantidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajacomposicion> fetchByCantidad(Integer... values) {
		return fetch(Cajacomposicion.CAJACOMPOSICION.CANTIDAD, values);
	}

	/**
	 * Fetch records that have <code>usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajacomposicion> fetchByUsuario(String... values) {
		return fetch(Cajacomposicion.CAJACOMPOSICION.USUARIO, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Cajacomposicion> fetchByFechareal(Timestamp... values) {
		return fetch(Cajacomposicion.CAJACOMPOSICION.FECHAREAL, values);
	}
}