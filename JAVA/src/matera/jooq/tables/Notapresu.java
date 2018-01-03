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
import matera.jooq.tables.records.NotapresuRecord;

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
public class Notapresu extends TableImpl<NotapresuRecord> {

	private static final long serialVersionUID = 797636630;

	/**
	 * The reference instance of <code>matera.notapresu</code>
	 */
	public static final Notapresu NOTAPRESU = new Notapresu();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<NotapresuRecord> getRecordType() {
		return NotapresuRecord.class;
	}

	/**
	 * The column <code>matera.notapresu.id_notaPresu</code>.
	 */
	public final TableField<NotapresuRecord, Integer> ID_NOTAPRESU = createField("id_notaPresu", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.notapresu.nota</code>.
	 */
	public final TableField<NotapresuRecord, String> NOTA = createField("nota", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>matera.notapresu.id_empresa</code>.
	 */
	public final TableField<NotapresuRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>matera.notapresu.fechaReal</code>.
	 */
	public final TableField<NotapresuRecord, Timestamp> FECHAREAL = createField("fechaReal", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "");

	/**
	 * The column <code>matera.notapresu.usuario</code>.
	 */
	public final TableField<NotapresuRecord, String> USUARIO = createField("usuario", org.jooq.impl.SQLDataType.VARCHAR.length(45).defaulted(true), this, "");

	/**
	 * Create a <code>matera.notapresu</code> table reference
	 */
	public Notapresu() {
		this("notapresu", null);
	}

	/**
	 * Create an aliased <code>matera.notapresu</code> table reference
	 */
	public Notapresu(String alias) {
		this(alias, NOTAPRESU);
	}

	private Notapresu(String alias, Table<NotapresuRecord> aliased) {
		this(alias, aliased, null);
	}

	private Notapresu(String alias, Table<NotapresuRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<NotapresuRecord, Integer> getIdentity() {
		return Keys.IDENTITY_NOTAPRESU;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<NotapresuRecord> getPrimaryKey() {
		return Keys.KEY_NOTAPRESU_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<NotapresuRecord>> getKeys() {
		return Arrays.<UniqueKey<NotapresuRecord>>asList(Keys.KEY_NOTAPRESU_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Notapresu as(String alias) {
		return new Notapresu(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Notapresu rename(String name) {
		return new Notapresu(name, null);
	}
}