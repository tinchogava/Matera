/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Forecast;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
@Table(name = "forecast", schema = "matera")
public class ForecastRecord extends UpdatableRecordImpl<ForecastRecord> implements Record8<Integer, Integer, Integer, Integer, Integer, Double, String, Timestamp> {

	private static final long serialVersionUID = -1224704116;

	/**
	 * Setter for <code>matera.forecast.id_forecast</code>.
	 */
	public void setIdForecast(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.forecast.id_forecast</code>.
	 */
	@Id
	@Column(name = "id_forecast", unique = true, nullable = false, precision = 10)
	public Integer getIdForecast() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.forecast.anio</code>.
	 */
	public void setAnio(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.forecast.anio</code>.
	 */
	@Column(name = "anio", precision = 10)
	public Integer getAnio() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.forecast.id_vendedor</code>.
	 */
	public void setIdVendedor(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.forecast.id_vendedor</code>.
	 */
	@Column(name = "id_vendedor", precision = 10)
	public Integer getIdVendedor() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>matera.forecast.id_entidad</code>.
	 */
	public void setIdEntidad(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.forecast.id_entidad</code>.
	 */
	@Column(name = "id_entidad", precision = 10)
	public Integer getIdEntidad() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>matera.forecast.id_productoFact</code>.
	 */
	public void setIdProductofact(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.forecast.id_productoFact</code>.
	 */
	@Column(name = "id_productoFact", precision = 10)
	public Integer getIdProductofact() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>matera.forecast.cantidad</code>.
	 */
	public void setCantidad(Double value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>matera.forecast.cantidad</code>.
	 */
	@Column(name = "cantidad", precision = 11, scale = 2)
	public Double getCantidad() {
		return (Double) getValue(5);
	}

	/**
	 * Setter for <code>matera.forecast.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>matera.forecast.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>matera.forecast.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>matera.forecast.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(7);
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
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<Integer, Integer, Integer, Integer, Integer, Double, String, Timestamp> fieldsRow() {
		return (Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<Integer, Integer, Integer, Integer, Integer, Double, String, Timestamp> valuesRow() {
		return (Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Forecast.FORECAST.ID_FORECAST;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Forecast.FORECAST.ANIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Forecast.FORECAST.ID_VENDEDOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Forecast.FORECAST.ID_ENTIDAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return Forecast.FORECAST.ID_PRODUCTOFACT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field6() {
		return Forecast.FORECAST.CANTIDAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return Forecast.FORECAST.USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field8() {
		return Forecast.FORECAST.FECHAREAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdForecast();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getAnio();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getIdVendedor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getIdEntidad();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getIdProductofact();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value6() {
		return getCantidad();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value8() {
		return getFechareal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value1(Integer value) {
		setIdForecast(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value2(Integer value) {
		setAnio(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value3(Integer value) {
		setIdVendedor(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value4(Integer value) {
		setIdEntidad(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value5(Integer value) {
		setIdProductofact(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value6(Double value) {
		setCantidad(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value7(String value) {
		setUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord value8(Timestamp value) {
		setFechareal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForecastRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Double value6, String value7, Timestamp value8) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ForecastRecord
	 */
	public ForecastRecord() {
		super(Forecast.FORECAST);
	}

	/**
	 * Create a detached, initialised ForecastRecord
	 */
	public ForecastRecord(Integer idForecast, Integer anio, Integer idVendedor, Integer idEntidad, Integer idProductofact, Double cantidad, String usuario, Timestamp fechareal) {
		super(Forecast.FORECAST);

		setValue(0, idForecast);
		setValue(1, anio);
		setValue(2, idVendedor);
		setValue(3, idEntidad);
		setValue(4, idProductofact);
		setValue(5, cantidad);
		setValue(6, usuario);
		setValue(7, fechareal);
	}
}
