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
public class Insertliquidaciondetalle extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1247436660;

	/**
	 * The parameter <code>matera.insertLiquidacionDetalle.id_liquidacion</code>.
	 */
	public static final Parameter<Integer> ID_LIQUIDACION = createParameter("id_liquidacion", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertLiquidacionDetalle.banco</code>.
	 */
	public static final Parameter<String> BANCO = createParameter("banco", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertLiquidacionDetalle.cheque</code>.
	 */
	public static final Parameter<String> CHEQUE = createParameter("cheque", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertLiquidacionDetalle.vencimiento</code>.
	 */
	public static final Parameter<Date> VENCIMIENTO = createParameter("vencimiento", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertLiquidacionDetalle.importe</code>.
	 */
	public static final Parameter<BigDecimal> IMPORTE = createParameter("importe", org.jooq.impl.SQLDataType.DECIMAL.precision(7, 2), false);

	/**
	 * The parameter <code>matera.insertLiquidacionDetalle.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertLiquidacionDetalle.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertliquidaciondetalle() {
		super("insertLiquidacionDetalle", Matera.MATERA);

		addInParameter(ID_LIQUIDACION);
		addInParameter(BANCO);
		addInParameter(CHEQUE);
		addInParameter(VENCIMIENTO);
		addInParameter(IMPORTE);
		addInParameter(ID_EMPRESA);
		addInParameter(USUARIO);
	}

	/**
	 * Set the <code>id_liquidacion</code> parameter IN value to the routine
	 */
	public void setIdLiquidacion(Integer value) {
		setValue(ID_LIQUIDACION, value);
	}

	/**
	 * Set the <code>banco</code> parameter IN value to the routine
	 */
	public void setBanco(String value) {
		setValue(BANCO, value);
	}

	/**
	 * Set the <code>cheque</code> parameter IN value to the routine
	 */
	public void setCheque(String value) {
		setValue(CHEQUE, value);
	}

	/**
	 * Set the <code>vencimiento</code> parameter IN value to the routine
	 */
	public void setVencimiento(Date value) {
		setValue(VENCIMIENTO, value);
	}

	/**
	 * Set the <code>importe</code> parameter IN value to the routine
	 */
	public void setImporte(BigDecimal value) {
		setValue(IMPORTE, value);
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
