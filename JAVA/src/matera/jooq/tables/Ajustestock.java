/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.AjustestockRecord;

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
public class Ajustestock extends TableImpl<AjustestockRecord> {

	private static final long serialVersionUID = 359651400;

	/**
	 * The reference instance of <code>matera.ajustestock</code>
	 */
	public static final Ajustestock AJUSTESTOCK = new Ajustestock();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AjustestockRecord> getRecordType() {
		return AjustestockRecord.class;
	}

	/**
	 * The column <code>matera.ajustestock.id_ajusteStock</code>.
	 */
	public final TableField<AjustestockRecord, Integer> ID_AJUSTESTOCK = createField("id_ajusteStock", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.ajustestock.id_zona</code>.
	 */
	public final TableField<AjustestockRecord, Integer> ID_ZONA = createField("id_zona", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.ajustestock.fecha</code>.
	 */
	public final TableField<AjustestockRecord, Date> FECHA = createField("fecha", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>matera.ajustestock.id_empresa</code>.
	 */
	public final TableField<AjustestockRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.ajustestock.usuario</code>.
	 */
	public final TableField<AjustestockRecord, String> USUARIO = createField("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * The column <code>matera.ajustestock.fechaReal</code>.
	 */
	public final TableField<AjustestockRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * Create a <code>matera.ajustestock</code> table reference
	 */
	public Ajustestock() {
		this("ajustestock", null);
	}

	/**
	 * Create an aliased <code>matera.ajustestock</code> table reference
	 */
	public Ajustestock(String alias) {
		this(alias, AJUSTESTOCK);
	}

	private Ajustestock(String alias, Table<AjustestockRecord> aliased) {
		this(alias, aliased, null);
	}

	private Ajustestock(String alias, Table<AjustestockRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<AjustestockRecord, Integer> getIdentity() {
		return Keys.IDENTITY_AJUSTESTOCK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AjustestockRecord> getPrimaryKey() {
		return Keys.KEY_AJUSTESTOCK_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AjustestockRecord>> getKeys() {
		return Arrays.<UniqueKey<AjustestockRecord>>asList(Keys.KEY_AJUSTESTOCK_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ajustestock as(String alias) {
		return new Ajustestock(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Ajustestock rename(String name) {
		return new Ajustestock(name, null);
	}
}
