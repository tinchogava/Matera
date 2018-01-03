/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Zona;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
@Table(name = "zona", schema = "matera")
public class ZonaRecord extends UpdatableRecordImpl<ZonaRecord> implements Record3<Integer, String, String> {

	private static final long serialVersionUID = -177937525;

	/**
	 * Setter for <code>matera.zona.id_zona</code>.
	 */
	public void setIdZona(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.zona.id_zona</code>.
	 */
	@Id
	@Column(name = "id_zona", unique = true, nullable = false, precision = 10)
	public Integer getIdZona() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.zona.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.zona.nombre</code>.
	 */
	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.zona.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.zona.habilita</code>.
	 */
	@Column(name = "habilita", nullable = false, length = 1)
	public String getHabilita() {
		return (String) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, String> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, String> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Zona.ZONA.ID_ZONA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Zona.ZONA.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Zona.ZONA.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdZona();
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
	public String value3() {
		return getHabilita();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ZonaRecord value1(Integer value) {
		setIdZona(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ZonaRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ZonaRecord value3(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ZonaRecord values(Integer value1, String value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ZonaRecord
	 */
	public ZonaRecord() {
		super(Zona.ZONA);
	}

	/**
	 * Create a detached, initialised ZonaRecord
	 */
	public ZonaRecord(Integer idZona, String nombre, String habilita) {
		super(Zona.ZONA);

		setValue(0, idZona);
		setValue(1, nombre);
		setValue(2, habilita);
	}
        
    @Override
    public String toString() {
        return getNombre();
    }
}
