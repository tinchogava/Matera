/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.CajadepositoRecord;

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
public class Cajadeposito extends TableImpl<CajadepositoRecord> {

	private static final long serialVersionUID = 1846981132;

	/**
	 * The reference instance of <code>matera.cajadeposito</code>
	 */
	public static final Cajadeposito CAJADEPOSITO = new Cajadeposito();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<CajadepositoRecord> getRecordType() {
		return CajadepositoRecord.class;
	}

	/**
	 * The column <code>matera.cajadeposito.id_cajaDeposito</code>.
	 */
	public final TableField<CajadepositoRecord, Integer> ID_CAJADEPOSITO = createField("id_cajaDeposito", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.cajadeposito.id_zona</code>.
	 */
	public final TableField<CajadepositoRecord, Integer> ID_ZONA = createField("id_zona", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.cajadeposito.id_caja</code>.
	 */
	public final TableField<CajadepositoRecord, Integer> ID_CAJA = createField("id_caja", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.cajadeposito.codigo</code>.
	 */
	public final TableField<CajadepositoRecord, String> CODIGO = createField("codigo", org.jooq.impl.SQLDataType.VARCHAR.length(25).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.cajadeposito.habilita</code>.
	 */
	public final TableField<CajadepositoRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.cajadeposito.id_empresa</code>.
	 */
	public final TableField<CajadepositoRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.cajadeposito</code> table reference
	 */
	public Cajadeposito() {
		this("cajadeposito", null);
	}

	/**
	 * Create an aliased <code>matera.cajadeposito</code> table reference
	 */
	public Cajadeposito(String alias) {
		this(alias, CAJADEPOSITO);
	}

	private Cajadeposito(String alias, Table<CajadepositoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Cajadeposito(String alias, Table<CajadepositoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<CajadepositoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_CAJADEPOSITO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<CajadepositoRecord> getPrimaryKey() {
		return Keys.KEY_CAJADEPOSITO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<CajadepositoRecord>> getKeys() {
		return Arrays.<UniqueKey<CajadepositoRecord>>asList(Keys.KEY_CAJADEPOSITO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cajadeposito as(String alias) {
		return new Cajadeposito(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Cajadeposito rename(String name) {
		return new Cajadeposito(name, null);
	}
}
