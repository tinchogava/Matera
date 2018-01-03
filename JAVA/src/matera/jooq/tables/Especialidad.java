/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.EspecialidadRecord;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Especialidad extends TableImpl<EspecialidadRecord> {

	private static final long serialVersionUID = -619990770;

	/**
	 * The reference instance of <code>matera.especialidad</code>
	 */
	public static final Especialidad ESPECIALIDAD = new Especialidad();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<EspecialidadRecord> getRecordType() {
		return EspecialidadRecord.class;
	}

	/**
	 * The column <code>matera.especialidad.id_especialidad</code>.
	 */
	public final TableField<EspecialidadRecord, Integer> ID_ESPECIALIDAD = createField("id_especialidad", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.especialidad.nombre</code>.
	 */
	public final TableField<EspecialidadRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.especialidad.habilita</code>.
	 */
	public final TableField<EspecialidadRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.especialidad</code> table reference
	 */
	public Especialidad() {
		this("especialidad", null);
	}

	/**
	 * Create an aliased <code>matera.especialidad</code> table reference
	 */
	public Especialidad(String alias) {
		this(alias, ESPECIALIDAD);
	}

	private Especialidad(String alias, Table<EspecialidadRecord> aliased) {
		this(alias, aliased, null);
	}

	private Especialidad(String alias, Table<EspecialidadRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<EspecialidadRecord, Integer> getIdentity() {
		return Keys.IDENTITY_ESPECIALIDAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<EspecialidadRecord> getPrimaryKey() {
		return Keys.KEY_ESPECIALIDAD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<EspecialidadRecord>> getKeys() {
		return Arrays.<UniqueKey<EspecialidadRecord>>asList(Keys.KEY_ESPECIALIDAD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Especialidad as(String alias) {
		return new Especialidad(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Especialidad rename(String name) {
		return new Especialidad(name, null);
	}
}
