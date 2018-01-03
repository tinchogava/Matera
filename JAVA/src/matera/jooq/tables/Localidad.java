/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.LocalidadRecord;

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
public class Localidad extends TableImpl<LocalidadRecord> {

	private static final long serialVersionUID = -1742652159;

	/**
	 * The reference instance of <code>matera.localidad</code>
	 */
	public static final Localidad LOCALIDAD = new Localidad();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<LocalidadRecord> getRecordType() {
		return LocalidadRecord.class;
	}

	/**
	 * The column <code>matera.localidad.id_localidad</code>.
	 */
	public final TableField<LocalidadRecord, Integer> ID_LOCALIDAD = createField("id_localidad", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.localidad.id_departamento</code>.
	 */
	public final TableField<LocalidadRecord, Integer> ID_DEPARTAMENTO = createField("id_departamento", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.localidad.nombre</code>.
	 */
	public final TableField<LocalidadRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.localidad</code> table reference
	 */
	public Localidad() {
		this("localidad", null);
	}

	/**
	 * Create an aliased <code>matera.localidad</code> table reference
	 */
	public Localidad(String alias) {
		this(alias, LOCALIDAD);
	}

	private Localidad(String alias, Table<LocalidadRecord> aliased) {
		this(alias, aliased, null);
	}

	private Localidad(String alias, Table<LocalidadRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<LocalidadRecord, Integer> getIdentity() {
		return Keys.IDENTITY_LOCALIDAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<LocalidadRecord> getPrimaryKey() {
		return Keys.KEY_LOCALIDAD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<LocalidadRecord>> getKeys() {
		return Arrays.<UniqueKey<LocalidadRecord>>asList(Keys.KEY_LOCALIDAD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Localidad as(String alias) {
		return new Localidad(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Localidad rename(String name) {
		return new Localidad(name, null);
	}
}
