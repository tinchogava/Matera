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
public class Insertnotapresu extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 677382292;

	/**
	 * The parameter <code>matera.insertNotaPresu.nota</code>.
	 */
	public static final Parameter<String> NOTA = createParameter("nota", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertNotaPresu.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertNotaPresu.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertnotapresu() {
		super("insertNotaPresu", Matera.MATERA);

		addInParameter(NOTA);
		addInParameter(ID_EMPRESA);
		addInParameter(USUARIO);
	}

	/**
	 * Set the <code>nota</code> parameter IN value to the routine
	 */
	public void setNota(String value) {
		setValue(NOTA, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}

	/**
	 * Set the <code>usuario</code> parameter IN value to the routine
	 */
	public void setUsuario(String value) {
		setValue(USUARIO, value);
	}
}