/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.DepartamentoRecord;

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
public class Departamento extends TableImpl<DepartamentoRecord> {

	private static final long serialVersionUID = 2044975127;

	/**
	 * The reference instance of <code>matera.departamento</code>
	 */
	public static final Departamento DEPARTAMENTO = new Departamento();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DepartamentoRecord> getRecordType() {
		return DepartamentoRecord.class;
	}

	/**
	 * The column <code>matera.departamento.id_departamento</code>.
	 */
	public final TableField<DepartamentoRecord, Integer> ID_DEPARTAMENTO = createField("id_departamento", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.departamento.id_provincia</code>.
	 */
	public final TableField<DepartamentoRecord, Integer> ID_PROVINCIA = createField("id_provincia", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.departamento.nombre</code>.
	 */
	public final TableField<DepartamentoRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.departamento</code> table reference
	 */
	public Departamento() {
		this("departamento", null);
	}

	/**
	 * Create an aliased <code>matera.departamento</code> table reference
	 */
	public Departamento(String alias) {
		this(alias, DEPARTAMENTO);
	}

	private Departamento(String alias, Table<DepartamentoRecord> aliased) {
		this(alias, aliased, null);
	}

	private Departamento(String alias, Table<DepartamentoRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<DepartamentoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_DEPARTAMENTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<DepartamentoRecord> getPrimaryKey() {
		return Keys.KEY_DEPARTAMENTO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<DepartamentoRecord>> getKeys() {
		return Arrays.<UniqueKey<DepartamentoRecord>>asList(Keys.KEY_DEPARTAMENTO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Departamento as(String alias) {
		return new Departamento(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Departamento rename(String name) {
		return new Departamento(name, null);
	}
}