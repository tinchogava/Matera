/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Provincia;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
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
@Table(name = "provincia", schema = "matera")
public class ProvinciaRecord extends UpdatableRecordImpl<ProvinciaRecord> implements Record2<Integer, String> {

	private static final long serialVersionUID = 2130206283;

	/**
	 * Setter for <code>matera.provincia.id_provincia</code>.
	 */
	public void setIdProvincia(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.provincia.id_provincia</code>.
	 */
	@Id
	@Column(name = "id_provincia", unique = true, nullable = false, precision = 10)
	public Integer getIdProvincia() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.provincia.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.provincia.nombre</code>.
	 */
	@Column(name = "nombre", nullable = false, length = 50)
	public String getNombre() {
		return (String) getValue(1);
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
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Provincia.PROVINCIA.ID_PROVINCIA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Provincia.PROVINCIA.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdProvincia();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getNombre();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProvinciaRecord value1(Integer value) {
		setIdProvincia(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProvinciaRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProvinciaRecord values(Integer value1, String value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ProvinciaRecord
	 */
	public ProvinciaRecord() {
		super(Provincia.PROVINCIA);
	}

	/**
	 * Create a detached, initialised ProvinciaRecord
	 */
	public ProvinciaRecord(Integer idProvincia, String nombre) {
		super(Provincia.PROVINCIA);

		setValue(0, idProvincia);
		setValue(1, nombre);
	}
}