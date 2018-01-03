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

import matera.jooq.tables.Intersucursal;

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
@Table(name = "intersucursal", schema = "matera")
public class IntersucursalRecord extends UpdatableRecordImpl<IntersucursalRecord> implements Record5<Integer, Integer, String, String, Timestamp> {

	private static final long serialVersionUID = -1529367780;

	/**
	 * Setter for <code>matera.intersucursal.id_intersucursal</code>.
	 */
	public void setIdIntersucursal(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.intersucursal.id_intersucursal</code>.
	 */
	@Id
	@Column(name = "id_intersucursal", unique = true, nullable = false, precision = 10)
	public Integer getIdIntersucursal() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.intersucursal.id_remito</code>.
	 */
	public void setIdRemito(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.intersucursal.id_remito</code>.
	 */
	@Column(name = "id_remito", precision = 10)
	public Integer getIdRemito() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.intersucursal.recibido</code>.
	 */
	public void setRecibido(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.intersucursal.recibido</code>.
	 */
	@Column(name = "recibido", length = 1)
	public String getRecibido() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.intersucursal.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.intersucursal.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>matera.intersucursal.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.intersucursal.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(4);
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
	public Row5<Integer, Integer, String, String, Timestamp> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, Integer, String, String, Timestamp> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Intersucursal.INTERSUCURSAL.ID_INTERSUCURSAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Intersucursal.INTERSUCURSAL.ID_REMITO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Intersucursal.INTERSUCURSAL.RECIBIDO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Intersucursal.INTERSUCURSAL.USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return Intersucursal.INTERSUCURSAL.FECHAREAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdIntersucursal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getIdRemito();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getRecibido();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getFechareal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersucursalRecord value1(Integer value) {
		setIdIntersucursal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersucursalRecord value2(Integer value) {
		setIdRemito(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersucursalRecord value3(String value) {
		setRecibido(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersucursalRecord value4(String value) {
		setUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersucursalRecord value5(Timestamp value) {
		setFechareal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersucursalRecord values(Integer value1, Integer value2, String value3, String value4, Timestamp value5) {
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
	 * Create a detached IntersucursalRecord
	 */
	public IntersucursalRecord() {
		super(Intersucursal.INTERSUCURSAL);
	}

	/**
	 * Create a detached, initialised IntersucursalRecord
	 */
	public IntersucursalRecord(Integer idIntersucursal, Integer idRemito, String recibido, String usuario, Timestamp fechareal) {
		super(Intersucursal.INTERSUCURSAL);

		setValue(0, idIntersucursal);
		setValue(1, idRemito);
		setValue(2, recibido);
		setValue(3, usuario);
		setValue(4, fechareal);
	}
}
