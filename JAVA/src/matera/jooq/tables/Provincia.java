/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.ProvinciaRecord;

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
public class Provincia extends TableImpl<ProvinciaRecord> {

	private static final long serialVersionUID = 1883396021;

	/**
	 * The reference instance of <code>matera.provincia</code>
	 */
	public static final Provincia PROVINCIA = new Provincia();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ProvinciaRecord> getRecordType() {
		return ProvinciaRecord.class;
	}

	/**
	 * The column <code>matera.provincia.id_provincia</code>.
	 */
	public final TableField<ProvinciaRecord, Integer> ID_PROVINCIA = createField("id_provincia", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.provincia.nombre</code>.
	 */
	public final TableField<ProvinciaRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.provincia</code> table reference
	 */
	public Provincia() {
		this("provincia", null);
	}

	/**
	 * Create an aliased <code>matera.provincia</code> table reference
	 */
	public Provincia(String alias) {
		this(alias, PROVINCIA);
	}

	private Provincia(String alias, Table<ProvinciaRecord> aliased) {
		this(alias, aliased, null);
	}

	private Provincia(String alias, Table<ProvinciaRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ProvinciaRecord, Integer> getIdentity() {
		return Keys.IDENTITY_PROVINCIA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ProvinciaRecord> getPrimaryKey() {
		return Keys.KEY_PROVINCIA_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ProvinciaRecord>> getKeys() {
		return Arrays.<UniqueKey<ProvinciaRecord>>asList(Keys.KEY_PROVINCIA_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Provincia as(String alias) {
		return new Provincia(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Provincia rename(String name) {
		return new Provincia(name, null);
	}
}