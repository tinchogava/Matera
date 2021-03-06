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
public class Liquidamayortecnico extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1720155858;

	/**
	 * The parameter <code>matera.liquidaMayorTecnico.id_mayorTecnico</code>.
	 */
	public static final Parameter<Integer> ID_MAYORTECNICO = createParameter("id_mayorTecnico", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.liquidaMayorTecnico.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.liquidaMayorTecnico.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Liquidamayortecnico() {
		super("liquidaMayorTecnico", Matera.MATERA);

		addInParameter(ID_MAYORTECNICO);
		addInParameter(USUARIO);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_mayorTecnico</code> parameter IN value to the routine
	 */
	public void setIdMayortecnico(Integer value) {
		setValue(ID_MAYORTECNICO, value);
	}

	/**
	 * Set the <code>usuario</code> parameter IN value to the routine
	 */
	public void setUsuario(String value) {
		setValue(USUARIO, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}
