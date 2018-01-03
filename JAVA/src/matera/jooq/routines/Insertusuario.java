/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import java.sql.Date;

import javax.annotation.Generated;

import matera.jooq.Matera;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


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
public class Insertusuario extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1872043139;

	/**
	 * The parameter <code>matera.insertUsuario.id_usuario</code>.
	 */
	public static final Parameter<Integer> ID_USUARIO = createParameter("id_usuario", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertUsuario.nombre</code>.
	 */
	public static final Parameter<String> NOMBRE = createParameter("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertUsuario.documento</code>.
	 */
	public static final Parameter<String> DOCUMENTO = createParameter("documento", org.jooq.impl.SQLDataType.VARCHAR.length(10), false);

	/**
	 * The parameter <code>matera.insertUsuario.cuil</code>.
	 */
	public static final Parameter<String> CUIL = createParameter("cuil", org.jooq.impl.SQLDataType.VARCHAR.length(11), false);

	/**
	 * The parameter <code>matera.insertUsuario.fechaNac</code>.
	 */
	public static final Parameter<Date> FECHANAC = createParameter("fechaNac", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertUsuario.direccion</code>.
	 */
	public static final Parameter<String> DIRECCION = createParameter("direccion", org.jooq.impl.SQLDataType.VARCHAR.length(100), false);

	/**
	 * The parameter <code>matera.insertUsuario.codPostal</code>.
	 */
	public static final Parameter<String> CODPOSTAL = createParameter("codPostal", org.jooq.impl.SQLDataType.VARCHAR.length(10), false);

	/**
	 * The parameter <code>matera.insertUsuario.id_provincia</code>.
	 */
	public static final Parameter<Integer> ID_PROVINCIA = createParameter("id_provincia", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertUsuario.id_departamento</code>.
	 */
	public static final Parameter<Integer> ID_DEPARTAMENTO = createParameter("id_departamento", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertUsuario.id_localidad</code>.
	 */
	public static final Parameter<Integer> ID_LOCALIDAD = createParameter("id_localidad", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertUsuario.fechaIngreso</code>.
	 */
	public static final Parameter<Date> FECHAINGRESO = createParameter("fechaIngreso", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertUsuario.fechaEgreso</code>.
	 */
	public static final Parameter<Date> FECHAEGRESO = createParameter("fechaEgreso", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertUsuario.usaSistema</code>.
	 */
	public static final Parameter<String> USASISTEMA = createParameter("usaSistema", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertUsuario.contraseña</code>.
	 */
	public static final Parameter<String> CONTRASEÑA = createParameter("contraseña", org.jooq.impl.SQLDataType.VARCHAR.length(15), false);

	/**
	 * The parameter <code>matera.insertUsuario.email</code>.
	 */
	public static final Parameter<String> EMAIL = createParameter("email", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertUsuario.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertUsuario.habilita</code>.
	 */
	public static final Parameter<String> HABILITA = createParameter("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertUsuario.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Insertusuario() {
		super("insertUsuario", Matera.MATERA);

		addInParameter(ID_USUARIO);
		addInParameter(NOMBRE);
		addInParameter(DOCUMENTO);
		addInParameter(CUIL);
		addInParameter(FECHANAC);
		addInParameter(DIRECCION);
		addInParameter(CODPOSTAL);
		addInParameter(ID_PROVINCIA);
		addInParameter(ID_DEPARTAMENTO);
		addInParameter(ID_LOCALIDAD);
		addInParameter(FECHAINGRESO);
		addInParameter(FECHAEGRESO);
		addInParameter(USASISTEMA);
		addInParameter(CONTRASEÑA);
		addInParameter(EMAIL);
		addInParameter(ID_ZONA);
		addInParameter(HABILITA);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_usuario</code> parameter IN value to the routine
	 */
	public void setIdUsuario(Integer value) {
		setValue(ID_USUARIO, value);
	}

	/**
	 * Set the <code>nombre</code> parameter IN value to the routine
	 */
	public void setNombre(String value) {
		setValue(NOMBRE, value);
	}

	/**
	 * Set the <code>documento</code> parameter IN value to the routine
	 */
	public void setDocumento(String value) {
		setValue(DOCUMENTO, value);
	}

	/**
	 * Set the <code>cuil</code> parameter IN value to the routine
	 */
	public void setCuil(String value) {
		setValue(CUIL, value);
	}

	/**
	 * Set the <code>fechaNac</code> parameter IN value to the routine
	 */
	public void setFechanac(Date value) {
		setValue(FECHANAC, value);
	}

	/**
	 * Set the <code>direccion</code> parameter IN value to the routine
	 */
	public void setDireccion(String value) {
		setValue(DIRECCION, value);
	}

	/**
	 * Set the <code>codPostal</code> parameter IN value to the routine
	 */
	public void setCodpostal(String value) {
		setValue(CODPOSTAL, value);
	}

	/**
	 * Set the <code>id_provincia</code> parameter IN value to the routine
	 */
	public void setIdProvincia(Integer value) {
		setValue(ID_PROVINCIA, value);
	}

	/**
	 * Set the <code>id_departamento</code> parameter IN value to the routine
	 */
	public void setIdDepartamento(Integer value) {
		setValue(ID_DEPARTAMENTO, value);
	}

	/**
	 * Set the <code>id_localidad</code> parameter IN value to the routine
	 */
	public void setIdLocalidad(Integer value) {
		setValue(ID_LOCALIDAD, value);
	}

	/**
	 * Set the <code>fechaIngreso</code> parameter IN value to the routine
	 */
	public void setFechaingreso(Date value) {
		setValue(FECHAINGRESO, value);
	}

	/**
	 * Set the <code>fechaEgreso</code> parameter IN value to the routine
	 */
	public void setFechaegreso(Date value) {
		setValue(FECHAEGRESO, value);
	}

	/**
	 * Set the <code>usaSistema</code> parameter IN value to the routine
	 */
	public void setUsasistema(String value) {
		setValue(USASISTEMA, value);
	}

	/**
	 * Set the <code>contraseña</code> parameter IN value to the routine
	 */
	public void setContraseña(String value) {
		setValue(CONTRASEÑA, value);
	}

	/**
	 * Set the <code>email</code> parameter IN value to the routine
	 */
	public void setEmail(String value) {
		setValue(EMAIL, value);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
	}

	/**
	 * Set the <code>habilita</code> parameter IN value to the routine
	 */
	public void setHabilita(String value) {
		setValue(HABILITA, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}