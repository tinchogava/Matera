/**
 * This class is generated by jOOQ
 */
package matera.jooq;


import javax.annotation.Generated;

import matera.jooq.tables.Acceso;
import matera.jooq.tables.Ajustestock;
import matera.jooq.tables.Atencioncliente;
import matera.jooq.tables.Caja;
import matera.jooq.tables.Cajaasignada;
import matera.jooq.tables.Cajacomposicion;
import matera.jooq.tables.Cajadeposito;
import matera.jooq.tables.Clasientidad;
import matera.jooq.tables.Clasiproducto;
import matera.jooq.tables.Codconsumo;
import matera.jooq.tables.Cotizacion;
import matera.jooq.tables.Departamento;
import matera.jooq.tables.Diaria;
import matera.jooq.tables.Emailrecipters;
import matera.jooq.tables.Empresa;
import matera.jooq.tables.Encargadocompras;
import matera.jooq.tables.Encargadocontable;
import matera.jooq.tables.Encargadodeposito;
import matera.jooq.tables.Encargadofarmacia;
import matera.jooq.tables.Encargadopago;
import matera.jooq.tables.Encargadotesoreria;
import matera.jooq.tables.Entidad;
import matera.jooq.tables.Especialidad;
import matera.jooq.tables.Estadosistema;
import matera.jooq.tables.Estrategia;
import matera.jooq.tables.Estrategiaentidad;
import matera.jooq.tables.Factura;
import matera.jooq.tables.Forecast;
import matera.jooq.tables.Forecastcomposiciongrupo;
import matera.jooq.tables.Forecastentidad;
import matera.jooq.tables.Forecastgrupo;
import matera.jooq.tables.Formapago;
import matera.jooq.tables.Grupoporcentaje;
import matera.jooq.tables.HistoricoPrecioProducto;
import matera.jooq.tables.Intersucursal;
import matera.jooq.tables.Intersucursalcaja;
import matera.jooq.tables.Liquidacion;
import matera.jooq.tables.LiquidacionOlp;
import matera.jooq.tables.Liquidaciondetalle;
import matera.jooq.tables.Localidad;
import matera.jooq.tables.LogError;
import matera.jooq.tables.LogEventoPresupuesto;
import matera.jooq.tables.LogEventoRemito;
import matera.jooq.tables.LogEventoTipo;
import matera.jooq.tables.Logsistema;
import matera.jooq.tables.Mantenimiento;
import matera.jooq.tables.MarchaNegocios;
import matera.jooq.tables.MarchaNegociosDetail;
import matera.jooq.tables.Mayorprofesional;
import matera.jooq.tables.Mayortecnico;
import matera.jooq.tables.Mayorvendedor;
import matera.jooq.tables.Menu;
import matera.jooq.tables.Moneda;
import matera.jooq.tables.Motivo;
import matera.jooq.tables.Nivelescertificado;
import matera.jooq.tables.Notapresu;
import matera.jooq.tables.Numeracionliquidaciones;
import matera.jooq.tables.Opcion;
import matera.jooq.tables.Plazo;
import matera.jooq.tables.Porcentaje;
import matera.jooq.tables.Prestacion;
import matera.jooq.tables.Presupuesto;
import matera.jooq.tables.PresupuestoControlfacturacion;
import matera.jooq.tables.Presupuestoprod;
import matera.jooq.tables.Producto;
import matera.jooq.tables.Productofact;
import matera.jooq.tables.Profesional;
import matera.jooq.tables.Proveedor;
import matera.jooq.tables.Provincia;
import matera.jooq.tables.Reclamo;
import matera.jooq.tables.Reclamoexterno;
import matera.jooq.tables.Reclamointerno;
import matera.jooq.tables.Remito;
import matera.jooq.tables.RemitoConsumido;
import matera.jooq.tables.Revisionsoftware;
import matera.jooq.tables.Stock;
import matera.jooq.tables.Stockdetalle;
import matera.jooq.tables.Subespecialidad;
import matera.jooq.tables.Tecnico;
import matera.jooq.tables.Tipocomp;
import matera.jooq.tables.Tipooperacion;
import matera.jooq.tables.Trackingolp;
import matera.jooq.tables.Usuario;
import matera.jooq.tables.Vendedor;
import matera.jooq.tables.Zona;



/**
 * Convenience access to all tables in matera
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * The table matera.acceso
	 */
	public static final Acceso ACCESO = matera.jooq.tables.Acceso.ACCESO;

	/**
	 * The table matera.ajustestock
	 */
	public static final Ajustestock AJUSTESTOCK = matera.jooq.tables.Ajustestock.AJUSTESTOCK;

	/**
	 * The table matera.atencioncliente
	 */
	public static final Atencioncliente ATENCIONCLIENTE = matera.jooq.tables.Atencioncliente.ATENCIONCLIENTE;

	/**
	 * The table matera.caja
	 */
	public static final Caja CAJA = matera.jooq.tables.Caja.CAJA;

	/**
	 * The table matera.cajaasignada
	 */
	public static final Cajaasignada CAJAASIGNADA = matera.jooq.tables.Cajaasignada.CAJAASIGNADA;

	/**
	 * The table matera.cajacomposicion
	 */
	public static final Cajacomposicion CAJACOMPOSICION = matera.jooq.tables.Cajacomposicion.CAJACOMPOSICION;

	/**
	 * The table matera.cajadeposito
	 */
	public static final Cajadeposito CAJADEPOSITO = matera.jooq.tables.Cajadeposito.CAJADEPOSITO;

	/**
	 * The table matera.clasientidad
	 */
	public static final Clasientidad CLASIENTIDAD = matera.jooq.tables.Clasientidad.CLASIENTIDAD;

	/**
	 * The table matera.clasiproducto
	 */
	public static final Clasiproducto CLASIPRODUCTO = matera.jooq.tables.Clasiproducto.CLASIPRODUCTO;

	/**
	 * The table matera.codconsumo
	 */
	public static final Codconsumo CODCONSUMO = matera.jooq.tables.Codconsumo.CODCONSUMO;

	/**
	 * The table matera.cotizacion
	 */
	public static final Cotizacion COTIZACION = matera.jooq.tables.Cotizacion.COTIZACION;

	/**
	 * The table matera.departamento
	 */
	public static final Departamento DEPARTAMENTO = matera.jooq.tables.Departamento.DEPARTAMENTO;

	/**
	 * The table matera.diaria
	 */
	public static final Diaria DIARIA = matera.jooq.tables.Diaria.DIARIA;
        
        /**
	 * The table matera.emailrecipters
	 */
	public static final Emailrecipters EMAILRECIPTERS = matera.jooq.tables.Emailrecipters.EMAILRECIPTERS;


	/**
	 * The table matera.empresa
	 */
	public static final Empresa EMPRESA = matera.jooq.tables.Empresa.EMPRESA;

	/**
	 * The table matera.encargadocompras
	 */
	public static final Encargadocompras ENCARGADOCOMPRAS = matera.jooq.tables.Encargadocompras.ENCARGADOCOMPRAS;

	/**
	 * The table matera.encargadocontable
	 */
	public static final Encargadocontable ENCARGADOCONTABLE = matera.jooq.tables.Encargadocontable.ENCARGADOCONTABLE;

	/**
	 * The table matera.encargadodeposito
	 */
	public static final Encargadodeposito ENCARGADODEPOSITO = matera.jooq.tables.Encargadodeposito.ENCARGADODEPOSITO;

	/**
	 * The table matera.encargadofarmacia
	 */
	public static final Encargadofarmacia ENCARGADOFARMACIA = matera.jooq.tables.Encargadofarmacia.ENCARGADOFARMACIA;

	/**
	 * The table matera.encargadopago
	 */
	public static final Encargadopago ENCARGADOPAGO = matera.jooq.tables.Encargadopago.ENCARGADOPAGO;

	/**
	 * The table matera.encargadotesoreria
	 */
	public static final Encargadotesoreria ENCARGADOTESORERIA = matera.jooq.tables.Encargadotesoreria.ENCARGADOTESORERIA;

	/**
	 * The table matera.entidad
	 */
	public static final Entidad ENTIDAD = matera.jooq.tables.Entidad.ENTIDAD;

	/**
	 * The table matera.especialidad
	 */
	public static final Especialidad ESPECIALIDAD = matera.jooq.tables.Especialidad.ESPECIALIDAD;

	/**
	 * The table matera.estadosistema
	 */
	public static final Estadosistema ESTADOSISTEMA = matera.jooq.tables.Estadosistema.ESTADOSISTEMA;

	/**
	 * The table matera.estrategia
	 */
	public static final Estrategia ESTRATEGIA = matera.jooq.tables.Estrategia.ESTRATEGIA;

	/**
	 * The table matera.estrategiaentidad
	 */
	public static final Estrategiaentidad ESTRATEGIAENTIDAD = matera.jooq.tables.Estrategiaentidad.ESTRATEGIAENTIDAD;

	/**
	 * The table matera.factura
	 */
	public static final Factura FACTURA = matera.jooq.tables.Factura.FACTURA;

	/**
	 * The table matera.forecast
	 */
	public static final Forecast FORECAST = matera.jooq.tables.Forecast.FORECAST;

	/**
	 * The table matera.forecastcomposiciongrupo
	 */
	public static final Forecastcomposiciongrupo FORECASTCOMPOSICIONGRUPO = matera.jooq.tables.Forecastcomposiciongrupo.FORECASTCOMPOSICIONGRUPO;

	/**
	 * The table matera.forecastentidad
	 */
	public static final Forecastentidad FORECASTENTIDAD = matera.jooq.tables.Forecastentidad.FORECASTENTIDAD;

	/**
	 * The table matera.forecastgrupo
	 */
	public static final Forecastgrupo FORECASTGRUPO = matera.jooq.tables.Forecastgrupo.FORECASTGRUPO;

	/**
	 * The table matera.formapago
	 */
	public static final Formapago FORMAPAGO = matera.jooq.tables.Formapago.FORMAPAGO;

	/**
	 * The table matera.grupoporcentaje
	 */
	public static final Grupoporcentaje GRUPOPORCENTAJE = matera.jooq.tables.Grupoporcentaje.GRUPOPORCENTAJE;

	/**
	 * The table matera.historico_precio_producto
	 */
	public static final HistoricoPrecioProducto HISTORICO_PRECIO_PRODUCTO = matera.jooq.tables.HistoricoPrecioProducto.HISTORICO_PRECIO_PRODUCTO;

	/**
	 * The table matera.intersucursal
	 */
	public static final Intersucursal INTERSUCURSAL = matera.jooq.tables.Intersucursal.INTERSUCURSAL;

	/**
	 * The table matera.intersucursalcaja
	 */
	public static final Intersucursalcaja INTERSUCURSALCAJA = matera.jooq.tables.Intersucursalcaja.INTERSUCURSALCAJA;

	/**
	 * The table matera.liquidacion
	 */
	public static final Liquidacion LIQUIDACION = matera.jooq.tables.Liquidacion.LIQUIDACION;

	/**
	 * The table matera.liquidaciondetalle
	 */
	public static final Liquidaciondetalle LIQUIDACIONDETALLE = matera.jooq.tables.Liquidaciondetalle.LIQUIDACIONDETALLE;

	/**
	 * The table matera.liquidacion_olp
	 */
	public static final LiquidacionOlp LIQUIDACION_OLP = matera.jooq.tables.LiquidacionOlp.LIQUIDACION_OLP;

	/**
	 * The table matera.localidad
	 */
	public static final Localidad LOCALIDAD = matera.jooq.tables.Localidad.LOCALIDAD;

	/**
	 * The table matera.logsistema
	 */
	public static final Logsistema LOGSISTEMA = matera.jooq.tables.Logsistema.LOGSISTEMA;

	/**
	 * The table matera.log_error
	 */
	public static final LogError LOG_ERROR = matera.jooq.tables.LogError.LOG_ERROR;

	/**
	 * The table matera.log_evento_presupuesto
	 */
	public static final LogEventoPresupuesto LOG_EVENTO_PRESUPUESTO = matera.jooq.tables.LogEventoPresupuesto.LOG_EVENTO_PRESUPUESTO;

	/**
	 * The table matera.log_evento_remito
	 */
	public static final LogEventoRemito LOG_EVENTO_REMITO = matera.jooq.tables.LogEventoRemito.LOG_EVENTO_REMITO;

	/**
	 * The table matera.log_evento_tipo
	 */
	public static final LogEventoTipo LOG_EVENTO_TIPO = matera.jooq.tables.LogEventoTipo.LOG_EVENTO_TIPO;

	/**
	 * The table matera.mantenimiento
	 */
	public static final Mantenimiento MANTENIMIENTO = matera.jooq.tables.Mantenimiento.MANTENIMIENTO;

	/**
	 * The table matera.marcha_negocios
	 */
	public static final MarchaNegocios MARCHA_NEGOCIOS = matera.jooq.tables.MarchaNegocios.MARCHA_NEGOCIOS;

	/**
	 * The table matera.marcha_negocios_detail
	 */
	public static final MarchaNegociosDetail MARCHA_NEGOCIOS_DETAIL = matera.jooq.tables.MarchaNegociosDetail.MARCHA_NEGOCIOS_DETAIL;

	/**
	 * The table matera.mayorprofesional
	 */
	public static final Mayorprofesional MAYORPROFESIONAL = matera.jooq.tables.Mayorprofesional.MAYORPROFESIONAL;

	/**
	 * The table matera.mayortecnico
	 */
	public static final Mayortecnico MAYORTECNICO = matera.jooq.tables.Mayortecnico.MAYORTECNICO;

	/**
	 * The table matera.mayorvendedor
	 */
	public static final Mayorvendedor MAYORVENDEDOR = matera.jooq.tables.Mayorvendedor.MAYORVENDEDOR;

	/**
	 * The table matera.menu
	 */
	public static final Menu MENU = matera.jooq.tables.Menu.MENU;

	/**
	 * The table matera.moneda
	 */
	public static final Moneda MONEDA = matera.jooq.tables.Moneda.MONEDA;

	/**
	 * The table matera.motivo
	 */
	public static final Motivo MOTIVO = matera.jooq.tables.Motivo.MOTIVO;

	/**
	 * The table matera.nivelescertificado
	 */
	public static final Nivelescertificado NIVELESCERTIFICADO = matera.jooq.tables.Nivelescertificado.NIVELESCERTIFICADO;

	/**
	 * The table matera.notapresu
	 */
	public static final Notapresu NOTAPRESU = matera.jooq.tables.Notapresu.NOTAPRESU;

	/**
	 * The table matera.numeracionliquidaciones
	 */
	public static final Numeracionliquidaciones NUMERACIONLIQUIDACIONES = matera.jooq.tables.Numeracionliquidaciones.NUMERACIONLIQUIDACIONES;

	/**
	 * The table matera.opcion
	 */
	public static final Opcion OPCION = matera.jooq.tables.Opcion.OPCION;

	/**
	 * The table matera.plazo
	 */
	public static final Plazo PLAZO = matera.jooq.tables.Plazo.PLAZO;

	/**
	 * The table matera.porcentaje
	 */
	public static final Porcentaje PORCENTAJE = matera.jooq.tables.Porcentaje.PORCENTAJE;

	/**
	 * The table matera.prestacion
	 */
	public static final Prestacion PRESTACION = matera.jooq.tables.Prestacion.PRESTACION;

	/**
	 * The table matera.presupuesto
	 */
	public static final Presupuesto PRESUPUESTO = matera.jooq.tables.Presupuesto.PRESUPUESTO;

	/**
	 * The table matera.presupuestoprod
	 */
	public static final Presupuestoprod PRESUPUESTOPROD = matera.jooq.tables.Presupuestoprod.PRESUPUESTOPROD;

	/**
	 * The table matera.presupuesto_controlfacturacion
	 */
	public static final PresupuestoControlfacturacion PRESUPUESTO_CONTROLFACTURACION = matera.jooq.tables.PresupuestoControlfacturacion.PRESUPUESTO_CONTROLFACTURACION;

	/**
	 * The table matera.producto
	 */
	public static final Producto PRODUCTO = matera.jooq.tables.Producto.PRODUCTO;

	/**
	 * The table matera.productofact
	 */
	public static final Productofact PRODUCTOFACT = matera.jooq.tables.Productofact.PRODUCTOFACT;

	/**
	 * The table matera.profesional
	 */
	public static final Profesional PROFESIONAL = matera.jooq.tables.Profesional.PROFESIONAL;

	/**
	 * The table matera.proveedor
	 */
	public static final Proveedor PROVEEDOR = matera.jooq.tables.Proveedor.PROVEEDOR;

	/**
	 * The table matera.provincia
	 */
	public static final Provincia PROVINCIA = matera.jooq.tables.Provincia.PROVINCIA;
        
        /**
	 * The table matera.reclamo
	 */
	public static final Reclamo RECLAMO = matera.jooq.tables.Reclamo.RECLAMO;

	/**
	 * The table matera.reclamoExterno
	 */
	public static final Reclamoexterno RECLAMOEXTERNO = matera.jooq.tables.Reclamoexterno.RECLAMOEXTERNO;

	/**
	 * The table matera.reclamoInterno
	 */
	public static final Reclamointerno RECLAMOINTERNO = matera.jooq.tables.Reclamointerno.RECLAMOINTERNO;


	/**
	 * The table matera.remito
	 */
	public static final Remito REMITO = matera.jooq.tables.Remito.REMITO;

	/**
	 * The table matera.remito_consumido
	 */
	public static final RemitoConsumido REMITO_CONSUMIDO = matera.jooq.tables.RemitoConsumido.REMITO_CONSUMIDO;

	/**
	 * The table matera.revisionsoftware
	 */
	public static final Revisionsoftware REVISIONSOFTWARE = matera.jooq.tables.Revisionsoftware.REVISIONSOFTWARE;

	/**
	 * The table matera.stock
	 */
	public static final Stock STOCK = matera.jooq.tables.Stock.STOCK;

	/**
	 * The table matera.stockdetalle
	 */
	public static final Stockdetalle STOCKDETALLE = matera.jooq.tables.Stockdetalle.STOCKDETALLE;

	/**
	 * The table matera.subespecialidad
	 */
	public static final Subespecialidad SUBESPECIALIDAD = matera.jooq.tables.Subespecialidad.SUBESPECIALIDAD;

	/**
	 * The table matera.tecnico
	 */
	public static final Tecnico TECNICO = matera.jooq.tables.Tecnico.TECNICO;

	/**
	 * The table matera.tipocomp
	 */
	public static final Tipocomp TIPOCOMP = matera.jooq.tables.Tipocomp.TIPOCOMP;

	/**
	 * The table matera.tipooperacion
	 */
	public static final Tipooperacion TIPOOPERACION = matera.jooq.tables.Tipooperacion.TIPOOPERACION;

        /**
	 * The table matera.trackingOLP
	 */
	public static final Trackingolp TRACKINGOLP = matera.jooq.tables.Trackingolp.TRACKINGOLP;

	/**
	 * The table matera.usuario
	 */
	public static final Usuario USUARIO = matera.jooq.tables.Usuario.USUARIO;

	/**
	 * The table matera.vendedor
	 */
	public static final Vendedor VENDEDOR = matera.jooq.tables.Vendedor.VENDEDOR;

	/**
	 * The table matera.zona
	 */
	public static final Zona ZONA = matera.jooq.tables.Zona.ZONA;
}