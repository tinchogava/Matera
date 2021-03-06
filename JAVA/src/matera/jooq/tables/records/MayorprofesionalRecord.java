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

import matera.jooq.tables.Mayorprofesional;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
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
@Table(name = "mayorprofesional", schema = "matera")
public class MayorprofesionalRecord extends UpdatableRecordImpl<MayorprofesionalRecord> implements Record13<Integer, Date, Integer, Integer, String, BigDecimal, String, Integer, Integer, String, Integer, String, Timestamp> {

	private static final long serialVersionUID = 891264699;

	/**
	 * Setter for <code>matera.mayorprofesional.id_mayorProfesional</code>.
	 */
	public void setIdMayorprofesional(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.id_mayorProfesional</code>.
	 */
	@Id
	@Column(name = "id_mayorProfesional", unique = true, nullable = false, precision = 10)
	public Integer getIdMayorprofesional() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.fecha</code>.
	 */
	public void setFecha(Date value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.fecha</code>.
	 */
	@Column(name = "fecha")
	public Date getFecha() {
		return (Date) getValue(1);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.id_presupuesto</code>.
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.id_presupuesto</code>.
	 */
	@Column(name = "id_presupuesto", precision = 10)
	public Integer getIdPresupuesto() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.id_profesional</code>.
	 */
	public void setIdProfesional(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.id_profesional</code>.
	 */
	@Column(name = "id_profesional", precision = 10)
	public Integer getIdProfesional() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.dc</code>.
	 */
	public void setDc(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.dc</code>.
	 */
	@Column(name = "dc", length = 1)
	public String getDc() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.importe</code>.
	 */
	public void setImporte(BigDecimal value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.importe</code>.
	 */
	@Column(name = "importe", precision = 9, scale = 2)
	public BigDecimal getImporte() {
		return (BigDecimal) getValue(5);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.observaciones</code>.
	 */
	public void setObservaciones(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.observaciones</code>.
	 */
	@Column(name = "observaciones", length = 65535)
	public String getObservaciones() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.preliquidacion</code>.
	 */
	public void setPreliquidacion(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.preliquidacion</code>.
	 */
	@Column(name = "preliquidacion", precision = 10)
	public Integer getPreliquidacion() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.liquidacion</code>.
	 */
	public void setLiquidacion(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.liquidacion</code>.
	 */
	@Column(name = "liquidacion", precision = 10)
	public Integer getLiquidacion() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.transferencia</code>.
	 */
	public void setTransferencia(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.transferencia</code>.
	 */
	@Column(name = "transferencia", length = 1)
	public String getTransferencia() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.id_empresa</code>.
	 */
	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(10);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>matera.mayorprofesional.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>matera.mayorprofesional.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(12);
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
	// Record13 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row13<Integer, Date, Integer, Integer, String, BigDecimal, String, Integer, Integer, String, Integer, String, Timestamp> fieldsRow() {
		return (Row13) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row13<Integer, Date, Integer, Integer, String, BigDecimal, String, Integer, Integer, String, Integer, String, Timestamp> valuesRow() {
		return (Row13) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Mayorprofesional.MAYORPROFESIONAL.ID_MAYORPROFESIONAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field2() {
		return Mayorprofesional.MAYORPROFESIONAL.FECHA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Mayorprofesional.MAYORPROFESIONAL.ID_PRESUPUESTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Mayorprofesional.MAYORPROFESIONAL.ID_PROFESIONAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Mayorprofesional.MAYORPROFESIONAL.DC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field6() {
		return Mayorprofesional.MAYORPROFESIONAL.IMPORTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return Mayorprofesional.MAYORPROFESIONAL.OBSERVACIONES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return Mayorprofesional.MAYORPROFESIONAL.PRELIQUIDACION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return Mayorprofesional.MAYORPROFESIONAL.LIQUIDACION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return Mayorprofesional.MAYORPROFESIONAL.TRANSFERENCIA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return Mayorprofesional.MAYORPROFESIONAL.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return Mayorprofesional.MAYORPROFESIONAL.USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field13() {
		return Mayorprofesional.MAYORPROFESIONAL.FECHAREAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdMayorprofesional();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value2() {
		return getFecha();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getIdPresupuesto();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getIdProfesional();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getDc();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value6() {
		return getImporte();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getObservaciones();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getPreliquidacion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getLiquidacion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getTransferencia();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getIdEmpresa();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value13() {
		return getFechareal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value1(Integer value) {
		setIdMayorprofesional(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value2(Date value) {
		setFecha(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value3(Integer value) {
		setIdPresupuesto(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value4(Integer value) {
		setIdProfesional(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value5(String value) {
		setDc(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value6(BigDecimal value) {
		setImporte(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value7(String value) {
		setObservaciones(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value8(Integer value) {
		setPreliquidacion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value9(Integer value) {
		setLiquidacion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value10(String value) {
		setTransferencia(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value11(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value12(String value) {
		setUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord value13(Timestamp value) {
		setFechareal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayorprofesionalRecord values(Integer value1, Date value2, Integer value3, Integer value4, String value5, BigDecimal value6, String value7, Integer value8, Integer value9, String value10, Integer value11, String value12, Timestamp value13) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		value12(value12);
		value13(value13);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MayorprofesionalRecord
	 */
	public MayorprofesionalRecord() {
		super(Mayorprofesional.MAYORPROFESIONAL);
	}

	/**
	 * Create a detached, initialised MayorprofesionalRecord
	 */
	public MayorprofesionalRecord(Integer idMayorprofesional, Date fecha, Integer idPresupuesto, Integer idProfesional, String dc, BigDecimal importe, String observaciones, Integer preliquidacion, Integer liquidacion, String transferencia, Integer idEmpresa, String usuario, Timestamp fechareal) {
		super(Mayorprofesional.MAYORPROFESIONAL);

		setValue(0, idMayorprofesional);
		setValue(1, fecha);
		setValue(2, idPresupuesto);
		setValue(3, idProfesional);
		setValue(4, dc);
		setValue(5, importe);
		setValue(6, observaciones);
		setValue(7, preliquidacion);
		setValue(8, liquidacion);
		setValue(9, transferencia);
		setValue(10, idEmpresa);
		setValue(11, usuario);
		setValue(12, fechareal);
	}
}
