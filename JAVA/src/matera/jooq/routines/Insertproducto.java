/**
 * This class is generated by jOOQ
 */
package matera.jooq.routines;


import java.math.BigDecimal;

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
public class Insertproducto extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 765469108;

	/**
	 * The parameter <code>matera.insertProducto.id_producto</code>.
	 */
	public static final Parameter<Integer> ID_PRODUCTO = createParameter("id_producto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertProducto.codigo</code>.
	 */
	public static final Parameter<String> CODIGO = createParameter("codigo", org.jooq.impl.SQLDataType.VARCHAR.length(25), false);

	/**
	 * The parameter <code>matera.insertProducto.gtin</code>.
	 */
	public static final Parameter<String> GTIN = createParameter("gtin", org.jooq.impl.SQLDataType.VARCHAR.length(13), false);

	/**
	 * The parameter <code>matera.insertProducto.nombre</code>.
	 */
	public static final Parameter<String> NOMBRE = createParameter("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(255), false);

	/**
	 * The parameter <code>matera.insertProducto.id_codConsumo</code>.
	 */
	public static final Parameter<Integer> ID_CODCONSUMO = createParameter("id_codConsumo", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertProducto.id_moneda</code>.
	 */
	public static final Parameter<Integer> ID_MONEDA = createParameter("id_moneda", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertProducto.costo</code>.
	 */
	public static final Parameter<BigDecimal> COSTO = createParameter("costo", org.jooq.impl.SQLDataType.DECIMAL.precision(12, 3), false);

	/**
	 * The parameter <code>matera.insertProducto.stockMinimo</code>.
	 */
	public static final Parameter<Integer> STOCKMINIMO = createParameter("stockMinimo", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertProducto.id_clasiProducto</code>.
	 */
	public static final Parameter<Integer> ID_CLASIPRODUCTO = createParameter("id_clasiProducto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertProducto.pm</code>.
	 */
	public static final Parameter<String> PM = createParameter("pm", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertProducto.id_proveedor</code>.
	 */
	public static final Parameter<Integer> ID_PROVEEDOR = createParameter("id_proveedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertProducto.habilita</code>.
	 */
	public static final Parameter<String> HABILITA = createParameter("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertProducto.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Insertproducto() {
		super("insertProducto", Matera.MATERA);

		addInParameter(ID_PRODUCTO);
		addInParameter(CODIGO);
		addInParameter(GTIN);
		addInParameter(NOMBRE);
		addInParameter(ID_CODCONSUMO);
		addInParameter(ID_MONEDA);
		addInParameter(COSTO);
		addInParameter(STOCKMINIMO);
		addInParameter(ID_CLASIPRODUCTO);
		addInParameter(PM);
		addInParameter(ID_PROVEEDOR);
		addInParameter(HABILITA);
		addInParameter(ID_EMPRESA);
	}

	/**
	 * Set the <code>id_producto</code> parameter IN value to the routine
	 */
	public void setIdProducto(Integer value) {
		setValue(ID_PRODUCTO, value);
	}

	/**
	 * Set the <code>codigo</code> parameter IN value to the routine
	 */
	public void setCodigo(String value) {
		setValue(CODIGO, value);
	}

	/**
	 * Set the <code>gtin</code> parameter IN value to the routine
	 */
	public void setGtin(String value) {
		setValue(GTIN, value);
	}

	/**
	 * Set the <code>nombre</code> parameter IN value to the routine
	 */
	public void setNombre(String value) {
		setValue(NOMBRE, value);
	}

	/**
	 * Set the <code>id_codConsumo</code> parameter IN value to the routine
	 */
	public void setIdCodconsumo(Integer value) {
		setValue(ID_CODCONSUMO, value);
	}

	/**
	 * Set the <code>id_moneda</code> parameter IN value to the routine
	 */
	public void setIdMoneda(Integer value) {
		setValue(ID_MONEDA, value);
	}

	/**
	 * Set the <code>costo</code> parameter IN value to the routine
	 */
	public void setCosto(BigDecimal value) {
		setValue(COSTO, value);
	}

	/**
	 * Set the <code>stockMinimo</code> parameter IN value to the routine
	 */
	public void setStockminimo(Integer value) {
		setValue(STOCKMINIMO, value);
	}

	/**
	 * Set the <code>id_clasiProducto</code> parameter IN value to the routine
	 */
	public void setIdClasiproducto(Integer value) {
		setValue(ID_CLASIPRODUCTO, value);
	}

	/**
	 * Set the <code>pm</code> parameter IN value to the routine
	 */
	public void setPm(String value) {
		setValue(PM, value);
	}

	/**
	 * Set the <code>id_proveedor</code> parameter IN value to the routine
	 */
	public void setIdProveedor(Integer value) {
		setValue(ID_PROVEEDOR, value);
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