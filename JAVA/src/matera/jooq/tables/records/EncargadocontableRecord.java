/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Encargadocontable;

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
@Table(name = "encargadocontable", schema = "matera")
public class EncargadocontableRecord extends UpdatableRecordImpl<EncargadocontableRecord> implements Record5<Integer, Integer, String, String, String> {

	private static final long serialVersionUID = 501048287;

	/**
	 * Setter for <code>matera.encargadocontable.id_encargadoContable</code>.
	 */
	public void setIdEncargadocontable(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.encargadocontable.id_encargadoContable</code>.
	 */
	@Id
	@Column(name = "id_encargadoContable", unique = true, nullable = false, precision = 10)
	public Integer getIdEncargadocontable() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.encargadocontable.id_entidad</code>.
	 */
	public void setIdEntidad(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.encargadocontable.id_entidad</code>.
	 */
	@Column(name = "id_entidad", precision = 10)
	public Integer getIdEntidad() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.encargadocontable.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.encargadocontable.nombre</code>.
	 */
	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.encargadocontable.telefono</code>.
	 */
	public void setTelefono(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.encargadocontable.telefono</code>.
	 */
	@Column(name = "telefono", length = 45)
	public String getTelefono() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>matera.encargadocontable.email</code>.
	 */
	public void setEmail(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.encargadocontable.email</code>.
	 */
	@Column(name = "email", length = 45)
	public String getEmail() {
		return (String) getValue(4);
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
	public Row5<Integer, Integer, String, String, String> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, Integer, String, String, String> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Encargadocontable.ENCARGADOCONTABLE.ID_ENCARGADOCONTABLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Encargadocontable.ENCARGADOCONTABLE.ID_ENTIDAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Encargadocontable.ENCARGADOCONTABLE.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Encargadocontable.ENCARGADOCONTABLE.TELEFONO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Encargadocontable.ENCARGADOCONTABLE.EMAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdEncargadocontable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getIdEntidad();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getNombre();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getTelefono();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getEmail();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadocontableRecord value1(Integer value) {
		setIdEncargadocontable(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadocontableRecord value2(Integer value) {
		setIdEntidad(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadocontableRecord value3(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadocontableRecord value4(String value) {
		setTelefono(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadocontableRecord value5(String value) {
		setEmail(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadocontableRecord values(Integer value1, Integer value2, String value3, String value4, String value5) {
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
	 * Create a detached EncargadocontableRecord
	 */
	public EncargadocontableRecord() {
		super(Encargadocontable.ENCARGADOCONTABLE);
	}

	/**
	 * Create a detached, initialised EncargadocontableRecord
	 */
	public EncargadocontableRecord(Integer idEncargadocontable, Integer idEntidad, String nombre, String telefono, String email) {
		super(Encargadocontable.ENCARGADOCONTABLE);

		setValue(0, idEncargadocontable);
		setValue(1, idEntidad);
		setValue(2, nombre);
		setValue(3, telefono);
		setValue(4, email);
	}
}
