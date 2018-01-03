package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */

@AllArgsConstructor
public class MayorGeneralDetallado {
        @Getter @Setter String nombre, paciente, tipoOperacion, observaciones;
        @Getter @Setter BigDecimal saldoInicial, debe, haber;
        @Getter @Setter Date fecha;
        @Getter @Setter int id_presupuesto;
}
