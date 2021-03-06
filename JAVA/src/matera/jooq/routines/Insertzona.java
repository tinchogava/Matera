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
public class Insertzona extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1573002521;

	/**
	 * The parameter <code>matera.insertZona.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertZona.nombre</code>.
	 */
	public static final Parameter<String> NOMBRE = createParameter("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertZona.habilita</code>.
	 */
	public static final Parameter<String> HABILITA = createParameter("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertzona() {
		super("insertZona", Matera.MATERA);

		addInParameter(ID_ZONA);
		addInParameter(NOMBRE);
		addInParameter(HABILITA);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
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
}
