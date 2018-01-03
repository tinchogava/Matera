/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import java.sql.Date;

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
public class Consultaestrategia extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 1212498328;

	/**
	 * The parameter <code>matera.consultaEstrategia.desde</code>.
	 */
	public static final Parameter<Date> DESDE = createParameter("desde", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.consultaEstrategia.hasta</code>.
	 */
	public static final Parameter<Date> HASTA = createParameter("hasta", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.consultaEstrategia.id_profesional</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL = createParameter("id_profesional", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaEstrategia.id_vendedor</code>.
	 */
	public static final Parameter<Integer> ID_VENDEDOR = createParameter("id_vendedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaEstrategia.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaEstrategia.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaEstrategia.historico</code>.
	 */
	public static final Parameter<String> HISTORICO = createParameter("historico", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * Create a new routine call instance
	 */
	public Consultaestrategia() {
		super("consultaEstrategia", Matera.MATERA);

		addInParameter(DESDE);
		addInParameter(HASTA);
		addInParameter(ID_PROFESIONAL);
		addInParameter(ID_VENDEDOR);
		addInParameter(ID_ZONA);
		addInParameter(ID_EMPRESA);
		addInParameter(HISTORICO);
	}

	/**
	 * Set the <code>desde</code> parameter IN value to the routine
	 */
	public void setDesde(Date value) {
		setValue(DESDE, value);
	}

	/**
	 * Set the <code>hasta</code> parameter IN value to the routine
	 */
	public void setHasta(Date value) {
		setValue(HASTA, value);
	}

	/**
	 * Set the <code>id_profesional</code> parameter IN value to the routine
	 */
	public void setIdProfesional(Integer value) {
		setValue(ID_PROFESIONAL, value);
	}

	/**
	 * Set the <code>id_vendedor</code> parameter IN value to the routine
	 */
	public void setIdVendedor(Integer value) {
		setValue(ID_VENDEDOR, value);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}

	/**
	 * Set the <code>historico</code> parameter IN value to the routine
	 */
	public void setHistorico(String value) {
		setValue(HISTORICO, value);
	}
}
