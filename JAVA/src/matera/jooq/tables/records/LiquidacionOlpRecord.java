/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.LiquidacionOlp;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
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
@Table(name = "liquidacion_olp", schema = "matera")
public class LiquidacionOlpRecord extends UpdatableRecordImpl<LiquidacionOlpRecord> implements Record2<Integer, String> {

	private static final long serialVersionUID = -509779869;

	/**
	 * Setter for <code>matera.liquidacion_olp.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.liquidacion_olp.id</code>.
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.liquidacion_olp.olp</code>.
	 */
	public void setOlp(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.liquidacion_olp.olp</code>.
	 */
	@Column(name = "olp", nullable = false, length = 64)
	public String getOlp() {
		return (String) getValue(1);
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
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, String> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return LiquidacionOlp.LIQUIDACION_OLP.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return LiquidacionOlp.LIQUIDACION_OLP.OLP;
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
	public String value2() {
		return getOlp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionOlpRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionOlpRecord value2(String value) {
		setOlp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionOlpRecord values(Integer value1, String value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached LiquidacionOlpRecord
	 */
	public LiquidacionOlpRecord() {
		super(LiquidacionOlp.LIQUIDACION_OLP);
	}

	/**
	 * Create a detached, initialised LiquidacionOlpRecord
	 */
	public LiquidacionOlpRecord(Integer id, String olp) {
		super(LiquidacionOlp.LIQUIDACION_OLP);

		setValue(0, id);
		setValue(1, olp);
	}
}
