/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.MenuRecord;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


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
public class Menu extends TableImpl<MenuRecord> {

	private static final long serialVersionUID = -435422931;

	/**
	 * The reference instance of <code>matera.menu</code>
	 */
	public static final Menu MENU = new Menu();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MenuRecord> getRecordType() {
		return MenuRecord.class;
	}

	/**
	 * The column <code>matera.menu.id_menu</code>.
	 */
	public final TableField<MenuRecord, UInteger> ID_MENU = createField("id_menu", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>matera.menu.posicion</code>.
	 */
	public final TableField<MenuRecord, String> POSICION = createField("posicion", org.jooq.impl.SQLDataType.VARCHAR.length(5).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.menu.nombre</code>.
	 */
	public final TableField<MenuRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.menu.accion</code>.
	 */
	public final TableField<MenuRecord, Integer> ACCION = createField("accion", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.menu.habilita</code>.
	 */
	public final TableField<MenuRecord, String> HABILITA = createField("habilita", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>matera.menu.id_empresa</code>.
	 */
	public final TableField<MenuRecord, Integer> ID_EMPRESA = createField("id_empresa", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>matera.menu</code> table reference
	 */
	public Menu() {
		this("menu", null);
	}

	/**
	 * Create an aliased <code>matera.menu</code> table reference
	 */
	public Menu(String alias) {
		this(alias, MENU);
	}

	private Menu(String alias, Table<MenuRecord> aliased) {
		this(alias, aliased, null);
	}

	private Menu(String alias, Table<MenuRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<MenuRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_MENU;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<MenuRecord> getPrimaryKey() {
		return Keys.KEY_MENU_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<MenuRecord>> getKeys() {
		return Arrays.<UniqueKey<MenuRecord>>asList(Keys.KEY_MENU_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Menu as(String alias) {
		return new Menu(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Menu rename(String name) {
		return new Menu(name, null);
	}
}
