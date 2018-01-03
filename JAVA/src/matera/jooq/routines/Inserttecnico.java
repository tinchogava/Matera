/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


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
public class Inserttecnico extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1335250137;

	/**
	 * The parameter <code>matera.insertTecnico.id_tecnico</code>.
	 */
	public static final Parameter<Integer> ID_TECNICO = createParameter("id_tecnico", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertTecnico.nombre</code>.
	 */
	public static final Parameter<String> NOMBRE = createParameter("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertTecnico.habilita</code>.
	 */
	public static final Parameter<String> HABILITA = createParameter("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertTecnico.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Inserttecnico() {
		super("insertTecnico", Matera.MATERA);

		addInParameter(ID_TECNICO);
		addInParameter(NOMBRE);
		addInParameter(HABILITA);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_tecnico</code> parameter IN value to the routine
	 */
	public void setIdTecnico(Integer value) {
		setValue(ID_TECNICO, value);
	}

	/**
	 * Set the <code>nombre</code> parameter IN value to the routine
	 */
	public void setNombre(String value) {
		setValue(NOMBRE, value);
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