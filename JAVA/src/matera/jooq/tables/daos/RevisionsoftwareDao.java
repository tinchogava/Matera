/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Revisionsoftware;
import matera.jooq.tables.records.RevisionsoftwareRecord;

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
public class RevisionsoftwareDao extends DAOImpl<RevisionsoftwareRecord, matera.jooq.tables.pojos.Revisionsoftware, Integer> {

	/**
	 * Create a new RevisionsoftwareDao without any configuration
	 */
	public RevisionsoftwareDao() {
		super(Revisionsoftware.REVISIONSOFTWARE, matera.jooq.tables.pojos.Revisionsoftware.class);
	}

	/**
	 * Create a new RevisionsoftwareDao with an attached configuration
	 */
	@Autowired
	public RevisionsoftwareDao(Configuration configuration) {
		super(Revisionsoftware.REVISIONSOFTWARE, matera.jooq.tables.pojos.Revisionsoftware.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Revisionsoftware object) {
		return object.getIdRevisionsoftware();
	}

	/**
	 * Fetch records that have <code>id_revisionSoftware IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Revisionsoftware> fetchByIdRevisionsoftware(Integer... values) {
		return fetch(Revisionsoftware.REVISIONSOFTWARE.ID_REVISIONSOFTWARE, values);
	}

	/**
	 * Fetch a unique record that has <code>id_revisionSoftware = value</code>
	 */
	public matera.jooq.tables.pojos.Revisionsoftware fetchOneByIdRevisionsoftware(Integer value) {
		return fetchOne(Revisionsoftware.REVISIONSOFTWARE.ID_REVISIONSOFTWARE, value);
	}

	/**
	 * Fetch records that have <code>detalleRevision IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Revisionsoftware> fetchByDetallerevision(String... values) {
		return fetch(Revisionsoftware.REVISIONSOFTWARE.DETALLEREVISION, values);
	}

	/**
	 * Fetch records that have <code>fechaReal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Revisionsoftware> fetchByFechareal(Timestamp... values) {
		return fetch(Revisionsoftware.REVISIONSOFTWARE.FECHAREAL, values);
	}

	/**
	 * Fetch records that have <code>lanzada IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Revisionsoftware> fetchByLanzada(String... values) {
		return fetch(Revisionsoftware.REVISIONSOFTWARE.LANZADA, values);
	}

	/**
	 * Fetch records that have <code>critica IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Revisionsoftware> fetchByCritica(String... values) {
		return fetch(Revisionsoftware.REVISIONSOFTWARE.CRITICA, values);
	}

	/**
	 * Fetch records that have <code>link IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Revisionsoftware> fetchByLink(String... values) {
		return fetch(Revisionsoftware.REVISIONSOFTWARE.LINK, values);
	}
}
