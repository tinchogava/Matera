/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import matera.jooq.tables.Grupoporcentaje;

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
@Table(name = "grupoporcentaje", schema = "matera")
public class GrupoporcentajeRecord extends UpdatableRecordImpl<GrupoporcentajeRecord> implements Record4<Integer, String, String, Integer> {

	private static final long serialVersionUID = -73570253;

	/**
	 * Setter for <code>matera.grupoporcentaje.id_grupoPorcentaje</code>.
	 */
	public void setIdGrupoporcentaje(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>matera.grupoporcentaje.id_grupoPorcentaje</code>.
	 */
	@Id
	@Column(name = "id_grupoPorcentaje", unique = true, nullable = false, precision = 10)
	public Integer getIdGrupoporcentaje() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>matera.grupoporcentaje.nombre</code>.
	 */
	public void setNombre(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>matera.grupoporcentaje.nombre</code>.
	 */
	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>matera.grupoporcentaje.habilita</code>.
	 */
	public void setHabilita(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>matera.grupoporcentaje.habilita</code>.
	 */
	@Column(name = "habilita", length = 1)
	public String getHabilita() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>matera.grupoporcentaje.id_empresa</code>.
	 */
	public void setIdEmpresa(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>matera.grupoporcentaje.id_empresa</code>.
	 */
	@Column(name = "id_empresa", precision = 10)
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
		return Grupoporcentaje.GRUPOPORCENTAJE.ID_GRUPOPORCENTAJE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Grupoporcentaje.GRUPOPORCENTAJE.NOMBRE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Grupoporcentaje.GRUPOPORCENTAJE.HABILITA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Grupoporcentaje.GRUPOPORCENTAJE.ID_EMPRESA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getIdGrupoporcentaje();
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
	public GrupoporcentajeRecord value1(Integer value) {
		setIdGrupoporcentaje(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GrupoporcentajeRecord value2(String value) {
		setNombre(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GrupoporcentajeRecord value3(String value) {
		setHabilita(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GrupoporcentajeRecord value4(Integer value) {
		setIdEmpresa(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GrupoporcentajeRecord values(Integer value1, String value2, String value3, Integer value4) {
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
	 * Create a detached GrupoporcentajeRecord
	 */
	public GrupoporcentajeRecord() {
		super(Grupoporcentaje.GRUPOPORCENTAJE);
	}

	/**
	 * Create a detached, initialised GrupoporcentajeRecord
	 */
	public GrupoporcentajeRecord(Integer idGrupoporcentaje, String nombre, String habilita, Integer idEmpresa) {
		super(Grupoporcentaje.GRUPOPORCENTAJE);

		setValue(0, idGrupoporcentaje);
		setValue(1, nombre);
		setValue(2, habilita);
		setValue(3, idEmpresa);
	}
}
