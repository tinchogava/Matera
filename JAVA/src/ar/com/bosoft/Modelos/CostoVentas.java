/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import matera.services.RmService;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
@AllArgsConstructor
public class CostoVentas {

@Getter @Setter Integer id_presupuesto, cantidad;
@Getter @Setter String categoria, vendedor, producto, formaPago;
@Getter @Setter BigDecimal costoPesos, totalCosto, costo_venta, rmPresupuesto, totalPresupuesto, totalFacturado;
@Getter @Setter Double cotizacion, rmPagado;  
    
    public CostoVentas(int id_presupuesto, Integer cantidad, String categoria, String vendedor, String producto, 
            BigDecimal costoPesos, BigDecimal totalCosto, BigDecimal costo_venta, Double cotizacion) {
        this.id_presupuesto = id_presupuesto;
        this.categoria = categoria;
        this.vendedor = vendedor;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costoPesos = costoPesos;
        this.totalCosto = totalCosto;
        this.costo_venta = costo_venta;
        this.cotizacion = cotizacion;
}
    //Agregar monto facturado y rm pagado
    
    public CostoVentas(int id_presupuesto, Integer cantidad, String categoria, String vendedor, String producto, 
            BigDecimal costoPesos, BigDecimal totalCosto, BigDecimal costo_venta, Double cotizacion, String formaPago, 
            BigDecimal totalPresupuesto, BigDecimal rmPresupuesto) {
        this.id_presupuesto = id_presupuesto;
        this.categoria = categoria;
        this.vendedor = vendedor;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costoPesos = costoPesos;
        this.totalCosto = totalCosto;
        this.costo_venta = costo_venta;
        this.cotizacion = cotizacion;
        this.formaPago = formaPago;
        this.totalPresupuesto = totalPresupuesto;
        this.rmPresupuesto = rmPresupuesto;
    }
}
