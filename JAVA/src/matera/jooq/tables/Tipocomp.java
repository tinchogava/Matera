/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.TipocompRecord;

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
public class Tipocomp extends TableImpl<TipocompRecord> {

	private static final long serialVersionUID = -1694368449;

	/**
	 * The reference instance of <code>matera.tipocomp</code>
	 */
	public static final Tipocomp TIPOCOMP = new Tipocomp();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TipocompRecord> getRecordType() {
		return TipocompRecord.class;
	}

	/**
	 * The column <code>matera.tipocomp.id_tipoComp</code>.
	 */
	public final TableField<TipocompRecord, Integer> ID_TIPOCOMP = createField("id_tipoComp", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.tipocomp.codigo</code>.
	 */
	public final TableField<TipocompRecord, String> CODIGO = createField("codigo", org.jooq.impl.SQLDataType.VARCHAR.length(3).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.tipocomp.denominacion</code>.
	 */
	public final TableField<TipocompRecord, String> DENOMINACION = createField("denominacion", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.tipocomp.abreviatura</code>.
	 */
	public final TableField<TipocompRecord, String> ABREVIATURA = createField("abreviatura", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.tipocomp.habilita</code>.
	 */
	public final TableField<TipocompRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.tipocomp</code> table reference
	 */
	public Tipocomp() {
		this("tipocomp", null);
	}

	/**
	 * Create an aliased <code>matera.tipocomp</code> table reference
	 */
	public Tipocomp(String alias) {
		this(alias, TIPOCOMP);
	}

	private Tipocomp(String alias, Table<TipocompRecord> aliased) {
		this(alias, aliased, null);
	}

	private Tipocomp(String alias, Table<TipocompRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TipocompRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TIPOCOMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TipocompRecord> getPrimaryKey() {
		return Keys.KEY_TIPOCOMP_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TipocompRecord>> getKeys() {
		return Arrays.<UniqueKey<TipocompRecord>>asList(Keys.KEY_TIPOCOMP_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tipocomp as(String alias) {
		return new Tipocomp(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Tipocomp rename(String name) {
		return new Tipocomp(name, null);
	}
}
