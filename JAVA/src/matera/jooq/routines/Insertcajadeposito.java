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
public class Insertcajadeposito extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1272670830;

	/**
	 * The parameter <code>matera.insertCajaDeposito.id_cajaDeposito</code>.
	 */
	public static final Parameter<Integer> ID_CAJADEPOSITO = createParameter("id_cajaDeposito", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertCajaDeposito.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertCajaDeposito.id_caja</code>.
	 */
	public static final Parameter<Integer> ID_CAJA = createParameter("id_caja", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertCajaDeposito.codigo</code>.
	 */
	public static final Parameter<String> CODIGO = createParameter("codigo", org.jooq.impl.SQLDataType.VARCHAR.length(25), false);

	/**
	 * The parameter <code>matera.insertCajaDeposito.habilita</code>.
	 */
	public static final Parameter<String> HABILITA = createParameter("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertCajaDeposito.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Insertcajadeposito() {
		super("insertCajaDeposito", Matera.MATERA);

		addInParameter(ID_CAJADEPOSITO);
		addInParameter(ID_ZONA);
		addInParameter(ID_CAJA);
		addInParameter(CODIGO);
		addInParameter(HABILITA);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_cajaDeposito</code> parameter IN value to the routine
	 */
	public void setIdCajadeposito(Integer value) {
		setValue(ID_CAJADEPOSITO, value);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
	}

	/**
	 * Set the <code>id_caja</code> parameter IN value to the routine
	 */
	public void setIdCaja(Integer value) {
		setValue(ID_CAJA, value);
	}

	/**
	 * Set the <code>codigo</code> parameter IN value to the routine
	 */
	public void setCodigo(String value) {
		setValue(CODIGO, value);
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