/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Acceso;
import matera.jooq.tables.records.AccesoRecord;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UInteger;
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
public class AccesoDao extends DAOImpl<AccesoRecord, matera.jooq.tables.pojos.Acceso, UInteger> {

	/**
	 * Create a new AccesoDao without any configuration
	 */
	public AccesoDao() {
		super(Acceso.ACCESO, matera.jooq.tables.pojos.Acceso.class);
	}

	/**
	 * Create a new AccesoDao with an attached configuration
	 */
	@Autowired
	public AccesoDao(Configuration configuration) {
		super(Acceso.ACCESO, matera.jooq.tables.pojos.Acceso.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UInteger getId(matera.jooq.tables.pojos.Acceso object) {
		return object.getIdAcceso();
	}

	/**
	 * Fetch records that have <code>id_acceso IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Acceso> fetchByIdAcceso(UInteger... values) {
		return fetch(Acceso.ACCESO.ID_ACCESO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_acceso = value</code>
	 */
	public matera.jooq.tables.pojos.Acceso fetchOneByIdAcceso(UInteger value) {
		return fetchOne(Acceso.ACCESO.ID_ACCESO, value);
	}

	/**
	 * Fetch records that have <code>id_usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Acceso> fetchByIdUsuario(UInteger... values) {
		return fetch(Acceso.ACCESO.ID_USUARIO, values);
	}

	/**
	 * Fetch records that have <code>id_menu IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Acceso> fetchByIdMenu(UInteger... values) {
		return fetch(Acceso.ACCESO.ID_MENU, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Acceso> fetchByIdEmpresa(Integer... values) {
		return fetch(Acceso.ACCESO.ID_EMPRESA, values);
	}
}