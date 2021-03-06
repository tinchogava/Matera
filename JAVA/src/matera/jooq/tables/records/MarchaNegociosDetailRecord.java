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

import matera.jooq.tables.MarchaNegociosDetail;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
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
@Table(name = "marcha_negocios_detail", schema = "matera")
public class MarchaNegociosDetailRecord extends UpdatableRecordImpl<MarchaNegociosDetailRecord> implements Record14<Integer, Integer, Timestamp, Date, Date, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, Integer, Integer> {

	private static final long serialVersionUID = -629188327;

	/**
	 * Setter for <code>matera.marcha_negocios_detail.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.id</code>.
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.id_marcha_negocios</code>.
	 */
	public void setIdMarchaNegocios(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.id_marcha_negocios</code>.
	 */
	@Column(name = "id_marcha_negocios", precision = 10)
	public Integer getIdMarchaNegocios() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.date</code>.
	 */
	public void setDate(Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.date</code>.
	 */
	@Column(name = "date", nullable = false)
	public Timestamp getDate() {
		return (Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.from</code>.
	 */
	public void setFrom(Date value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.from</code>.
	 */
	@Column(name = "from", nullable = false)
	public Date getFrom() {
		return (Date) getValue(3);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.to</code>.
	 */
	public void setTo(Date value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.to</code>.
	 */
	@Column(name = "to", nullable = false)
	public Date getTo() {
		return (Date) getValue(4);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.facturacion_total</code>.
	 */
	public void setFacturacionTotal(BigDecimal value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.facturacion_total</code>.
	 */
	@Column(name = "facturacion_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getFacturacionTotal() {
		return (BigDecimal) getValue(5);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.facturacion_pendiente</code>.
	 */
	public void setFacturacionPendiente(BigDecimal value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.facturacion_pendiente</code>.
	 */
	@Column(name = "facturacion_pendiente", nullable = false, precision = 10, scale = 2)
	public BigDecimal getFacturacionPendiente() {
		return (BigDecimal) getValue(6);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.cx_pendiente</code>.
	 */
	public void setCxPendiente(BigDecimal value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.cx_pendiente</code>.
	 */
	@Column(name = "cx_pendiente", nullable = false, precision = 10, scale = 2)
	public BigDecimal getCxPendiente() {
		return (BigDecimal) getValue(7);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.rm</code>.
	 */
	public void setRm(BigDecimal value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.rm</code>.
	 */
	@Column(name = "rm", nullable = false, precision = 10, scale = 2)
	public BigDecimal getRm() {
		return (BigDecimal) getValue(8);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.costo_venta</code>.
	 */
	public void setCostoVenta(BigDecimal value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.costo_venta</code>.
	 */
	@Column(name = "costo_venta", nullable = false, precision = 10, scale = 2)
	public BigDecimal getCostoVenta() {
		return (BigDecimal) getValue(9);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.oc_sin_fecha</code>.
	 */
	public void setOcSinFecha(BigDecimal value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.oc_sin_fecha</code>.
	 */
	@Column(name = "oc_sin_fecha", nullable = false, precision = 10, scale = 2)
	public BigDecimal getOcSinFecha() {
		return (BigDecimal) getValue(10);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.pendientes_vip</code>.
	 */
	public void setPendientesVip(BigDecimal value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.pendientes_vip</code>.
	 */
	@Column(name = "pendientes_vip", nullable = false, precision = 10, scale = 2)
	public BigDecimal getPendientesVip() {
		return (BigDecimal) getValue(11);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.id_usuario</code>.
	 */
	public void setIdUsuario(Integer value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.id_usuario</code>.
	 */
	@Column(name = "id_usuario", nullable = false, precision = 10)
	public Integer getIdUsuario() {
		return (Integer) getValue(12);
	}

	/**
	 * Setter for <code>matera.marcha_negocios_detail.id_zona</code>.
	 */
	public void setIdZona(Integer value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios_detail.id_zona</code>.
	 */
	@Column(name = "id_zona", nullable = false, precision = 10)
	public Integer getIdZona() {
		return (Integer) getValue(13);
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
	// Record14 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row14<Integer, Integer, Timestamp, Date, Date, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, Integer, Integer> fieldsRow() {
		return (Row14) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row14<Integer, Integer, Timestamp, Date, Date, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, Integer, Integer> valuesRow() {
		return (Row14) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.ID_MARCHA_NEGOCIOS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field4() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.FROM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field5() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.TO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field6() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.FACTURACION_TOTAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field7() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.FACTURACION_PENDIENTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field8() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.CX_PENDIENTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field9() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.RM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field10() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.COSTO_VENTA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field11() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.OC_SIN_FECHA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field12() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.PENDIENTES_VIP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field13() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.ID_USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field14() {
		return MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL.ID_ZONA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getIdMarchaNegocios();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value3() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value4() {
		return getFrom();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value5() {
		return getTo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value6() {
		return getFacturacionTotal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value7() {
		return getFacturacionPendiente();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value8() {
		return getCxPendiente();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value9() {
		return getRm();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value10() {
		return getCostoVenta();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value11() {
		return getOcSinFecha();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value12() {
		return getPendientesVip();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value13() {
		return getIdUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value14() {
		return getIdZona();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value2(Integer value) {
		setIdMarchaNegocios(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value3(Timestamp value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value4(Date value) {
		setFrom(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value5(Date value) {
		setTo(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value6(BigDecimal value) {
		setFacturacionTotal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value7(BigDecimal value) {
		setFacturacionPendiente(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value8(BigDecimal value) {
		setCxPendiente(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value9(BigDecimal value) {
		setRm(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value10(BigDecimal value) {
		setCostoVenta(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value11(BigDecimal value) {
		setOcSinFecha(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value12(BigDecimal value) {
		setPendientesVip(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value13(Integer value) {
		setIdUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord value14(Integer value) {
		setIdZona(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosDetailRecord values(Integer value1, Integer value2, Timestamp value3, Date value4, Date value5, BigDecimal value6, BigDecimal value7, BigDecimal value8, BigDecimal value9, BigDecimal value10, BigDecimal value11, BigDecimal value12, Integer value13, Integer value14) {
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
		value14(value14);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MarchaNegociosDetailRecord
	 */
	public MarchaNegociosDetailRecord() {
		super(MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL);
	}

	/**
	 * Create a detached, initialised MarchaNegociosDetailRecord
	 */
	public MarchaNegociosDetailRecord(Integer id, Integer idMarchaNegocios, Timestamp date, Date from, Date to, BigDecimal facturacionTotal, BigDecimal facturacionPendiente, BigDecimal cxPendiente, BigDecimal rm, BigDecimal costoVenta, BigDecimal ocSinFecha, BigDecimal pendientesVip, Integer idUsuario, Integer idZona) {
		super(MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL);

		setValue(0, id);
		setValue(1, idMarchaNegocios);
		setValue(2, date);
		setValue(3, from);
		setValue(4, to);
		setValue(5, facturacionTotal);
		setValue(6, facturacionPendiente);
		setValue(7, cxPendiente);
		setValue(8, rm);
		setValue(9, costoVenta);
		setValue(10, ocSinFecha);
		setValue(11, pendientesVip);
		setValue(12, idUsuario);
		setValue(13, idZona);
	}
}
