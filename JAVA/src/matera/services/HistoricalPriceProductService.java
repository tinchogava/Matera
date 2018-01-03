/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.formularios.Principal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import matera.db.model.HistoricoPrecioProducto;
import matera.db.model.Stock;
import matera.db.model.Usuario;
import matera.db.repository.HistoricalPriceProductRepository;

/**
 *
 * @author lpascuali
 */
public class HistoricalPriceProductService {
    
    private static final HistoricalPriceProductRepository REPOSITORY = (HistoricalPriceProductRepository) Principal.appContext.getBean("historicalPriceProductRepository");
    
    public static boolean create(Integer productId, BigDecimal oldPrice, BigDecimal newPrice){
        oldPrice = oldPrice.setScale(3, RoundingMode.CEILING);
        newPrice = newPrice.setScale(3, RoundingMode.CEILING);
        if (oldPrice.compareTo(newPrice) != 0){
            HistoricoPrecioProducto hpp = new HistoricoPrecioProducto();
            hpp.setIdProducto(new Long(productId));
            Usuario user = new Usuario();
            user.setId(new Long(Principal.getIdUsuario()));
            hpp.setUsuario(user);
            hpp.setPrecioAnterior(oldPrice);
            hpp.setPrecioNuevo(newPrice);
            REPOSITORY.save(hpp);           
        }
        return true;
    }
    
    public static boolean createFromStock(Integer stockId, Integer productId, double oldPrice, double newPrice){
        BigDecimal oldPriceBD = new BigDecimal(oldPrice).setScale(3, RoundingMode.CEILING);
        BigDecimal newPriceDB = new BigDecimal(newPrice).setScale(3, RoundingMode.CEILING);
        if (oldPriceBD.compareTo(newPriceDB) != 0){
            HistoricoPrecioProducto hpp = new HistoricoPrecioProducto();
            hpp.setIdProducto(new Long(productId));
            Usuario user = new Usuario();
            user.setId(new Long(Principal.getIdUsuario()));
            hpp.setUsuario(user);
            Stock stock = new Stock();
            stock.setId(new Long(stockId));
            hpp.setStock(stock);
            hpp.setPrecioAnterior(oldPriceBD);
            hpp.setPrecioNuevo(newPriceDB);
            REPOSITORY.save(hpp);
        }
        return true;
    }
    
    public static List<HistoricoPrecioProducto> findByProductId(Long productId){        
        return REPOSITORY.findByIdProductoOrderByFechaActualizacionDesc(productId);
    }
    
  
}
