/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public interface CacheRecords {
    
    public Result<Record> get();
    public Result<Record> getHabilitados();
    public void fetch();
}
