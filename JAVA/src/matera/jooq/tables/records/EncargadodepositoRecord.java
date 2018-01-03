/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Encargadodeposito;

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
@Table(name = "encargadodeposito", schema = "matera")
public class EncargadodepositoRecord extends UpdatableRecordImpl<EncargadodepositoRecord> implements Record5<Integer, Integer, String, String, String> {

	private static final long serialVersionUID = -620862605;

	/**
	 * Setter for <code>matera.encargadodeposito.id_encargadoDeposito</code>.
	 */
	public void setIdEncargadodeposito(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.encargadodeposito.id_encargadoDeposito</code>.
	 */
	@Id
	@Column(name = "id_encargadoDeposito", unique = true, nullable = false, precision = 10)
	public Integer getIdEncargadodeposito() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.encargadodeposito.id_proveedor</code>.
	 */
	public void setIdProveedor(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.encargadodeposito.id_proveedor</code>.
	 */
	@Column(name = "id_proveedor", precision = 10)
	public Integer getIdProveedor() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.encargadodeposito.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.encargadodeposito.nombre</code>.
	 */
	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.encargadodeposito.telefono</code>.
	 */
	public void setTelefono(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.encargadodeposito.telefono</code>.
	 */
	@Column(name = "telefono", length = 45)
	public String getTelefono() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>matera.encargadodeposito.email</code>.
	 */
	public void setEmail(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.encargadodeposito.email</code>.
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
		return Encargadodeposito.ENCARGADODEPOSITO.ID_ENCARGADODEPOSITO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Encargadodeposito.ENCARGADODEPOSITO.ID_PROVEEDOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Encargadodeposito.ENCARGADODEPOSITO.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Encargadodeposito.ENCARGADODEPOSITO.TELEFONO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Encargadodeposito.ENCARGADODEPOSITO.EMAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdEncargadodeposito();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getIdProveedor();
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
	public EncargadodepositoRecord value1(Integer value) {
		setIdEncargadodeposito(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadodepositoRecord value2(Integer value) {
		setIdProveedor(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadodepositoRecord value3(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadodepositoRecord value4(String value) {
		setTelefono(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadodepositoRecord value5(String value) {
		setEmail(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EncargadodepositoRecord values(Integer value1, Integer value2, String value3, String value4, String value5) {
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
	 * Create a detached EncargadodepositoRecord
	 */
	public EncargadodepositoRecord() {
		super(Encargadodeposito.ENCARGADODEPOSITO);
	}

	/**
	 * Create a detached, initialised EncargadodepositoRecord
	 */
	public EncargadodepositoRecord(Integer idEncargadodeposito, Integer idProveedor, String nombre, String telefono, String email) {
		super(Encargadodeposito.ENCARGADODEPOSITO);

		setValue(0, idEncargadodeposito);
		setValue(1, idProveedor);
		setValue(2, nombre);
		setValue(3, telefono);
		setValue(4, email);
	}
}