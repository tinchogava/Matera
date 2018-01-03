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
public class Insertstock extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -1027373321;

	/**
	 * The parameter <code>matera.insertStock.id_stock</code>.
	 */
	public static final Parameter<Integer> ID_STOCK = createParameter("id_stock", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertStock.fecha</code>.
	 */
	public static final Parameter<Date> FECHA = createParameter("fecha", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertStock.id_tipoComp</code>.
	 */
	public static final Parameter<Integer> ID_TIPOCOMP = createParameter("id_tipoComp", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertStock.numComp</code>.
	 */
	public static final Parameter<String> NUMCOMP = createParameter("numComp", org.jooq.impl.SQLDataType.VARCHAR.length(12), false);

	/**
	 * The parameter <code>matera.insertStock.id_proveedor</code>.
	 */
	public static final Parameter<Integer> ID_PROVEEDOR = createParameter("id_proveedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertStock.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertStock.observaciones</code>.
	 */
	public static final Parameter<String> OBSERVACIONES = createParameter("observaciones", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertStock.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertStock.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertstock() {
		super("insertStock", Matera.MATERA);

		addInParameter(ID_STOCK);
		addInParameter(FECHA);
		addInParameter(ID_TIPOCOMP);
		addInParameter(NUMCOMP);
		addInParameter(ID_PROVEEDOR);
		addInParameter(ID_ZONA);
		addInParameter(OBSERVACIONES);
		addInParameter(ID_EMPRESA);
		addInParameter(USUARIO);
	}

	/**
	 * Set the <code>id_stock</code> parameter IN value to the routine
	 */
	public void setIdStock(Integer value) {
		setValue(ID_STOCK, value);
	}

	/**
	 * Set the <code>fecha</code> parameter IN value to the routine
	 */
	public void setFecha(Date value) {
		setValue(FECHA, value);
	}

	/**
	 * Set the <code>id_tipoComp</code> parameter IN value to the routine
	 */
	public void setIdTipocomp(Integer value) {
		setValue(ID_TIPOCOMP, value);
	}

	/**
	 * Set the <code>numComp</code> parameter IN value to the routine
	 */
	public void setNumcomp(String value) {
		setValue(NUMCOMP, value);
	}

	/**
	 * Set the <code>id_proveedor</code> parameter IN value to the routine
	 */
	public void setIdProveedor(Integer value) {
		setValue(ID_PROVEEDOR, value);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
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
}
