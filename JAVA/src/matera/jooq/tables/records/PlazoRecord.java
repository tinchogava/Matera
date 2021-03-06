/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Plazo;

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
@Table(name = "plazo", schema = "matera")
public class PlazoRecord extends UpdatableRecordImpl<PlazoRecord> implements Record3<Integer, String, String> {

	private static final long serialVersionUID = -278843893;

	/**
	 * Setter for <code>matera.plazo.id_plazo</code>.
	 */
	public void setIdPlazo(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.plazo.id_plazo</code>.
	 */
	@Id
	@Column(name = "id_plazo", unique = true, nullable = false, precision = 10)
	public Integer getIdPlazo() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.plazo.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.plazo.nombre</code>.
	 */
	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.plazo.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.plazo.habilita</code>.
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
		return Plazo.PLAZO.ID_PLAZO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Plazo.PLAZO.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Plazo.PLAZO.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdPlazo();
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
	public PlazoRecord value1(Integer value) {
		setIdPlazo(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlazoRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlazoRecord value3(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlazoRecord values(Integer value1, String value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PlazoRecord
	 */
	public PlazoRecord() {
		super(Plazo.PLAZO);
	}

	/**
	 * Create a detached, initialised PlazoRecord
	 */
	public PlazoRecord(Integer idPlazo, String nombre, String habilita) {
		super(Plazo.PLAZO);

		setValue(0, idPlazo);
		setValue(1, nombre);
		setValue(2, habilita);
	}
        
        @Override
        public String toString() {
            return getNombre();
        }
}
