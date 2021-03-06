/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.ClasiproductoRecord;

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
public class Clasiproducto extends TableImpl<ClasiproductoRecord> {

	private static final long serialVersionUID = 6105884;

	/**
	 * The reference instance of <code>matera.clasiproducto</code>
	 */
	public static final Clasiproducto CLASIPRODUCTO = new Clasiproducto();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ClasiproductoRecord> getRecordType() {
		return ClasiproductoRecord.class;
	}

	/**
	 * The column <code>matera.clasiproducto.id_clasiProducto</code>.
	 */
	public final TableField<ClasiproductoRecord, Integer> ID_CLASIPRODUCTO = createField("id_clasiProducto", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.clasiproducto.nombre</code>.
	 */
	public final TableField<ClasiproductoRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.clasiproducto.habilita</code>.
	 */
	public final TableField<ClasiproductoRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.clasiproducto</code> table reference
	 */
	public Clasiproducto() {
		this("clasiproducto", null);
	}

	/**
	 * Create an aliased <code>matera.clasiproducto</code> table reference
	 */
	public Clasiproducto(String alias) {
		this(alias, CLASIPRODUCTO);
	}

	private Clasiproducto(String alias, Table<ClasiproductoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Clasiproducto(String alias, Table<ClasiproductoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ClasiproductoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_CLASIPRODUCTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ClasiproductoRecord> getPrimaryKey() {
		return Keys.KEY_CLASIPRODUCTO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ClasiproductoRecord>> getKeys() {
		return Arrays.<UniqueKey<ClasiproductoRecord>>asList(Keys.KEY_CLASIPRODUCTO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Clasiproducto as(String alias) {
		return new Clasiproducto(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Clasiproducto rename(String name) {
		return new Clasiproducto(name, null);
	}
}
