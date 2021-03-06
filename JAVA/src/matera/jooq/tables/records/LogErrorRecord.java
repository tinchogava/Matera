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

import matera.jooq.tables.LogError;

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
@Table(name = "log_error", schema = "matera")
public class LogErrorRecord extends UpdatableRecordImpl<LogErrorRecord> implements Record5<Integer, Integer, String, Timestamp, Byte> {

	private static final long serialVersionUID = -197940464;

	/**
	 * Setter for <code>matera.log_error.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.log_error.id</code>.
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.log_error.id_user</code>.
	 */
	public void setIdUser(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.log_error.id_user</code>.
	 */
	@Column(name = "id_user", nullable = false, precision = 10)
	public Integer getIdUser() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>matera.log_error.error</code>.
	 */
	public void setError(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.log_error.error</code>.
	 */
	@Column(name = "error", nullable = false, length = 65535)
	public String getError() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.log_error.date</code>.
	 */
	public void setDate(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.log_error.date</code>.
	 */
	@Column(name = "date", nullable = false)
	public Timestamp getDate() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>matera.log_error.status</code>.
	 */
	public void setStatus(Byte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.log_error.status</code>.
	 */
	@Column(name = "status", nullable = false, precision = 3)
	public Byte getStatus() {
		return (Byte) getValue(4);
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
	public Row5<Integer, Integer, String, Timestamp, Byte> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, Integer, String, Timestamp, Byte> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return LogError.LOG_ERROR.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return LogError.LOG_ERROR.ID_USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return LogError.LOG_ERROR.ERROR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return LogError.LOG_ERROR.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field5() {
		return LogError.LOG_ERROR.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getIdUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getError();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value5() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogErrorRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogErrorRecord value2(Integer value) {
		setIdUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogErrorRecord value3(String value) {
		setError(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogErrorRecord value4(Timestamp value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogErrorRecord value5(Byte value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogErrorRecord values(Integer value1, Integer value2, String value3, Timestamp value4, Byte value5) {
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
	 * Create a detached LogErrorRecord
	 */
	public LogErrorRecord() {
		super(LogError.LOG_ERROR);
	}

	/**
	 * Create a detached, initialised LogErrorRecord
	 */
	public LogErrorRecord(Integer id, Integer idUser, String error, Timestamp date, Byte status) {
		super(LogError.LOG_ERROR);

		setValue(0, id);
		setValue(1, idUser);
		setValue(2, error);
		setValue(3, date);
		setValue(4, status);
	}
}
