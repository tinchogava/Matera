/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Prestacion;

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
@Table(name = "prestacion", schema = "matera")
public class PrestacionRecord extends UpdatableRecordImpl<PrestacionRecord> implements Record5<Integer, String, BigDecimal, String, Integer> {

	private static final long serialVersionUID = 1112960225;

	/**
	 * Setter for <code>matera.prestacion.id_prestacion</code>.
	 */
	public void setIdPrestacion(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.prestacion.id_prestacion</code>.
	 */
	@Id
	@Column(name = "id_prestacion", unique = true, nullable = false, precision = 10)
	public Integer getIdPrestacion() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.prestacion.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.prestacion.nombre</code>.
	 */
	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.prestacion.importe</code>.
	 */
	public void setImporte(BigDecimal value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.prestacion.importe</code>.
	 */
	@Column(name = "importe", nullable = false, precision = 9, scale = 2)
	public BigDecimal getImporte() {
		return (BigDecimal) getValue(2);
	}

	/**
	 * Setter for <code>matera.prestacion.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.prestacion.habilita</code>.
	 */
	@Column(name = "habilita", nullable = false, length = 1)
	public String getHabilita() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>matera.prestacion.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.prestacion.id_empresa</code>.
	 */
	@Column(name = "id_empresa", nullable = false, precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(4);
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
	public Row5<Integer, String, BigDecimal, String, Integer> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, String, BigDecimal, String, Integer> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Prestacion.PRESTACION.ID_PRESTACION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Prestacion.PRESTACION.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field3() {
		return Prestacion.PRESTACION.IMPORTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Prestacion.PRESTACION.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return Prestacion.PRESTACION.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdPrestacion();
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
	public BigDecimal value3() {
		return getImporte();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getHabilita();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getIdEmpresa();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrestacionRecord value1(Integer value) {
		setIdPrestacion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrestacionRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrestacionRecord value3(BigDecimal value) {
		setImporte(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrestacionRecord value4(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrestacionRecord value5(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrestacionRecord values(Integer value1, String value2, BigDecimal value3, String value4, Integer value5) {
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
	 * Create a detached PrestacionRecord
	 */
	public PrestacionRecord() {
		super(Prestacion.PRESTACION);
	}

	/**
	 * Create a detached, initialised PrestacionRecord
	 */
	public PrestacionRecord(Integer idPrestacion, String nombre, BigDecimal importe, String habilita, Integer idEmpresa) {
		super(Prestacion.PRESTACION);

		setValue(0, idPrestacion);
		setValue(1, nombre);
		setValue(2, importe);
		setValue(3, habilita);
		setValue(4, idEmpresa);
	}
        
        @Override
        public String toString() {
            return getNombre();
        }
}