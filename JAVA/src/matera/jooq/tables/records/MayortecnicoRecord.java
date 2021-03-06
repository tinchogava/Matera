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

import matera.jooq.tables.Mayortecnico;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
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
@Table(name = "mayortecnico", schema = "matera")
public class MayortecnicoRecord extends UpdatableRecordImpl<MayortecnicoRecord> implements Record11<Integer, Date, Integer, Integer, String, BigDecimal, String, Integer, Integer, String, Timestamp> {

	private static final long serialVersionUID = -133381385;

	/**
	 * Setter for <code>matera.mayortecnico.id_mayorTecnico</code>.
	 */
	public void setIdMayortecnico(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.id_mayorTecnico</code>.
	 */
	@Id
	@Column(name = "id_mayorTecnico", unique = true, nullable = false, precision = 10)
	public Integer getIdMayortecnico() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.mayortecnico.fecha</code>.
	 */
	public void setFecha(Date value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.fecha</code>.
	 */
	@Column(name = "fecha")
	public Date getFecha() {
		return (Date) getValue(1);
	}

	/**
	 * Setter for <code>matera.mayortecnico.id_presupuesto</code>.
	 */
	public void setIdPresupuesto(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.id_presupuesto</code>.
	 */
	@Column(name = "id_presupuesto", precision = 10)
	public Integer getIdPresupuesto() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>matera.mayortecnico.id_tecnico</code>.
	 */
	public void setIdTecnico(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.id_tecnico</code>.
	 */
	@Column(name = "id_tecnico", precision = 10)
	public Integer getIdTecnico() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>matera.mayortecnico.dc</code>.
	 */
	public void setDc(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.dc</code>.
	 */
	@Column(name = "dc", length = 1)
	public String getDc() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>matera.mayortecnico.importe</code>.
	 */
	public void setImporte(BigDecimal value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.importe</code>.
	 */
	@Column(name = "importe", precision = 7, scale = 2)
	public BigDecimal getImporte() {
		return (BigDecimal) getValue(5);
	}

	/**
	 * Setter for <code>matera.mayortecnico.observaciones</code>.
	 */
	public void setObservaciones(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.observaciones</code>.
	 */
	@Column(name = "observaciones", length = 65535)
	public String getObservaciones() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>matera.mayortecnico.liquidacion</code>.
	 */
	public void setLiquidacion(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.liquidacion</code>.
	 */
	@Column(name = "liquidacion", precision = 10)
	public Integer getLiquidacion() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>matera.mayortecnico.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.id_empresa</code>.
	 */
	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>matera.mayortecnico.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>matera.mayortecnico.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>matera.mayortecnico.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(10);
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
	// Record11 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Date, Integer, Integer, String, BigDecimal, String, Integer, Integer, String, Timestamp> fieldsRow() {
		return (Row11) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Date, Integer, Integer, String, BigDecimal, String, Integer, Integer, String, Timestamp> valuesRow() {
		return (Row11) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Mayortecnico.MAYORTECNICO.ID_MAYORTECNICO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field2() {
		return Mayortecnico.MAYORTECNICO.FECHA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Mayortecnico.MAYORTECNICO.ID_PRESUPUESTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Mayortecnico.MAYORTECNICO.ID_TECNICO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Mayortecnico.MAYORTECNICO.DC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field6() {
		return Mayortecnico.MAYORTECNICO.IMPORTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return Mayortecnico.MAYORTECNICO.OBSERVACIONES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return Mayortecnico.MAYORTECNICO.LIQUIDACION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return Mayortecnico.MAYORTECNICO.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return Mayortecnico.MAYORTECNICO.USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field11() {
		return Mayortecnico.MAYORTECNICO.FECHAREAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdMayortecnico();
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
		return getIdTecnico();
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
		return getLiquidacion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getIdEmpresa();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value11() {
		return getFechareal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value1(Integer value) {
		setIdMayortecnico(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value2(Date value) {
		setFecha(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value3(Integer value) {
		setIdPresupuesto(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value4(Integer value) {
		setIdTecnico(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value5(String value) {
		setDc(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value6(BigDecimal value) {
		setImporte(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value7(String value) {
		setObservaciones(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value8(Integer value) {
		setLiquidacion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value9(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value10(String value) {
		setUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord value11(Timestamp value) {
		setFechareal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MayortecnicoRecord values(Integer value1, Date value2, Integer value3, Integer value4, String value5, BigDecimal value6, String value7, Integer value8, Integer value9, String value10, Timestamp value11) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MayortecnicoRecord
	 */
	public MayortecnicoRecord() {
		super(Mayortecnico.MAYORTECNICO);
	}

	/**
	 * Create a detached, initialised MayortecnicoRecord
	 */
	public MayortecnicoRecord(Integer idMayortecnico, Date fecha, Integer idPresupuesto, Integer idTecnico, String dc, BigDecimal importe, String observaciones, Integer liquidacion, Integer idEmpresa, String usuario, Timestamp fechareal) {
		super(Mayortecnico.MAYORTECNICO);

		setValue(0, idMayortecnico);
		setValue(1, fecha);
		setValue(2, idPresupuesto);
		setValue(3, idTecnico);
		setValue(4, dc);
		setValue(5, importe);
		setValue(6, observaciones);
		setValue(7, liquidacion);
		setValue(8, idEmpresa);
		setValue(9, usuario);
		setValue(10, fechareal);
	}
}
