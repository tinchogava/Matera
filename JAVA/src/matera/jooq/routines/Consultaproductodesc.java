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
public class Consultaproductodesc extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1102489020;

	/**
	 * The parameter <code>matera.consultaProductoDesc.id_clasiProducto</code>.
	 */
	public static final Parameter<Integer> ID_CLASIPRODUCTO = createParameter("id_clasiProducto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaProductoDesc.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaProductoDesc.soloHabilitados</code>.
	 */
	public static final Parameter<Byte> SOLOHABILITADOS = createParameter("soloHabilitados", org.jooq.impl.SQLDataType.TINYINT, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaproductodesc() {
		super("consultaProductoDesc", Matera.MATERA);

		addInParameter(ID_CLASIPRODUCTO);
		addInParameter(ID_EMPRESA);
		addInParameter(SOLOHABILITADOS);
	}

	/**
	 * Set the <code>id_clasiProducto</code> parameter IN value to the routine
	 */
	public void setIdClasiproducto(Integer value) {
		setValue(ID_CLASIPRODUCTO, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}

	/**
	 * Set the <code>soloHabilitados</code> parameter IN value to the routine
	 */
	public void setSolohabilitados(Byte value) {
		setValue(SOLOHABILITADOS, value);
	}
}
