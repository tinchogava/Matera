/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author h2o
 */
@AllArgsConstructor
public class MayorGeneradoConsolidado {
    @Getter @Setter String medico;
    @Getter @Setter BigDecimal saldo;
}
