/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Clasiproducto;

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
@Table(name = "clasiproducto", schema = "matera")
public class ClasiproductoRecord extends UpdatableRecordImpl<ClasiproductoRecord> implements Record3<Integer, String, String> {

	private static final long serialVersionUID = -845584901;

	/**
	 * Setter for <code>matera.clasiproducto.id_clasiProducto</code>.
	 */
	public void setIdClasiproducto(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.clasiproducto.id_clasiProducto</code>.
	 */
	@Id
	@Column(name = "id_clasiProducto", unique = true, nullable = false, precision = 10)
	public Integer getIdClasiproducto() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.clasiproducto.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.clasiproducto.nombre</code>.
	 */
	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.clasiproducto.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.clasiproducto.habilita</code>.
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
		return Clasiproducto.CLASIPRODUCTO.ID_CLASIPRODUCTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Clasiproducto.CLASIPRODUCTO.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Clasiproducto.CLASIPRODUCTO.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdClasiproducto();
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
	public ClasiproductoRecord value1(Integer value) {
		setIdClasiproducto(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClasiproductoRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClasiproductoRecord value3(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClasiproductoRecord values(Integer value1, String value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ClasiproductoRecord
	 */
	public ClasiproductoRecord() {
		super(Clasiproducto.CLASIPRODUCTO);
	}

	/**
	 * Create a detached, initialised ClasiproductoRecord
	 */
	public ClasiproductoRecord(Integer idClasiproducto, String nombre, String habilita) {
		super(Clasiproducto.CLASIPRODUCTO);

		setValue(0, idClasiproducto);
		setValue(1, nombre);
		setValue(2, habilita);
	}
        
        @Override
        public String toString() {
            return getNombre();
        }
}
