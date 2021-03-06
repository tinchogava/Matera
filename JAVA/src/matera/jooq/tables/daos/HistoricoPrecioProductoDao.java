/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.daos;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import matera.jooq.tables.HistoricoPrecioProducto;
import matera.jooq.tables.records.HistoricoPrecioProductoRecord;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
@Repository
public class HistoricoPrecioProductoDao extends DAOImpl<HistoricoPrecioProductoRecord, matera.jooq.tables.pojos.HistoricoPrecioProducto, Integer> {

	/**
	 * Create a new HistoricoPrecioProductoDao without any configuration
	 */
	public HistoricoPrecioProductoDao() {
		super(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO, matera.jooq.tables.pojos.HistoricoPrecioProducto.class);
	}

	/**
	 * Create a new HistoricoPrecioProductoDao with an attached configuration
	 */
	@Autowired
	public HistoricoPrecioProductoDao(Configuration configuration) {
		super(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO, matera.jooq.tables.pojos.HistoricoPrecioProducto.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(matera.jooq.tables.pojos.HistoricoPrecioProducto object) {
		return object.getIdHistoricoPrecioProducto();
	}

	/**
	 * Fetch records that have <code>id_historico_precio_producto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.HistoricoPrecioProducto> fetchByIdHistoricoPrecioProducto(Integer... values) {
		return fetch(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.ID_HISTORICO_PRECIO_PRODUCTO, values);
	}

	/**
	 * Fetch a unique record that has <code>id_historico_precio_producto = value</code>
	 */
	public matera.jooq.tables.pojos.HistoricoPrecioProducto fetchOneByIdHistoricoPrecioProducto(Integer value) {
		return fetchOne(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.ID_HISTORICO_PRECIO_PRODUCTO, value);
	}

	/**
	 * Fetch records that have <code>id_producto IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.HistoricoPrecioProducto> fetchByIdProducto(Integer... values) {
		return fetch(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.ID_PRODUCTO, values);
	}

	/**
	 * Fetch records that have <code>precio_anterior IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.HistoricoPrecioProducto> fetchByPrecioAnterior(BigDecimal... values) {
		return fetch(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.PRECIO_ANTERIOR, values);
	}

	/**
	 * Fetch records that have <code>precio_nuevo IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.HistoricoPrecioProducto> fetchByPrecioNuevo(BigDecimal... values) {
		return fetch(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.PRECIO_NUEVO, values);
	}

	/**
	 * Fetch records that have <code>fecha_actualizacion IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.HistoricoPrecioProducto> fetchByFechaActualizacion(Timestamp... values) {
		return fetch(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.FECHA_ACTUALIZACION, values);
	}

	/**
	 * Fetch records that have <code>id_stock IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.HistoricoPrecioProducto> fetchByIdStock(Integer... values) {
		return fetch(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.ID_STOCK, values);
	}

	/**
	 * Fetch records that have <code>id_usuario IN (values)</code>
	 */
	public List<matera.jooq.tables.pojos.HistoricoPrecioProducto> fetchByIdUsuario(UInteger... values) {
		return fetch(HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO.ID_USUARIO, values);
	}
}
