/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.sql.Date;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.Profesional;
import matera.jooq.tables.records.ProfesionalRecord;

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
public class ProfesionalDao extends DAOImpl<ProfesionalRecord, matera.jooq.tables.pojos.Profesional, Integer> {

	/**
	 * Create a new ProfesionalDao without any configuration
	 */
	public ProfesionalDao() {
		super(Profesional.PROFESIONAL, matera.jooq.tables.pojos.Profesional.class);
	}

	/**
	 * Create a new ProfesionalDao with an attached configuration
	 */
	@Autowired
	public ProfesionalDao(Configuration configuration) {
		super(Profesional.PROFESIONAL, matera.jooq.tables.pojos.Profesional.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.Profesional object) {
		return object.getIdProfesional();
	}

	/**
	 * Fetch records that have <code>id_profesional IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdProfesional(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_PROFESIONAL, values);
	}

	/**
	 * Fetch a unique record that has <code>id_profesional = value</code>
	 */
	public matera.jooq.tables.pojos.Profesional fetchOneByIdProfesional(Integer value) {
		return fetchOne(Profesional.PROFESIONAL.ID_PROFESIONAL, value);
	}

	/**
	 * Fetch records that have <code>nombre IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByNombre(String... values) {
		return fetch(Profesional.PROFESIONAL.NOMBRE, values);
	}

	/**
	 * Fetch records that have <code>direccion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByDireccion(String... values) {
		return fetch(Profesional.PROFESIONAL.DIRECCION, values);
	}

	/**
	 * Fetch records that have <code>id_provincia IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdProvincia(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_PROVINCIA, values);
	}

	/**
	 * Fetch records that have <code>id_departamento IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdDepartamento(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_DEPARTAMENTO, values);
	}

	/**
	 * Fetch records that have <code>id_localidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdLocalidad(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_LOCALIDAD, values);
	}

	/**
	 * Fetch records that have <code>codPostal IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByCodpostal(String... values) {
		return fetch(Profesional.PROFESIONAL.CODPOSTAL, values);
	}

	/**
	 * Fetch records that have <code>fechaNac IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByFechanac(Date... values) {
		return fetch(Profesional.PROFESIONAL.FECHANAC, values);
	}

	/**
	 * Fetch records that have <code>dni IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByDni(String... values) {
		return fetch(Profesional.PROFESIONAL.DNI, values);
	}

	/**
	 * Fetch records that have <code>matricula IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByMatricula(String... values) {
		return fetch(Profesional.PROFESIONAL.MATRICULA, values);
	}

	/**
	 * Fetch records that have <code>contacto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByContacto(String... values) {
		return fetch(Profesional.PROFESIONAL.CONTACTO, values);
	}

	/**
	 * Fetch records that have <code>perfil IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByPerfil(String... values) {
		return fetch(Profesional.PROFESIONAL.PERFIL, values);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByEmail(String... values) {
		return fetch(Profesional.PROFESIONAL.EMAIL, values);
	}

	/**
	 * Fetch records that have <code>telParticular IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByTelparticular(String... values) {
		return fetch(Profesional.PROFESIONAL.TELPARTICULAR, values);
	}

	/**
	 * Fetch records that have <code>telMovil IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByTelmovil(String... values) {
		return fetch(Profesional.PROFESIONAL.TELMOVIL, values);
	}

	/**
	 * Fetch records that have <code>telOtros IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByTelotros(String... values) {
		return fetch(Profesional.PROFESIONAL.TELOTROS, values);
	}

	/**
	 * Fetch records that have <code>telConsultorio IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByTelconsultorio(String... values) {
		return fetch(Profesional.PROFESIONAL.TELCONSULTORIO, values);
	}

	/**
	 * Fetch records that have <code>secretaria IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchBySecretaria(String... values) {
		return fetch(Profesional.PROFESIONAL.SECRETARIA, values);
	}

	/**
	 * Fetch records that have <code>dirConsultorio IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByDirconsultorio(String... values) {
		return fetch(Profesional.PROFESIONAL.DIRCONSULTORIO, values);
	}

	/**
	 * Fetch records that have <code>id_especialidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdEspecialidad(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_ESPECIALIDAD, values);
	}

	/**
	 * Fetch records that have <code>id_subespecialidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdSubespecialidad(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_SUBESPECIALIDAD, values);
	}

	/**
	 * Fetch records that have <code>id_zona IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdZona(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_ZONA, values);
	}

	/**
	 * Fetch records that have <code>id_vendedor IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdVendedor(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_VENDEDOR, values);
	}

	/**
	 * Fetch records that have <code>id_entidad IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdEntidad(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_ENTIDAD, values);
	}

	/**
	 * Fetch records that have <code>observaciones IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByObservaciones(String... values) {
		return fetch(Profesional.PROFESIONAL.OBSERVACIONES, values);
	}

	/**
	 * Fetch records that have <code>habilita IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByHabilita(String... values) {
		return fetch(Profesional.PROFESIONAL.HABILITA, values);
	}

	/**
	 * Fetch records that have <code>id_empresa IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.Profesional> fetchByIdEmpresa(Integer... values) {
		return fetch(Profesional.PROFESIONAL.ID_EMPRESA, values);
	}
}
