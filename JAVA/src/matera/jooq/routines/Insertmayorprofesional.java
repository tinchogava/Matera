/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import java.math.BigDecimal;
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
public class Insertmayorprofesional extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 733593435;

	/**
	 * The parameter <code>matera.insertMayorProfesional.fecha</code>.
	 */
	public static final Parameter<Date> FECHA = createParameter("fecha", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.id_presupuesto</code>.
	 */
	public static final Parameter<Integer> ID_PRESUPUESTO = createParameter("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.id_profesional</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL = createParameter("id_profesional", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.dc</code>.
	 */
	public static final Parameter<String> DC = createParameter("dc", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.importe</code>.
	 */
	public static final Parameter<BigDecimal> IMPORTE = createParameter("importe", org.jooq.impl.SQLDataType.DECIMAL.precision(9, 2), false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.observaciones</code>.
	 */
	public static final Parameter<String> OBSERVACIONES = createParameter("observaciones", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertMayorProfesional.transferencia</code>.
	 */
	public static final Parameter<String> TRANSFERENCIA = createParameter("transferencia", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertmayorprofesional() {
		super("insertMayorProfesional", Matera.MATERA);

		addInParameter(FECHA);
		addInParameter(ID_PRESUPUESTO);
		addInParameter(ID_PROFESIONAL);
		addInParameter(DC);
		addInParameter(IMPORTE);
		addInParameter(OBSERVACIONES);
		addInParameter(ID_EMPRESA);
		addInParameter(USUARIO);
		addInParameter(TRANSFERENCIA);
	}

	/**
	 * Set the <code>fecha</code> parameter IN value to the routine
	 */
	public void setFecha(Date value) {
		setValue(FECHA, value);
	}

	/**
	 * Set the <code>id_presupuesto</code> parameter IN value to the routine
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(ID_PRESUPUESTO, value);
	}

	/**
	 * Set the <code>id_profesional</code> parameter IN value to the routine
	 */
	public void setIdProfesional(Integer value) {
		setValue(ID_PROFESIONAL, value);
	}

	/**
	 * Set the <code>dc</code> parameter IN value to the routine
	 */
	public void setDc(String value) {
		setValue(DC, value);
	}

	/**
	 * Set the <code>importe</code> parameter IN value to the routine
	 */
	public void setImporte(BigDecimal value) {
		setValue(IMPORTE, value);
	}

	/**
	 * Set the <code>observaciones</code> parameter IN value to the routine
	 */
	public void setObservaciones(String value) {
		setValue(OBSERVACIONES, value);
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

	/**
	 * Set the <code>transferencia</code> parameter IN value to the routine
	 */
	public void setTransferencia(String value) {
		setValue(TRANSFERENCIA, value);
	}
}
