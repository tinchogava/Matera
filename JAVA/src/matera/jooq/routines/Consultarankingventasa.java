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
public class Consultarankingventasa extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 731866534;

	/**
	 * The parameter <code>matera.consultaRankingVentasA.desde</code>.
	 */
	public static final Parameter<Date> DESDE = createParameter("desde", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.hasta</code>.
	 */
	public static final Parameter<Date> HASTA = createParameter("hasta", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.orden</code>.
	 */
	public static final Parameter<Integer> ORDEN = createParameter("orden", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.id_profesional</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL = createParameter("id_profesional", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.id_productoFact</code>.
	 */
	public static final Parameter<Integer> ID_PRODUCTOFACT = createParameter("id_productoFact", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.visualiza</code>.
	 */
	public static final Parameter<Integer> VISUALIZA = createParameter("visualiza", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.aprobado</code>.
	 */
	public static final Parameter<String> APROBADO = createParameter("aprobado", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.facturado</code>.
	 */
	public static final Parameter<Byte> FACTURADO = createParameter("facturado", org.jooq.impl.SQLDataType.TINYINT, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.id_vendedor</code>.
	 */
	public static final Parameter<Integer> ID_VENDEDOR = createParameter("id_vendedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.id_proveedor</code>.
	 */
	public static final Parameter<Integer> ID_PROVEEDOR = createParameter("id_proveedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.consultaRankingVentasA.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * Create a new routine call instance
	 */
	public Consultarankingventasa() {
		super("consultaRankingVentasA", Matera.MATERA);

		addInParameter(DESDE);
		addInParameter(HASTA);
		addInParameter(ORDEN);
		addInParameter(ID_PROFESIONAL);
		addInParameter(ID_PRODUCTOFACT);
		addInParameter(VISUALIZA);
		addInParameter(APROBADO);
		addInParameter(FACTURADO);
		addInParameter(ID_ZONA);
		addInParameter(ID_VENDEDOR);
		addInParameter(ID_PROVEEDOR);
		addInParameter(ID_EMPRESA);
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
	 * Set the <code>orden</code> parameter IN value to the routine
	 */
	public void setOrden(Integer value) {
		setValue(ORDEN, value);
	}

	/**
	 * Set the <code>id_profesional</code> parameter IN value to the routine
	 */
	public void setIdProfesional(Integer value) {
		setValue(ID_PROFESIONAL, value);
	}

	/**
	 * Set the <code>id_productoFact</code> parameter IN value to the routine
	 */
	public void setIdProductofact(Integer value) {
		setValue(ID_PRODUCTOFACT, value);
	}

	/**
	 * Set the <code>visualiza</code> parameter IN value to the routine
	 */
	public void setVisualiza(Integer value) {
		setValue(VISUALIZA, value);
	}

	/**
	 * Set the <code>aprobado</code> parameter IN value to the routine
	 */
	public void setAprobado(String value) {
		setValue(APROBADO, value);
	}

	/**
	 * Set the <code>facturado</code> parameter IN value to the routine
	 */
	public void setFacturado(Byte value) {
		setValue(FACTURADO, value);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
	}

	/**
	 * Set the <code>id_vendedor</code> parameter IN value to the routine
	 */
	public void setIdVendedor(Integer value) {
		setValue(ID_VENDEDOR, value);
	}

	/**
	 * Set the <code>id_proveedor</code> parameter IN value to the routine
	 */
	public void setIdProveedor(Integer value) {
		setValue(ID_PROVEEDOR, value);
	}

	/**
	 * Set the <code>id_empresa</code> parameter IN value to the routine
	 */
	public void setIdEmpresa(Integer value) {
		setValue(ID_EMPRESA, value);
	}
}