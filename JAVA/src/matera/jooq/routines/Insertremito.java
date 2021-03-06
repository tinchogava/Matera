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
public class Insertremito extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = -385833637;

	/**
	 * The parameter <code>matera.insertRemito.id_remito</code>.
	 */
	public static final Parameter<Integer> ID_REMITO = createParameter("id_remito", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.id_presupuesto</code>.
	 */
	public static final Parameter<Integer> ID_PRESUPUESTO = createParameter("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.fecha</code>.
	 */
	public static final Parameter<Date> FECHA = createParameter("fecha", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertRemito.id_tipoComp</code>.
	 */
	public static final Parameter<Integer> ID_TIPOCOMP = createParameter("id_tipoComp", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.numComp</code>.
	 */
	public static final Parameter<String> NUMCOMP = createParameter("numComp", org.jooq.impl.SQLDataType.VARCHAR.length(12), false);

	/**
	 * The parameter <code>matera.insertRemito.id_entidad</code>.
	 */
	public static final Parameter<Integer> ID_ENTIDAD = createParameter("id_entidad", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.id_proveedor</code>.
	 */
	public static final Parameter<Integer> ID_PROVEEDOR = createParameter("id_proveedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.id_destino</code>.
	 */
	public static final Parameter<Integer> ID_DESTINO = createParameter("id_destino", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.id_zonaDestino</code>.
	 */
	public static final Parameter<Integer> ID_ZONADESTINO = createParameter("id_zonaDestino", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.observaciones</code>.
	 */
	public static final Parameter<String> OBSERVACIONES = createParameter("observaciones", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertRemito.cajas</code>.
	 */
	public static final Parameter<String> CAJAS = createParameter("cajas", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertRemito.sets</code>.
	 */
	public static final Parameter<String> SETS = createParameter("sets", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertRemito.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertRemito.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertRemito.equipo</code>.
	 */
	public static final Parameter<String> EQUIPO = createParameter("equipo", org.jooq.impl.SQLDataType.VARCHAR.length(255), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertremito() {
		super("insertRemito", Matera.MATERA);

		addInParameter(ID_REMITO);
		addInParameter(ID_PRESUPUESTO);
		addInParameter(FECHA);
		addInParameter(ID_TIPOCOMP);
		addInParameter(NUMCOMP);
		addInParameter(ID_ENTIDAD);
		addInParameter(ID_PROVEEDOR);
		addInParameter(ID_DESTINO);
		addInParameter(ID_ZONADESTINO);
		addInParameter(ID_ZONA);
		addInParameter(OBSERVACIONES);
		addInParameter(CAJAS);
		addInParameter(SETS);
		addInParameter(ID_EMPRESA);
		addInParameter(USUARIO);
		addInParameter(EQUIPO);
	}

	/**
	 * Set the <code>id_remito</code> parameter IN value to the routine
	 */
	public void setIdRemito(Integer value) {
		setValue(ID_REMITO, value);
	}

	/**
	 * Set the <code>id_presupuesto</code> parameter IN value to the routine
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(ID_PRESUPUESTO, value);
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
	 * Set the <code>id_entidad</code> parameter IN value to the routine
	 */
	public void setIdEntidad(Integer value) {
		setValue(ID_ENTIDAD, value);
	}

	/**
	 * Set the <code>id_proveedor</code> parameter IN value to the routine
	 */
	public void setIdProveedor(Integer value) {
		setValue(ID_PROVEEDOR, value);
	}

	/**
	 * Set the <code>id_destino</code> parameter IN value to the routine
	 */
	public void setIdDestino(Integer value) {
		setValue(ID_DESTINO, value);
	}

	/**
	 * Set the <code>id_zonaDestino</code> parameter IN value to the routine
	 */
	public void setIdZonadestino(Integer value) {
		setValue(ID_ZONADESTINO, value);
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
	 * Set the <code>cajas</code> parameter IN value to the routine
	 */
	public void setCajas(String value) {
		setValue(CAJAS, value);
	}

	/**
	 * Set the <code>sets</code> parameter IN value to the routine
	 */
	public void setSets(String value) {
		setValue(SETS, value);
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
	 * Set the <code>equipo</code> parameter IN value to the routine
	 */
	public void setEquipo(String value) {
		setValue(EQUIPO, value);
	}
}
