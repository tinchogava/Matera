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
public class Insertclasiproducto extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1760655075;

	/**
	 * The parameter <code>matera.insertClasiProducto.id_clasiProducto</code>.
	 */
	public static final Parameter<Integer> ID_CLASIPRODUCTO = createParameter("id_clasiProducto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertClasiProducto.nombre</code>.
	 */
	public static final Parameter<String> NOMBRE = createParameter("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertClasiProducto.habilita</code>.
	 */
	public static final Parameter<String> HABILITA = createParameter("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertclasiproducto() {
		super("insertClasiProducto", Matera.MATERA);

		addInParameter(ID_CLASIPRODUCTO);
		addInParameter(NOMBRE);
		addInParameter(HABILITA);
	}

	/**
	 * Set the <code>id_clasiProducto</code> parameter IN value to the routine
	 */
	public void setIdClasiproducto(Integer value) {
		setValue(ID_CLASIPRODUCTO, value);
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
