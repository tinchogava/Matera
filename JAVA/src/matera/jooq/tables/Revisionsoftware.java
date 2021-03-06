/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.RevisionsoftwareRecord;

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
public class Revisionsoftware extends TableImpl<RevisionsoftwareRecord> {

	private static final long serialVersionUID = -631407834;

	/**
	 * The reference instance of <code>matera.revisionsoftware</code>
	 */
	public static final Revisionsoftware REVISIONSOFTWARE = new Revisionsoftware();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<RevisionsoftwareRecord> getRecordType() {
		return RevisionsoftwareRecord.class;
	}

	/**
	 * The column <code>matera.revisionsoftware.id_revisionSoftware</code>.
	 */
	public final TableField<RevisionsoftwareRecord, Integer> ID_REVISIONSOFTWARE = createField("id_revisionSoftware", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.revisionsoftware.detalleRevision</code>.
	 */
	public final TableField<RevisionsoftwareRecord, String> DETALLEREVISION = createField("detalleRevision", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>matera.revisionsoftware.fechaReal</code>.
	 */
	public final TableField<RevisionsoftwareRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * The column <code>matera.revisionsoftware.lanzada</code>.
	 */
	public final TableField<RevisionsoftwareRecord, String> LANZADA = createField("lanzada", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.revisionsoftware.critica</code>.
	 */
	public final TableField<RevisionsoftwareRecord, String> CRITICA = createField("critica", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.revisionsoftware.link</code>.
	 */
	public final TableField<RevisionsoftwareRecord, String> LINK = createField("link", org.jooq.impl.SQLDataType.VARCHAR.length(200).defaulted(true), this, "");

	/**
	 * Create a <code>matera.revisionsoftware</code> table reference
	 */
	public Revisionsoftware() {
		this("revisionsoftware", null);
	}

	/**
	 * Create an aliased <code>matera.revisionsoftware</code> table reference
	 */
	public Revisionsoftware(String alias) {
		this(alias, REVISIONSOFTWARE);
	}

	private Revisionsoftware(String alias, Table<RevisionsoftwareRecord> aliased) {
		this(alias, aliased, null);
	}

	private Revisionsoftware(String alias, Table<RevisionsoftwareRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<RevisionsoftwareRecord, Integer> getIdentity() {
		return Keys.IDENTITY_REVISIONSOFTWARE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<RevisionsoftwareRecord> getPrimaryKey() {
		return Keys.KEY_REVISIONSOFTWARE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<RevisionsoftwareRecord>> getKeys() {
		return Arrays.<UniqueKey<RevisionsoftwareRecord>>asList(Keys.KEY_REVISIONSOFTWARE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Revisionsoftware as(String alias) {
		return new Revisionsoftware(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Revisionsoftware rename(String name) {
		return new Revisionsoftware(name, null);
	}
}
