/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Tipocomp;

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
@Table(name = "tipocomp", schema = "matera")
public class TipocompRecord extends UpdatableRecordImpl<TipocompRecord> implements Record5<Integer, String, String, String, String> {

	private static final long serialVersionUID = -2100455062;

	/**
	 * Setter for <code>matera.tipocomp.id_tipoComp</code>.
	 */
	public void setIdTipocomp(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.tipocomp.id_tipoComp</code>.
	 */
	@Id
	@Column(name = "id_tipoComp", unique = true, nullable = false, precision = 10)
	public Integer getIdTipocomp() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.tipocomp.codigo</code>.
	 */
	public void setCodigo(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.tipocomp.codigo</code>.
	 */
	@Column(name = "codigo", nullable = false, length = 3)
	public String getCodigo() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.tipocomp.denominacion</code>.
	 */
	public void setDenominacion(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.tipocomp.denominacion</code>.
	 */
	@Column(name = "denominacion", nullable = false, length = 45)
	public String getDenominacion() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.tipocomp.abreviatura</code>.
	 */
	public void setAbreviatura(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.tipocomp.abreviatura</code>.
	 */
	@Column(name = "abreviatura", nullable = false, length = 10)
	public String getAbreviatura() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>matera.tipocomp.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>matera.tipocomp.habilita</code>.
	 */
	@Column(name = "habilita", nullable = false, length = 1)
	public String getHabilita() {
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
	public Row5<Integer, String, String, String, String> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, String, String, String, String> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Tipocomp.TIPOCOMP.ID_TIPOCOMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Tipocomp.TIPOCOMP.CODIGO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Tipocomp.TIPOCOMP.DENOMINACION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Tipocomp.TIPOCOMP.ABREVIATURA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Tipocomp.TIPOCOMP.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdTipocomp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getCodigo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getDenominacion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getAbreviatura();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getHabilita();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipocompRecord value1(Integer value) {
		setIdTipocomp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipocompRecord value2(String value) {
		setCodigo(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipocompRecord value3(String value) {
		setDenominacion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipocompRecord value4(String value) {
		setAbreviatura(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipocompRecord value5(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipocompRecord values(Integer value1, String value2, String value3, String value4, String value5) {
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
	 * Create a detached TipocompRecord
	 */
	public TipocompRecord() {
		super(Tipocomp.TIPOCOMP);
	}

	/**
	 * Create a detached, initialised TipocompRecord
	 */
	public TipocompRecord(Integer idTipocomp, String codigo, String denominacion, String abreviatura, String habilita) {
		super(Tipocomp.TIPOCOMP);

		setValue(0, idTipocomp);
		setValue(1, codigo);
		setValue(2, denominacion);
		setValue(3, abreviatura);
		setValue(4, habilita);
	}
        
        @Override
        public String toString() {
            return getAbreviatura();
        }
}