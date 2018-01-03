/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.repository;

import java.util.List;
import matera.db.model.HistoricoPrecioProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lpascuali
 */
@Repository
public interface HistoricalPriceProductRepository extends JpaRepository<HistoricoPrecioProducto, Long>{
    
    public List<HistoricoPrecioProducto> findByIdProductoOrderByFechaActualizacionDesc(Long idProducto);
}
