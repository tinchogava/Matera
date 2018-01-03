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
public class Consultaentidad extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 2131147208;

	/**
	 * The parameter <code>matera.consultaEntidad.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaEntidad.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaEntidad.soloHabilitados</code>.
	 */
	public static final Parameter<Byte> SOLOHABILITADOS = createParameter("soloHabilitados", org.jooq.impl.SQLDataType.TINYINT, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaentidad() {
		super("consultaEntidad", Matera.MATERA);

		addInParameter(ID_EMPRESA);
		addInParameter(ID_ZONA);
		addInParameter(SOLOHABILITADOS);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
	}

	/**
	 * Set the <code>soloHabilitados</code> parameter IN value to the routine
	 */
	public void setSolohabilitados(Byte value) {
		setValue(SOLOHABILITADOS, value);
	}
}
