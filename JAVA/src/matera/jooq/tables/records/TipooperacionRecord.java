/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Tipooperacion;

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
@Table(name = "tipooperacion", schema = "matera")
public class TipooperacionRecord extends UpdatableRecordImpl<TipooperacionRecord> implements Record3<Integer, String, String> {

	private static final long serialVersionUID = 1071522557;

	/**
	 * Setter for <code>matera.tipooperacion.id_tipoOperacion</code>.
	 */
	public void setIdTipooperacion(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.tipooperacion.id_tipoOperacion</code>.
	 */
	@Id
	@Column(name = "id_tipoOperacion", unique = true, nullable = false, precision = 10)
	public Integer getIdTipooperacion() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.tipooperacion.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.tipooperacion.nombre</code>.
	 */
	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.tipooperacion.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.tipooperacion.habilita</code>.
	 */
	@Column(name = "habilita", length = 1)
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
		return Tipooperacion.TIPOOPERACION.ID_TIPOOPERACION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Tipooperacion.TIPOOPERACION.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Tipooperacion.TIPOOPERACION.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdTipooperacion();
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
	public TipooperacionRecord value1(Integer value) {
		setIdTipooperacion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipooperacionRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipooperacionRecord value3(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipooperacionRecord values(Integer value1, String value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TipooperacionRecord
	 */
	public TipooperacionRecord() {
		super(Tipooperacion.TIPOOPERACION);
	}

	/**
	 * Create a detached, initialised TipooperacionRecord
	 */
	public TipooperacionRecord(Integer idTipooperacion, String nombre, String habilita) {
		super(Tipooperacion.TIPOOPERACION);

		setValue(0, idTipooperacion);
		setValue(1, nombre);
		setValue(2, habilita);
	}
        
        @Override
        public String toString() {
            return getNombre();
        }
}
