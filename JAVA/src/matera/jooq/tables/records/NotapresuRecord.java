/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Notapresu;

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
@Table(name = "notapresu", schema = "matera")
public class NotapresuRecord extends UpdatableRecordImpl<NotapresuRecord> implements Record5<Integer, String, Integer, Timestamp, String> {

	private static final long serialVersionUID = 700183585;

	/**
	 * Setter for <code>matera.notapresu.id_notaPresu</code>.
	 */
	public void setIdNotapresu(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.notapresu.id_notaPresu</code>.
	 */
	@Id
	@Column(name = "id_notaPresu", unique = true, nullable = false, precision = 10)
	public Integer getIdNotapresu() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.notapresu.nota</code>.
	 */
	public void setNota(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.notapresu.nota</code>.
	 */
	@Column(name = "nota", length = 65535)
	public String getNota() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.notapresu.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.notapresu.id_empresa</code>.
	 */
	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>matera.notapresu.fechaReal</code>.
	 */
	public void setFechareal(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.notapresu.fechaReal</code>.
	 */
	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>matera.notapresu.usuario</code>.
	 */
	public void setUsuario(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.notapresu.usuario</code>.
	 */
	@Column(name = "usuario", length = 45)
	public String getUsuario() {
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
	public Row5<Integer, String, Integer, Timestamp, String> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, String, Integer, Timestamp, String> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Notapresu.NOTAPRESU.ID_NOTAPRESU;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Notapresu.NOTAPRESU.NOTA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Notapresu.NOTAPRESU.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return Notapresu.NOTAPRESU.FECHAREAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Notapresu.NOTAPRESU.USUARIO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdNotapresu();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getNota();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getIdEmpresa();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getFechareal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NotapresuRecord value1(Integer value) {
		setIdNotapresu(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NotapresuRecord value2(String value) {
		setNota(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NotapresuRecord value3(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NotapresuRecord value4(Timestamp value) {
		setFechareal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NotapresuRecord value5(String value) {
		setUsuario(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NotapresuRecord values(Integer value1, String value2, Integer value3, Timestamp value4, String value5) {
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
	 * Create a detached NotapresuRecord
	 */
	public NotapresuRecord() {
		super(Notapresu.NOTAPRESU);
	}

	/**
	 * Create a detached, initialised NotapresuRecord
	 */
	public NotapresuRecord(Integer idNotapresu, String nota, Integer idEmpresa, Timestamp fechareal, String usuario) {
		super(Notapresu.NOTAPRESU);

		setValue(0, idNotapresu);
		setValue(1, nota);
		setValue(2, idEmpresa);
		setValue(3, fechareal);
		setValue(4, usuario);
	}
}