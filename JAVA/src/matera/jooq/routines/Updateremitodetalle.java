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
public class Updateremitodetalle extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -2088613865;

	/**
	 * The parameter <code>matera.updateRemitoDetalle.id_remito</code>.
	 */
	public static final Parameter<Integer> ID_REMITO = createParameter("id_remito", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.updateRemitoDetalle.id_producto</code>.
	 */
	public static final Parameter<Integer> ID_PRODUCTO = createParameter("id_producto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.updateRemitoDetalle.lote</code>.
	 */
	public static final Parameter<String> LOTE = createParameter("lote", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.updateRemitoDetalle.serie</code>.
	 */
	public static final Parameter<String> SERIE = createParameter("serie", org.jooq.impl.SQLDataType.VARCHAR.length(255), false);

	/**
	 * The parameter <code>matera.updateRemitoDetalle.pm</code>.
	 */
	public static final Parameter<String> PM = createParameter("pm", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.updateRemitoDetalle.vencimiento</code>.
	 */
	public static final Parameter<Date> VENCIMIENTO = createParameter("vencimiento", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.updateRemitoDetalle.id_stockdetalle</code>.
	 */
	public static final Parameter<Integer> ID_STOCKDETALLE = createParameter("id_stockdetalle", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Updateremitodetalle() {
		super("updateRemitoDetalle", Matera.MATERA);

		addInParameter(ID_REMITO);
		addInParameter(ID_PRODUCTO);
		addInParameter(LOTE);
		addInParameter(SERIE);
		addInParameter(PM);
		addInParameter(VENCIMIENTO);
		addInParameter(ID_STOCKDETALLE);
	}

	/**
	 * Set the <code>id_remito</code> parameter IN value to the routine
	 */
	public void setIdRemito(Integer value) {
		setValue(ID_REMITO, value);
	}

	/**
	 * Set the <code>id_producto</code> parameter IN value to the routine
	 */
	public void setIdProducto(Integer value) {
		setValue(ID_PRODUCTO, value);
	}

	/**
	 * Set the <code>lote</code> parameter IN value to the routine
	 */
	public void setLote(String value) {
		setValue(LOTE, value);
	}

	/**
	 * Set the <code>serie</code> parameter IN value to the routine
	 */
	public void setSerie(String value) {
		setValue(SERIE, value);
	}

	/**
	 * Set the <code>pm</code> parameter IN value to the routine
	 */
	public void setPm(String value) {
		setValue(PM, value);
	}

	/**
	 * Set the <code>vencimiento</code> parameter IN value to the routine
	 */
	public void setVencimiento(Date value) {
		setValue(VENCIMIENTO, value);
	}

	/**
	 * Set the <code>id_stockdetalle</code> parameter IN value to the routine
	 */
	public void setIdStockdetalle(Integer value) {
		setValue(ID_STOCKDETALLE, value);
	}
}