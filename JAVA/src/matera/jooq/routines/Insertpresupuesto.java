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
public class Insertpresupuesto extends AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 942733579;

	/**
	 * The parameter <code>matera.insertPresupuesto.id_presupuesto</code>.
	 */
	public static final Parameter<Integer> ID_PRESUPUESTO = createParameter("id_presupuesto", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.estado</code>.
	 */
	public static final Parameter<String> ESTADO = createParameter("estado", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.fecha</code>.
	 */
	public static final Parameter<Date> FECHA = createParameter("fecha", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_zona</code>.
	 */
	public static final Parameter<Integer> ID_ZONA = createParameter("id_zona", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_entidad</code>.
	 */
	public static final Parameter<Integer> ID_ENTIDAD = createParameter("id_entidad", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_lugarCirugia</code>.
	 */
	public static final Parameter<Integer> ID_LUGARCIRUGIA = createParameter("id_lugarCirugia", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.acompañamiento</code>.
	 */
	public static final Parameter<String> ACOMPAÑAMIENTO = createParameter("acompañamiento", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.fechaApertura</code>.
	 */
	public static final Parameter<Date> FECHAAPERTURA = createParameter("fechaApertura", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.horaApertura</code>.
	 */
	public static final Parameter<String> HORAAPERTURA = createParameter("horaApertura", org.jooq.impl.SQLDataType.VARCHAR.length(4), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.expediente</code>.
	 */
	public static final Parameter<String> EXPEDIENTE = createParameter("expediente", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.contratacion</code>.
	 */
	public static final Parameter<String> CONTRATACION = createParameter("contratacion", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.paciente</code>.
	 */
	public static final Parameter<String> PACIENTE = createParameter("paciente", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.vip</code>.
	 */
	public static final Parameter<String> VIP = createParameter("vip", org.jooq.impl.SQLDataType.VARCHAR.length(1), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.dni</code>.
	 */
	public static final Parameter<String> DNI = createParameter("dni", org.jooq.impl.SQLDataType.VARCHAR.length(15), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.direccion</code>.
	 */
	public static final Parameter<String> DIRECCION = createParameter("direccion", org.jooq.impl.SQLDataType.VARCHAR.length(100), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.telefono</code>.
	 */
	public static final Parameter<String> TELEFONO = createParameter("telefono", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.total</code>.
	 */
	public static final Parameter<BigDecimal> TOTAL = createParameter("total", org.jooq.impl.SQLDataType.DECIMAL.precision(9, 2), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_profesional1</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL1 = createParameter("id_profesional1", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.rm1</code>.
	 */
	public static final Parameter<BigDecimal> RM1 = createParameter("rm1", org.jooq.impl.SQLDataType.DECIMAL.precision(7, 2), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_profesional2</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL2 = createParameter("id_profesional2", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.rm2</code>.
	 */
	public static final Parameter<BigDecimal> RM2 = createParameter("rm2", org.jooq.impl.SQLDataType.DECIMAL.precision(7, 2), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_profesional3</code>.
	 */
	public static final Parameter<Integer> ID_PROFESIONAL3 = createParameter("id_profesional3", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.rm3</code>.
	 */
	public static final Parameter<BigDecimal> RM3 = createParameter("rm3", org.jooq.impl.SQLDataType.DECIMAL.precision(7, 2), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_vendedor</code>.
	 */
	public static final Parameter<Integer> ID_VENDEDOR = createParameter("id_vendedor", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_tipoOperacion</code>.
	 */
	public static final Parameter<Integer> ID_TIPOOPERACION = createParameter("id_tipoOperacion", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_formaPago</code>.
	 */
	public static final Parameter<Integer> ID_FORMAPAGO = createParameter("id_formaPago", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.formaPagoOtro</code>.
	 */
	public static final Parameter<String> FORMAPAGOOTRO = createParameter("formaPagoOtro", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_plazo</code>.
	 */
	public static final Parameter<Integer> ID_PLAZO = createParameter("id_plazo", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.plazoOtro</code>.
	 */
	public static final Parameter<String> PLAZOOTRO = createParameter("plazoOtro", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_mantenimiento</code>.
	 */
	public static final Parameter<Integer> ID_MANTENIMIENTO = createParameter("id_mantenimiento", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.mantenimientoOtro</code>.
	 */
	public static final Parameter<String> MANTENIMIENTOOTRO = createParameter("mantenimientoOtro", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.notaPresu</code>.
	 */
	public static final Parameter<String> NOTAPRESU = createParameter("notaPresu", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.observaciones</code>.
	 */
	public static final Parameter<String> OBSERVACIONES = createParameter("observaciones", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_motivo</code>.
	 */
	public static final Parameter<Integer> ID_MOTIVO = createParameter("id_motivo", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.motivo</code>.
	 */
	public static final Parameter<String> MOTIVO = createParameter("motivo", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.notaExtra</code>.
	 */
	public static final Parameter<String> NOTAEXTRA = createParameter("notaExtra", org.jooq.impl.SQLDataType.CLOB, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.id_empresa</code>.
	 */
	public static final Parameter<Integer> ID_EMPRESA = createParameter("id_empresa", org.jooq.impl.SQLDataType.INTEGER, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.usuario</code>.
	 */
	public static final Parameter<String> USUARIO = createParameter("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.fechaAprobacion</code>.
	 */
	public static final Parameter<Date> FECHAAPROBACION = createParameter("fechaAprobacion", org.jooq.impl.SQLDataType.DATE, false);

	/**
	 * The parameter <code>matera.insertPresupuesto.equipo</code>.
	 */
	public static final Parameter<String> EQUIPO = createParameter("equipo", org.jooq.impl.SQLDataType.VARCHAR.length(255), false);

	/**
	 * The parameter <code>matera.insertPresupuesto.costo_venta</code>.
	 */
	public static final Parameter<BigDecimal> COSTO_VENTA = createParameter("costo_venta", org.jooq.impl.SQLDataType.DECIMAL.precision(9, 2), false);

	/**
	 * Create a new routine call instance
	 */
	public Insertpresupuesto() {
		super("insertPresupuesto", Matera.MATERA);

		addInParameter(ID_PRESUPUESTO);
		addInParameter(ESTADO);
		addInParameter(FECHA);
		addInParameter(ID_ZONA);
		addInParameter(ID_ENTIDAD);
		addInParameter(ID_LUGARCIRUGIA);
		addInParameter(ACOMPAÑAMIENTO);
		addInParameter(FECHAAPERTURA);
		addInParameter(HORAAPERTURA);
		addInParameter(EXPEDIENTE);
		addInParameter(CONTRATACION);
		addInParameter(PACIENTE);
		addInParameter(VIP);
		addInParameter(DNI);
		addInParameter(DIRECCION);
		addInParameter(TELEFONO);
		addInParameter(TOTAL);
		addInParameter(ID_PROFESIONAL1);
		addInParameter(RM1);
		addInParameter(ID_PROFESIONAL2);
		addInParameter(RM2);
		addInParameter(ID_PROFESIONAL3);
		addInParameter(RM3);
		addInParameter(ID_VENDEDOR);
		addInParameter(ID_TIPOOPERACION);
		addInParameter(ID_FORMAPAGO);
		addInParameter(FORMAPAGOOTRO);
		addInParameter(ID_PLAZO);
		addInParameter(PLAZOOTRO);
		addInParameter(ID_MANTENIMIENTO);
		addInParameter(MANTENIMIENTOOTRO);
		addInParameter(NOTAPRESU);
		addInParameter(OBSERVACIONES);
		addInParameter(ID_MOTIVO);
		addInParameter(MOTIVO);
		addInParameter(NOTAEXTRA);
		addInParameter(ID_EMPRESA);
		addInParameter(USUARIO);
		addInParameter(FECHAAPROBACION);
		addInParameter(EQUIPO);
		addInParameter(COSTO_VENTA);
	}

	/**
	 * Set the <code>id_presupuesto</code> parameter IN value to the routine
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(ID_PRESUPUESTO, value);
	}

	/**
	 * Set the <code>estado</code> parameter IN value to the routine
	 */
	public void setEstado(String value) {
		setValue(ESTADO, value);
	}

	/**
	 * Set the <code>fecha</code> parameter IN value to the routine
	 */
	public void setFecha(Date value) {
		setValue(FECHA, value);
	}

	/**
	 * Set the <code>id_zona</code> parameter IN value to the routine
	 */
	public void setIdZona(Integer value) {
		setValue(ID_ZONA, value);
	}

	/**
	 * Set the <code>id_entidad</code> parameter IN value to the routine
	 */
	public void setIdEntidad(Integer value) {
		setValue(ID_ENTIDAD, value);
	}

	/**
	 * Set the <code>id_lugarCirugia</code> parameter IN value to the routine
	 */
	public void setIdLugarcirugia(Integer value) {
		setValue(ID_LUGARCIRUGIA, value);
	}

	/**
	 * Set the <code>acompañamiento</code> parameter IN value to the routine
	 */
	public void setAcompañamiento(String value) {
		setValue(ACOMPAÑAMIENTO, value);
	}

	/**
	 * Set the <code>fechaApertura</code> parameter IN value to the routine
	 */
	public void setFechaapertura(Date value) {
		setValue(FECHAAPERTURA, value);
	}

	/**
	 * Set the <code>horaApertura</code> parameter IN value to the routine
	 */
	public void setHoraapertura(String value) {
		setValue(HORAAPERTURA, value);
	}

	/**
	 * Set the <code>expediente</code> parameter IN value to the routine
	 */
	public void setExpediente(String value) {
		setValue(EXPEDIENTE, value);
	}

	/**
	 * Set the <code>contratacion</code> parameter IN value to the routine
	 */
	public void setContratacion(String value) {
		setValue(CONTRATACION, value);
	}

	/**
	 * Set the <code>paciente</code> parameter IN value to the routine
	 */
	public void setPaciente(String value) {
		setValue(PACIENTE, value);
	}

	/**
	 * Set the <code>vip</code> parameter IN value to the routine
	 */
	public void setVip(String value) {
		setValue(VIP, value);
	}

	/**
	 * Set the <code>dni</code> parameter IN value to the routine
	 */
	public void setDni(String value) {
		setValue(DNI, value);
	}

	/**
	 * Set the <code>direccion</code> parameter IN value to the routine
	 */
	public void setDireccion(String value) {
		setValue(DIRECCION, value);
	}

	/**
	 * Set the <code>telefono</code> parameter IN value to the routine
	 */
	public void setTelefono(String value) {
		setValue(TELEFONO, value);
	}

	/**
	 * Set the <code>total</code> parameter IN value to the routine
	 */
	public void setTotal(BigDecimal value) {
		setValue(TOTAL, value);
	}

	/**
	 * Set the <code>id_profesional1</code> parameter IN value to the routine
	 */
	public void setIdProfesional1(Integer value) {
		setValue(ID_PROFESIONAL1, value);
	}

	/**
	 * Set the <code>rm1</code> parameter IN value to the routine
	 */
	public void setRm1(BigDecimal value) {
		setValue(RM1, value);
	}

	/**
	 * Set the <code>id_profesional2</code> parameter IN value to the routine
	 */
	public void setIdProfesional2(Integer value) {
		setValue(ID_PROFESIONAL2, value);
	}

	/**
	 * Set the <code>rm2</code> parameter IN value to the routine
	 */
	public void setRm2(BigDecimal value) {
		setValue(RM2, value);
	}

	/**
	 * Set the <code>id_profesional3</code> parameter IN value to the routine
	 */
	public void setIdProfesional3(Integer value) {
		setValue(ID_PROFESIONAL3, value);
	}

	/**
	 * Set the <code>rm3</code> parameter IN value to the routine
	 */
	public void setRm3(BigDecimal value) {
		setValue(RM3, value);
	}

	/**
	 * Set the <code>id_vendedor</code> parameter IN value to the routine
	 */
	public void setIdVendedor(Integer value) {
		setValue(ID_VENDEDOR, value);
	}

	/**
	 * Set the <code>id_tipoOperacion</code> parameter IN value to the routine
	 */
	public void setIdTipooperacion(Integer value) {
		setValue(ID_TIPOOPERACION, value);
	}

	/**
	 * Set the <code>id_formaPago</code> parameter IN value to the routine
	 */
	public void setIdFormapago(Integer value) {
		setValue(ID_FORMAPAGO, value);
	}

	/**
	 * Set the <code>formaPagoOtro</code> parameter IN value to the routine
	 */
	public void setFormapagootro(String value) {
		setValue(FORMAPAGOOTRO, value);
	}

	/**
	 * Set the <code>id_plazo</code> parameter IN value to the routine
	 */
	public void setIdPlazo(Integer value) {
		setValue(ID_PLAZO, value);
	}

	/**
	 * Set the <code>plazoOtro</code> parameter IN value to the routine
	 */
	public void setPlazootro(String value) {
		setValue(PLAZOOTRO, value);
	}

	/**
	 * Set the <code>id_mantenimiento</code> parameter IN value to the routine
	 */
	public void setIdMantenimiento(Integer value) {
		setValue(ID_MANTENIMIENTO, value);
	}

	/**
	 * Set the <code>mantenimientoOtro</code> parameter IN value to the routine
	 */
	public void setMantenimientootro(String value) {
		setValue(MANTENIMIENTOOTRO, value);
	}

	/**
	 * Set the <code>notaPresu</code> parameter IN value to the routine
	 */
	public void setNotapresu(String value) {
		setValue(NOTAPRESU, value);
	}

	/**
	 * Set the <code>observaciones</code> parameter IN value to the routine
	 */
	public void setObservaciones(String value) {
		setValue(OBSERVACIONES, value);
	}

	/**
	 * Set the <code>id_motivo</code> parameter IN value to the routine
	 */
	public void setIdMotivo(Integer value) {
		setValue(ID_MOTIVO, value);
	}

	/**
	 * Set the <code>motivo</code> parameter IN value to the routine
	 */
	public void setMotivo(String value) {
		setValue(MOTIVO, value);
	}

	/**
	 * Set the <code>notaExtra</code> parameter IN value to the routine
	 */
	public void setNotaextra(String value) {
		setValue(NOTAEXTRA, value);
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
	 * Set the <code>fechaAprobacion</code> parameter IN value to the routine
	 */
	public void setFechaaprobacion(Date value) {
		setValue(FECHAAPROBACION, value);
	}

	/**
	 * Set the <code>equipo</code> parameter IN value to the routine
	 */
	public void setEquipo(String value) {
		setValue(EQUIPO, value);
	}

	/**
	 * Set the <code>costo_venta</code> parameter IN value to the routine
	 */
	public void setCostoVenta(BigDecimal value) {
		setValue(COSTO_VENTA, value);
	}
}
