/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Presupuesto;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


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
@Entity
@Table(name = "presupuesto", schema = "matera")
public class PresupuestoRecord extends UpdatableRecordImpl<PresupuestoRecord> {

	private static final long serialVersionUID = -570472123;

	/**
	 * Setter for <code>matera.presupuesto.id_presupuesto</code>.
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_presupuesto</code>.
	 */
	@Id
	@Column(name = "id_presupuesto", unique = true, nullable = false, precision = 10)
	public Integer getIdPresupuesto() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.presupuesto.estado</code>. A: Aprobado; R: Rechazado; N: Anulado; C: Confirmado (Mapa); ' ': Default
	 */
	public void setEstado(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.estado</code>. A: Aprobado; R: Rechazado; N: Anulado; C: Confirmado (Mapa); ' ': Default
	 */
	@Column(name = "estado", length = 1)
	public String getEstado() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.presupuesto.fecha</code>.
	 */
	public void setFecha(Date value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.fecha</code>.
	 */
	@Column(name = "fecha")
	public Date getFecha() {
		return (Date) getValue(2);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_zona</code>.
	 */
	public void setIdZona(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_zona</code>.
	 */
	@Column(name = "id_zona", precision = 10)
	public Integer getIdZona() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_entidad</code>.
	 */
	public void setIdEntidad(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_entidad</code>.
	 */
	@Column(name = "id_entidad", precision = 10)
	public Integer getIdEntidad() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_lugarCirugia</code>. id_entidad
	 */
	public void setIdLugarcirugia(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_lugarCirugia</code>. id_entidad
	 */
	@Column(name = "id_lugarCirugia", precision = 10)
	public Integer getIdLugarcirugia() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>matera.presupuesto.acompañamiento</code>.
	 */
	public void setAcompañamiento(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.acompañamiento</code>.
	 */
	@Column(name = "acompañamiento", length = 1)
	public String getAcompañamiento() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>matera.presupuesto.fechaApertura</code>.
	 */
	public void setFechaapertura(Date value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.fechaApertura</code>.
	 */
	@Column(name = "fechaApertura")
	public Date getFechaapertura() {
		return (Date) getValue(7);
	}

	/**
	 * Setter for <code>matera.presupuesto.horaApertura</code>.
	 */
	public void setHoraapertura(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.horaApertura</code>.
	 */
	@Column(name = "horaApertura", length = 4)
	public String getHoraapertura() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>matera.presupuesto.expediente</code>.
	 */
	public void setExpediente(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.expediente</code>.
	 */
	@Column(name = "expediente", length = 45)
	public String getExpediente() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>matera.presupuesto.contratacion</code>.
	 */
	public void setContratacion(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.contratacion</code>.
	 */
	@Column(name = "contratacion", length = 45)
	public String getContratacion() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>matera.presupuesto.paciente</code>.
	 */
	public void setPaciente(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.paciente</code>.
	 */
	@Column(name = "paciente", length = 45)
	public String getPaciente() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>matera.presupuesto.vip</code>.
	 */
	public void setVip(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.vip</code>.
	 */
	@Column(name = "vip", length = 1)
	public String getVip() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>matera.presupuesto.dni</code>.
	 */
	public void setDni(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.dni</code>.
	 */
	@Column(name = "dni", length = 15)
	public String getDni() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>matera.presupuesto.direccion</code>.
	 */
	public void setDireccion(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.direccion</code>.
	 */
	@Column(name = "direccion", length = 100)
	public String getDireccion() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>matera.presupuesto.telefono</code>.
	 */
	public void setTelefono(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.telefono</code>.
	 */
	@Column(name = "telefono", length = 45)
	public String getTelefono() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>matera.presupuesto.total</code>.
	 */
	public void setTotal(BigDecimal value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.total</code>.
	 */
	@Column(name = "total", precision = 9, scale = 2)
	public BigDecimal getTotal() {
		return (BigDecimal) getValue(16);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_profesional1</code>. id_profesional
	 */
	public void setIdProfesional1(Integer value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_profesional1</code>. id_profesional
	 */
	@Column(name = "id_profesional1", precision = 10)
	public Integer getIdProfesional1() {
		return (Integer) getValue(17);
	}

	/**
	 * Setter for <code>matera.presupuesto.rm1</code>.
	 */
	public void setRm1(BigDecimal value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.rm1</code>.
	 */
	@Column(name = "rm1", precision = 7, scale = 2)
	public BigDecimal getRm1() {
		return (BigDecimal) getValue(18);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_profesional2</code>. id_profesional
	 */
	public void setIdProfesional2(Integer value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_profesional2</code>. id_profesional
	 */
	@Column(name = "id_profesional2", precision = 10)
	public Integer getIdProfesional2() {
		return (Integer) getValue(19);
	}

	/**
	 * Setter for <code>matera.presupuesto.rm2</code>.
	 */
	public void setRm2(BigDecimal value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.rm2</code>.
	 */
	@Column(name = "rm2", precision = 7, scale = 2)
	public BigDecimal getRm2() {
		return (BigDecimal) getValue(20);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_profesional3</code>. id_profesional
	 */
	public void setIdProfesional3(Integer value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_profesional3</code>. id_profesional
	 */
	@Column(name = "id_profesional3", precision = 10)
	public Integer getIdProfesional3() {
		return (Integer) getValue(21);
	}

	/**
	 * Setter for <code>matera.presupuesto.rm3</code>.
	 */
	public void setRm3(BigDecimal value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.rm3</code>.
	 */
	@Column(name = "rm3", precision = 7, scale = 2)
	public BigDecimal getRm3() {
		return (BigDecimal) getValue(22);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_vendedor</code>.
	 */
	public void setIdVendedor(Integer value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_vendedor</code>.
	 */
	@Column(name = "id_vendedor", precision = 10)
	public Integer getIdVendedor() {
		return (Integer) getValue(23);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_tipoOperacion</code>.
	 */
	public void setIdTipooperacion(Integer value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_tipoOperacion</code>.
	 */
	@Column(name = "id_tipoOperacion", precision = 10)
	public Integer getIdTipooperacion() {
		return (Integer) getValue(24);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_formaPago</code>.
	 */
	public void setIdFormapago(Integer value) {
		setValue(25, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_formaPago</code>.
	 */
	@Column(name = "id_formaPago", precision = 10)
	public Integer getIdFormapago() {
		return (Integer) getValue(25);
	}

	/**
	 * Setter for <code>matera.presupuesto.formaPagoOtro</code>.
	 */
	public void setFormapagootro(String value) {
		setValue(26, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.formaPagoOtro</code>.
	 */
	@Column(name = "formaPagoOtro", length = 45)
	public String getFormapagootro() {
		return (String) getValue(26);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_plazo</code>.
	 */
	public void setIdPlazo(Integer value) {
		setValue(27, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_plazo</code>.
	 */
	@Column(name = "id_plazo", precision = 10)
	public Integer getIdPlazo() {
		return (Integer) getValue(27);
	}

	/**
	 * Setter for <code>matera.presupuesto.plazoOtro</code>.
	 */
	public void setPlazootro(String value) {
		setValue(28, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.plazoOtro</code>.
	 */
	@Column(name = "plazoOtro", length = 45)
	public String getPlazootro() {
		return (String) getValue(28);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_mantenimiento</code>.
	 */
	public void setIdMantenimiento(Integer value) {
		setValue(29, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_mantenimiento</code>.
	 */
	@Column(name = "id_mantenimiento", precision = 10)
	public Integer getIdMantenimiento() {
		return (Integer) getValue(29);
	}

	/**
	 * Setter for <code>matera.presupuesto.mantenimientoOtro</code>.
	 */
	public void setMantenimientootro(String value) {
		setValue(30, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.mantenimientoOtro</code>.
	 */
	@Column(name = "mantenimientoOtro", length = 45)
	public String getMantenimientootro() {
		return (String) getValue(30);
	}

	/**
	 * Setter for <code>matera.presupuesto.notaPresu</code>.
	 */
	public void setNotapresu(String value) {
		setValue(31, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.notaPresu</code>.
	 */
	@Column(name = "notaPresu", length = 65535)
	public String getNotapresu() {
		return (String) getValue(31);
	}

	/**
	 * Setter for <code>matera.presupuesto.notaExtra</code>.
	 */
	public void setNotaextra(String value) {
		setValue(32, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.notaExtra</code>.
	 */
	@Column(name = "notaExtra", length = 65535)
	public String getNotaextra() {
		return (String) getValue(32);
	}

	/**
	 * Setter for <code>matera.presupuesto.observaciones</code>.
	 */
	public void setObservaciones(String value) {
		setValue(33, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.observaciones</code>.
	 */
	@Column(name = "observaciones", length = 65535)
	public String getObservaciones() {
		return (String) getValue(33);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_motivo</code>.
	 */
	public void setIdMotivo(Integer value) {
		setValue(34, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_motivo</code>.
	 */
	@Column(name = "id_motivo", precision = 10)
	public Integer getIdMotivo() {
		return (Integer) getValue(34);
	}

	/**
	 * Setter for <code>matera.presupuesto.motivo</code>.
	 */
	public void setMotivo(String value) {
		setValue(35, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.motivo</code>.
	 */
	@Column(name = "motivo", length = 65535)
	public String getMotivo() {
		return (String) getValue(35);
	}

	/**
	 * Setter for <code>matera.presupuesto.fechaAgenda</code>.
	 */
	public void setFechaagenda(Date value) {
		setValue(36, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.fechaAgenda</code>.
	 */
	@Column(name = "fechaAgenda")
	public Date getFechaagenda() {
		return (Date) getValue(36);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_tecnico</code>.
	 */
	public void setIdTecnico(Integer value) {
		setValue(37, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_tecnico</code>.
	 */
	@Column(name = "id_tecnico", precision = 10)
	public Integer getIdTecnico() {
		return (Integer) getValue(37);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_prestacion</code>.
	 */
	public void setIdPrestacion(Integer value) {
		setValue(38, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_prestacion</code>.
	 */
	@Column(name = "id_prestacion", precision = 10)
	public Integer getIdPrestacion() {
		return (Integer) getValue(38);
	}

	/**
	 * Setter for <code>matera.presupuesto.importePrestacion</code>.
	 */
	public void setImporteprestacion(BigDecimal value) {
		setValue(39, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.importePrestacion</code>.
	 */
	@Column(name = "importePrestacion", precision = 9, scale = 2)
	public BigDecimal getImporteprestacion() {
		return (BigDecimal) getValue(39);
	}

	/**
	 * Setter for <code>matera.presupuesto.fechaCirugia</code>.
	 */
	public void setFechacirugia(Date value) {
		setValue(40, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.fechaCirugia</code>.
	 */
	@Column(name = "fechaCirugia")
	public Date getFechacirugia() {
		return (Date) getValue(40);
	}

	/**
	 * Setter for <code>matera.presupuesto.fechaAprobacion</code>.
	 */
	public void setFechaaprobacion(Date value) {
		setValue(41, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.fechaAprobacion</code>.
	 */
	@Column(name = "fechaAprobacion")
	public Date getFechaaprobacion() {
		return (Date) getValue(41);
	}

	/**
	 * Setter for <code>matera.presupuesto.competencia</code>.
	 */
	public void setCompetencia(String value) {
		setValue(42, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.competencia</code>.
	 */
	@Column(name = "competencia", length = 65535)
	public String getCompetencia() {
		return (String) getValue(42);
	}

	/**
	 * Setter for <code>matera.presupuesto.lobby</code>.
	 */
	public void setLobby(String value) {
		setValue(43, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.lobby</code>.
	 */
	@Column(name = "lobby", length = 65535)
	public String getLobby() {
		return (String) getValue(43);
	}

	/**
	 * Setter for <code>matera.presupuesto.desactivado</code>.
	 */
	public void setDesactivado(String value) {
		setValue(44, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.desactivado</code>.
	 */
	@Column(name = "desactivado", length = 1)
	public String getDesactivado() {
		return (String) getValue(44);
	}

	/**
	 * Setter for <code>matera.presupuesto.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(45, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.id_empresa</code>.
	 */
	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(45);
	}

	/**
	 * Setter for <code>matera.presupuesto.fechareal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(46, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.fechareal</code>.
	 */
	@Column(name = "fechareal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(46);
	}

	/**
	 * Setter for <code>matera.presupuesto.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(47, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return (String) getValue(47);
	}

	/**
	 * Setter for <code>matera.presupuesto.costo_venta</code>.
	 */
	public void setCostoVenta(BigDecimal value) {
		setValue(48, value);
	}

	/**
	 * Getter for <code>matera.presupuesto.costo_venta</code>.
	 */
	@Column(name = "costo_venta", precision = 9, scale = 2)
	public BigDecimal getCostoVenta() {
		return (BigDecimal) getValue(48);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PresupuestoRecord
	 */
	public PresupuestoRecord() {
		super(Presupuesto.PRESUPUESTO);
	}

	/**
	 * Create a detached, initialised PresupuestoRecord
	 */
	public PresupuestoRecord(Integer idPresupuesto, String estado, Date fecha, Integer idZona, Integer idEntidad, Integer idLugarcirugia, String acompañamiento, Date fechaapertura, String horaapertura, String expediente, String contratacion, String paciente, String vip, String dni, String direccion, String telefono, BigDecimal total, Integer idProfesional1, BigDecimal rm1, Integer idProfesional2, BigDecimal rm2, Integer idProfesional3, BigDecimal rm3, Integer idVendedor, Integer idTipooperacion, Integer idFormapago, String formapagootro, Integer idPlazo, String plazootro, Integer idMantenimiento, String mantenimientootro, String notapresu, String notaextra, String observaciones, Integer idMotivo, String motivo, Date fechaagenda, Integer idTecnico, Integer idPrestacion, BigDecimal importeprestacion, Date fechacirugia, Date fechaaprobacion, String competencia, String lobby, String desactivado, Integer idEmpresa, Timestamp fechareal, String usuario, BigDecimal costoVenta) {
		super(Presupuesto.PRESUPUESTO);

		setValue(0, idPresupuesto);
		setValue(1, estado);
		setValue(2, fecha);
		setValue(3, idZona);
		setValue(4, idEntidad);
		setValue(5, idLugarcirugia);
		setValue(6, acompañamiento);
		setValue(7, fechaapertura);
		setValue(8, horaapertura);
		setValue(9, expediente);
		setValue(10, contratacion);
		setValue(11, paciente);
		setValue(12, vip);
		setValue(13, dni);
		setValue(14, direccion);
		setValue(15, telefono);
		setValue(16, total);
		setValue(17, idProfesional1);
		setValue(18, rm1);
		setValue(19, idProfesional2);
		setValue(20, rm2);
		setValue(21, idProfesional3);
		setValue(22, rm3);
		setValue(23, idVendedor);
		setValue(24, idTipooperacion);
		setValue(25, idFormapago);
		setValue(26, formapagootro);
		setValue(27, idPlazo);
		setValue(28, plazootro);
		setValue(29, idMantenimiento);
		setValue(30, mantenimientootro);
		setValue(31, notapresu);
		setValue(32, notaextra);
		setValue(33, observaciones);
		setValue(34, idMotivo);
		setValue(35, motivo);
		setValue(36, fechaagenda);
		setValue(37, idTecnico);
		setValue(38, idPrestacion);
		setValue(39, importeprestacion);
		setValue(40, fechacirugia);
		setValue(41, fechaaprobacion);
		setValue(42, competencia);
		setValue(43, lobby);
		setValue(44, desactivado);
		setValue(45, idEmpresa);
		setValue(46, fechareal);
		setValue(47, usuario);
		setValue(48, costoVenta);
	}
}