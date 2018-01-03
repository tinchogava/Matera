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
public class Insertacceso extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1837107340;

	/**
	 * The parameter <code>matera.insertAcceso.id_usuario</code>.
	 */
	public static final Parameter<Integer> ID_USUARIO = createParameter("id_usuario", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertAcceso.id_menu</code>.
	 */
	public static final Parameter<Integer> ID_MENU = createParameter("id_menu", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertAcceso.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Insertacceso() {
		super("insertAcceso", Matera.MATERA);

		addInParameter(ID_USUARIO);
		addInParameter(ID_MENU);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_usuario</code> parameter IN value to the routine
	 */
	public void setIdUsuario(Integer value) {
		setValue(ID_USUARIO, value);
	}

	/**
	 * Set the <code>id_menu</code> parameter IN value to the routine
	 */
	public void setIdMenu(Integer value) {
		setValue(ID_MENU, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
