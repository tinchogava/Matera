/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.MonedaRecord;

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
public class Moneda extends TableImpl<MonedaRecord> {

	private static final long serialVersionUID = 1072392218;

	/**
	 * The reference instance of <code>matera.moneda</code>
	 */
	public static final Moneda MONEDA = new Moneda();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MonedaRecord> getRecordType() {
		return MonedaRecord.class;
	}

	/**
	 * The column <code>matera.moneda.id_moneda</code>.
	 */
	public final TableField<MonedaRecord, Integer> ID_MONEDA = createField("id_moneda", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.moneda.codigo</code>.
	 */
	public final TableField<MonedaRecord, String> CODIGO = createField("codigo", org.jooq.impl.SQLDataType.VARCHAR.length(3).defaulted(true), this, "");

	/**
	 * The column <code>matera.moneda.nombre</code>.
	 */
	public final TableField<MonedaRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.moneda.simbolo</code>.
	 */
	public final TableField<MonedaRecord, String> SIMBOLO = createField("simbolo", org.jooq.impl.SQLDataType.VARCHAR.length(3).defaulted(true), this, "");

	/**
	 * The column <code>matera.moneda.habilita</code>.
	 */
	public final TableField<MonedaRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).defaulted(true), this, "");

	/**
	 * The column <code>matera.moneda.cotizacion</code>.
	 */
	public final TableField<MonedaRecord, BigDecimal> COTIZACION = createField("cotizacion", org.jooq.impl.SQLDataType.DECIMAL.precision(12, 3).defaulted(true), this, "");

	/**
	 * Create a <code>matera.moneda</code> table reference
	 */
	public Moneda() {
		this("moneda", null);
	}

	/**
	 * Create an aliased <code>matera.moneda</code> table reference
	 */
	public Moneda(String alias) {
		this(alias, MONEDA);
	}

	private Moneda(String alias, Table<MonedaRecord> aliased) {
		this(alias, aliased, null);
	}

	private Moneda(String alias, Table<MonedaRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<MonedaRecord, Integer> getIdentity() {
		return Keys.IDENTITY_MONEDA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<MonedaRecord> getPrimaryKey() {
		return Keys.KEY_MONEDA_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<MonedaRecord>> getKeys() {
		return Arrays.<UniqueKey<MonedaRecord>>asList(Keys.KEY_MONEDA_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Moneda as(String alias) {
		return new Moneda(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Moneda rename(String name) {
		return new Moneda(name, null);
	}
}
