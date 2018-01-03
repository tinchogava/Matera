/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import matera.jooq.tables.MarchaNegocios;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
@Table(name = "marcha_negocios", schema = "matera", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"month", "year"})
})
public class MarchaNegociosRecord extends UpdatableRecordImpl<MarchaNegociosRecord> implements Record5<Integer, Integer, Integer, Double, BigDecimal> {

	private static final long serialVersionUID = -124340214;

	/**
	 * Setter for <code>matera.marcha_negocios.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios.id</code>.
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.marcha_negocios.month</code>.
	 */
	public void setMonth(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios.month</code>.
	 */
	@Column(name = "month", nullable = false, precision = 10)
	public Integer getMonth() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.marcha_negocios.year</code>.
	 */
	public void setYear(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios.year</code>.
	 */
	@Column(name = "year", nullable = false, precision = 10)
	public Integer getYear() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>matera.marcha_negocios.iva</code>.
	 */
	public void setIva(Double value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios.iva</code>.
	 */
	@Column(name = "iva", nullable = false, precision = 22)
	public Double getIva() {
		return (Double) getValue(3);
	}

	/**
	 * Setter for <code>matera.marcha_negocios.forecast</code>.
	 */
	public void setForecast(BigDecimal value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.marcha_negocios.forecast</code>.
	 */
	@Column(name = "forecast", nullable = false, precision = 10, scale = 2)
	public BigDecimal getForecast() {
		return (BigDecimal) getValue(4);
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
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, Integer, Integer, Double, BigDecimal> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, Integer, Integer, Double, BigDecimal> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return MarchaNegocios.MARCHA_NEGOCIOS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return MarchaNegocios.MARCHA_NEGOCIOS.MONTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return MarchaNegocios.MARCHA_NEGOCIOS.YEAR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field4() {
		return MarchaNegocios.MARCHA_NEGOCIOS.IVA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field5() {
		return MarchaNegocios.MARCHA_NEGOCIOS.FORECAST;
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
		return getMonth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getYear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value4() {
		return getIva();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value5() {
		return getForecast();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosRecord value2(Integer value) {
		setMonth(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosRecord value3(Integer value) {
		setYear(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosRecord value4(Double value) {
		setIva(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosRecord value5(BigDecimal value) {
		setForecast(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarchaNegociosRecord values(Integer value1, Integer value2, Integer value3, Double value4, BigDecimal value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MarchaNegociosRecord
	 */
	public MarchaNegociosRecord() {
		super(MarchaNegocios.MARCHA_NEGOCIOS);
	}

	/**
	 * Create a detached, initialised MarchaNegociosRecord
	 */
	public MarchaNegociosRecord(Integer id, Integer month, Integer year, Double iva, BigDecimal forecast) {
		super(MarchaNegocios.MARCHA_NEGOCIOS);

		setValue(0, id);
		setValue(1, month);
		setValue(2, year);
		setValue(3, iva);
		setValue(4, forecast);
	}
}