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
public class Traeproductoid extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -2143309822;

	/**
	 * The parameter <code>matera.traeProductoId.id_producto</code>.
	 */
	public static final Parameter<Integer> ID_PRODUCTO = createParameter("id_producto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.traeProductoId.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Traeproductoid() {
		super("traeProductoId", Matera.MATERA);

		addInParameter(ID_PRODUCTO);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_producto</code> parameter IN value to the routine
	 */
	public void setIdProducto(Integer value) {
		setValue(ID_PRODUCTO, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
