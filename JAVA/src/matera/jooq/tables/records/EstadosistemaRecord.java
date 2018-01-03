/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Estadosistema;

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
@Table(name = "estadosistema", schema = "matera")
public class EstadosistemaRecord extends UpdatableRecordImpl<EstadosistemaRecord> implements Record3<Integer, String, String> {

	private static final long serialVersionUID = -2021987033;

	/**
	 * Setter for <code>matera.estadosistema.id_estadoSistema</code>.
	 */
	public void setIdEstadosistema(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.estadosistema.id_estadoSistema</code>.
	 */
	@Id
	@Column(name = "id_estadoSistema", unique = true, nullable = false, precision = 10)
	public Integer getIdEstadosistema() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.estadosistema.estado</code>.
	 */
	public void setEstado(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.estadosistema.estado</code>.
	 */
	@Column(name = "estado", length = 45)
	public String getEstado() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.estadosistema.enProceso</code>.
	 */
	public void setEnproceso(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.estadosistema.enProceso</code>.
	 */
	@Column(name = "enProceso", length = 1)
	public String getEnproceso() {
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
		return Estadosistema.ESTADOSISTEMA.ID_ESTADOSISTEMA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Estadosistema.ESTADOSISTEMA.ESTADO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Estadosistema.ESTADOSISTEMA.ENPROCESO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdEstadosistema();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getEstado();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getEnproceso();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EstadosistemaRecord value1(Integer value) {
		setIdEstadosistema(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EstadosistemaRecord value2(String value) {
		setEstado(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EstadosistemaRecord value3(String value) {
		setEnproceso(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EstadosistemaRecord values(Integer value1, String value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached EstadosistemaRecord
	 */
	public EstadosistemaRecord() {
		super(Estadosistema.ESTADOSISTEMA);
	}

	/**
	 * Create a detached, initialised EstadosistemaRecord
	 */
	public EstadosistemaRecord(Integer idEstadosistema, String estado, String enproceso) {
		super(Estadosistema.ESTADOSISTEMA);

		setValue(0, idEstadosistema);
		setValue(1, estado);
		setValue(2, enproceso);
	}
}