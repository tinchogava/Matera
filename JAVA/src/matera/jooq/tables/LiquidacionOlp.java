/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.LiquidacionOlpRecord;

import org.jooq.Field;
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
public class LiquidacionOlp extends TableImpl<LiquidacionOlpRecord> {

	private static final long serialVersionUID = 906178778;

	/**
	 * The reference instance of <code>matera.liquidacion_olp</code>
	 */
	public static final LiquidacionOlp LIQUIDACION_OLP = new LiquidacionOlp();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<LiquidacionOlpRecord> getRecordType() {
		return LiquidacionOlpRecord.class;
	}

	/**
	 * The column <code>matera.liquidacion_olp.id</code>.
	 */
	public final TableField<LiquidacionOlpRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.liquidacion_olp.olp</code>.
	 */
	public final TableField<LiquidacionOlpRecord, String> OLP = createField("olp", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * Create a <code>matera.liquidacion_olp</code> table reference
	 */
	public LiquidacionOlp() {
		this("liquidacion_olp", null);
	}

	/**
	 * Create an aliased <code>matera.liquidacion_olp</code> table reference
	 */
	public LiquidacionOlp(String alias) {
		this(alias, LIQUIDACION_OLP);
	}

	private LiquidacionOlp(String alias, Table<LiquidacionOlpRecord> aliased) {
		this(alias, aliased, null);
	}

	private LiquidacionOlp(String alias, Table<LiquidacionOlpRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<LiquidacionOlpRecord> getPrimaryKey() {
		return Keys.KEY_LIQUIDACION_OLP_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<LiquidacionOlpRecord>> getKeys() {
		return Arrays.<UniqueKey<LiquidacionOlpRecord>>asList(Keys.KEY_LIQUIDACION_OLP_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LiquidacionOlp as(String alias) {
		return new LiquidacionOlp(alias, this);
	}

	/**
	 * Rename this table
	 */
	public LiquidacionOlp rename(String name) {
		return new LiquidacionOlp(name, null);
	}
}
