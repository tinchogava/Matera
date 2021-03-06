/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Tecnico;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
@Table(name = "tecnico", schema = "matera")
public class TecnicoRecord extends UpdatableRecordImpl<TecnicoRecord> implements Record4<Integer, String, String, Integer> {

	private static final long serialVersionUID = 345408155;

	/**
	 * Setter for <code>matera.tecnico.id_tecnico</code>.
	 */
	public void setIdTecnico(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.tecnico.id_tecnico</code>.
	 */
	@Id
	@Column(name = "id_tecnico", unique = true, nullable = false, precision = 10)
	public Integer getIdTecnico() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.tecnico.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.tecnico.nombre</code>.
	 */
	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.tecnico.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.tecnico.habilita</code>.
	 */
	@Column(name = "habilita", nullable = false, length = 1)
	public String getHabilita() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.tecnico.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.tecnico.id_empresa</code>.
	 */
	@Column(name = "id_empresa", nullable = false, precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, String, String, Integer> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, String, String, Integer> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Tecnico.TECNICO.ID_TECNICO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Tecnico.TECNICO.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Tecnico.TECNICO.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Tecnico.TECNICO.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdTecnico();
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
	public Integer value4() {
		return getIdEmpresa();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TecnicoRecord value1(Integer value) {
		setIdTecnico(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TecnicoRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TecnicoRecord value3(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TecnicoRecord value4(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TecnicoRecord values(Integer value1, String value2, String value3, Integer value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TecnicoRecord
	 */
	public TecnicoRecord() {
		super(Tecnico.TECNICO);
	}

	/**
	 * Create a detached, initialised TecnicoRecord
	 */
	public TecnicoRecord(Integer idTecnico, String nombre, String habilita, Integer idEmpresa) {
		super(Tecnico.TECNICO);

		setValue(0, idTecnico);
		setValue(1, nombre);
		setValue(2, habilita);
		setValue(3, idEmpresa);
	}
        
        @Override
        public String toString() {
            return getNombre();
        }
}
