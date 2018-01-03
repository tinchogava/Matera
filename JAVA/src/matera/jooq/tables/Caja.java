/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.CajaRecord;

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
public class Caja extends TableImpl<CajaRecord> {

	private static final long serialVersionUID = -1273189537;

	/**
	 * The reference instance of <code>matera.caja</code>
	 */
	public static final Caja CAJA = new Caja();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<CajaRecord> getRecordType() {
		return CajaRecord.class;
	}

	/**
	 * The column <code>matera.caja.id_caja</code>.
	 */
	public final TableField<CajaRecord, Integer> ID_CAJA = createField("id_caja", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.caja.nombre</code>.
	 */
	public final TableField<CajaRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.caja.habilita</code>.
	 */
	public final TableField<CajaRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.caja</code> table reference
	 */
	public Caja() {
		this("caja", null);
	}

	/**
	 * Create an aliased <code>matera.caja</code> table reference
	 */
	public Caja(String alias) {
		this(alias, CAJA);
	}

	private Caja(String alias, Table<CajaRecord> aliased) {
		this(alias, aliased, null);
	}

	private Caja(String alias, Table<CajaRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<CajaRecord, Integer> getIdentity() {
		return Keys.IDENTITY_CAJA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<CajaRecord> getPrimaryKey() {
		return Keys.KEY_CAJA_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<CajaRecord>> getKeys() {
		return Arrays.<UniqueKey<CajaRecord>>asList(Keys.KEY_CAJA_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Caja as(String alias) {
		return new Caja(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Caja rename(String name) {
		return new Caja(name, null);
	}
}