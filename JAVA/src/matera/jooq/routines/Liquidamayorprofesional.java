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
public class Liquidamayorprofesional extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1008953528;

	/**
	 * The parameter <code>matera.liquidaMayorProfesional.id_mayorProfesional</code>.
	 */
	public static final Parameter<Integer> ID_MAYORPROFESIONAL = createParameter("id_mayorProfesional", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.liquidaMayorProfesional.id_liquidacion</code>.
	 */
	public static final Parameter<Integer> ID_LIQUIDACION = createParameter("id_liquidacion", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.liquidaMayorProfesional.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.liquidaMayorProfesional.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * Create a new routine call instance
	 */
	public Liquidamayorprofesional() {
		super("liquidaMayorProfesional", Matera.MATERA);

		addInParameter(ID_MAYORPROFESIONAL);
		addInParameter(ID_LIQUIDACION);
		addInParameter(ID_EMPRESA);
		addInParameter(USUARIO);
	}

	/**
	 * Set the <code>id_mayorProfesional</code> parameter IN value to the routine
	 */
	public void setIdMayorprofesional(Integer value) {
		setValue(ID_MAYORPROFESIONAL, value);
	}

	/**
	 * Set the <code>id_liquidacion</code> parameter IN value to the routine
	 */
	public void setIdLiquidacion(Integer value) {
		setValue(ID_LIQUIDACION, value);
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
