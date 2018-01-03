/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.Keys;
import matera.jooq.Matera;
import matera.jooq.tables.records.EmailreciptersRecord;

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
public class Emailrecipters extends TableImpl<EmailreciptersRecord> {

	private static final long serialVersionUID = 2096055246;

	/**
	 * The reference instance of <code>matera.emailrecipters</code>
	 */
	public static final Emailrecipters EMAILRECIPTERS = new Emailrecipters();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<EmailreciptersRecord> getRecordType() {
		return EmailreciptersRecord.class;
	}

	/**
	 * The column <code>matera.emailrecipters.idEmailRecipters</code>.
	 */
	public final TableField<EmailreciptersRecord, Integer> IDEMAILRECIPTERS = createField("idEmailRecipters", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.emailrecipters.idModulo</code>.
	 */
	public final TableField<EmailreciptersRecord, Integer> IDMODULO = createField("idModulo", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>matera.emailrecipters.id_usuario</code>.
	 */
	public final TableField<EmailreciptersRecord, UInteger> IDUSUARIO = createField("idUsuario", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>matera.emailrecipters.emailAddress</code>.
	 */
	public final TableField<EmailreciptersRecord, String> EMAILADDRESS = createField("emailAddress", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>matera.emailrecipters.enabled</code>.
	 */
	public final TableField<EmailreciptersRecord, Integer> ENABLED = createField("enabled", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

        /**
	 * The column <code>matera.emailrecipters.idZona</code>.
	 */
	public final TableField<EmailreciptersRecord, Integer> IDZONA = createField("idZona", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");
        
	/**
	 * Create a <code>matera.emailrecipters</code> table reference
	 */
	public Emailrecipters() {
		this("emailrecipters", null);
	}

	/**
	 * Create an aliased <code>matera.emailrecipters</code> table reference
	 */
	public Emailrecipters(String alias) {
		this(alias, EMAILRECIPTERS);
	}

	private Emailrecipters(String alias, Table<EmailreciptersRecord> aliased) {
		this(alias, aliased, null);
	}

	private Emailrecipters(String alias, Table<EmailreciptersRecord> aliased, Field<?>[] parameters) {
		super(alias, Matera.MATERA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<EmailreciptersRecord, Integer> getIdentity() {
		return Keys.IDENTITY_EMAILRECIPTERS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<EmailreciptersRecord> getPrimaryKey() {
		return Keys.KEY_EMAILRECIPTERS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<EmailreciptersRecord>> getKeys() {
		return Arrays.<UniqueKey<EmailreciptersRecord>>asList(Keys.KEY_EMAILRECIPTERS_PRIMARY, Keys.KEY_EMAILRECIPTERS_UNIQUE_IDEMAILRECIPTERS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Emailrecipters as(String alias) {
		return new Emailrecipters(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Emailrecipters rename(String name) {
		return new Emailrecipters(name, null);
	}
}