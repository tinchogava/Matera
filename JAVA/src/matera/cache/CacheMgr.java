/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheMgr {
    private static CacheAccess<String, Result<Record>> cache = null;

    public static CacheAccess<String, Result<Record>> getCache(){
        if (cache != null)
            return cache;
        try 
        {
            cache = JCS.getInstance( "testCache1" );
        }
        catch ( CacheException e ) 
        {
            System.out.println( String.format( "Problem initializing cache: %s", e.getMessage() ) );
        }
        
        return cache;
    }
}
