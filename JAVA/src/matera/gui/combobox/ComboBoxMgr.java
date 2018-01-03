/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.gui.combobox;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.formularios.Principal;
import java.util.Iterator;
import javax.swing.JComboBox;
import matera.cache.CacheClasiProducto;
import matera.cache.CacheCodConsumo;
import matera.cache.CacheEntidad;
import matera.cache.CacheFormaDePago;
import matera.cache.CacheMoneda;
import matera.cache.CachePrestaciones;
import matera.cache.CacheProductoFact;
import matera.cache.CacheProductos;
import matera.cache.CacheProfesional;
import matera.cache.CacheProveedores;
import matera.cache.CacheReclamoExterno;
import matera.cache.CacheReclamoInterno;
import matera.cache.CacheTecnico;
import matera.cache.CacheUsuario;
import matera.cache.CacheVendedor;
import matera.cache.CacheZona;
import matera.gui.renderers.combobox.ClasiProductoRenderer;
import matera.gui.renderers.combobox.MonedaRenderer;
import matera.jooq.Tables;
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.EntidadRecord;
import matera.jooq.tables.records.FormapagoRecord;
import matera.jooq.tables.records.PrestacionRecord;
import matera.jooq.tables.records.ProductoRecord;
import matera.jooq.tables.records.ProductofactRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import matera.jooq.tables.records.ProveedorRecord;
import matera.jooq.tables.records.ReclamoexternoRecord;
import matera.jooq.tables.records.ReclamointernoRecord;
import matera.jooq.tables.records.TecnicoRecord;
import matera.jooq.tables.records.UsuarioRecord;
import matera.jooq.tables.records.VendedorRecord;
import matera.jooq.tables.records.ZonaRecord;
import org.jooq.types.UInteger;

/**
 *
 * @author h2o
 */
public class ComboBoxMgr {
    
    public static void loadCodConsumoCombo(JComboBox combo){
        //combo.setRenderer(new CodConsumoRenderer());
        CacheCodConsumo.instance().getCodConsumos().forEach(c->{
            combo.addItem(c);
        });
    }
    
    public static void loadMonedaCombo(JComboBox combo){
        combo.setRenderer(new MonedaRenderer());
        CacheMoneda.instance().getMonedasHabilitadas().forEach(c->{
            combo.addItem(c);
        });
    }
    
    public static void loadClasiProductoCombo(JComboBox combo, Boolean includeTodos){
        if(includeTodos){
            ClasiproductoRecord t = new ClasiproductoRecord();
            t.setIdClasiproducto(Utiles.TODOS);
            t.setNombre(Utiles.TODOS_STRING);
            combo.addItem(t);
        }
        combo.setRenderer(new ClasiProductoRenderer());
        CacheClasiProducto.instance().getClasiProductos().forEach(c->{
            combo.addItem(c);
        });
    }
    
    public static void loadProductoCombo(JComboBox combo){
        CacheProductos.instance().getHabilitados().forEach(p -> {
            combo.addItem(p);
        });
    }
    
    public static void loadProveedorCombo(JComboBox combo){
        //combo.setRenderer(new ProveedorRenderer());
        CacheProveedores.instance().getProveedoresHabilitados().forEach(c->{
            combo.addItem(c);
        });
    }
    
    public static void loadHabilitaCombo(JComboBox combo, Boolean includeTodos){
        if(includeTodos){
            combo.addItem(Utiles.TODOS_STRING);
        }
        combo.addItem("SI");
        combo.addItem("NO");
    }
    
    public static void fillProductoCombo(GlazedCombo combo, Boolean includeTodos ){
        combo.removeAllItemsFromEventList();
        if(includeTodos){
            ProductoRecord t = new ProductoRecord();
            t.setIdProducto(Utiles.TODOS);
            t.setNombre(Utiles.TODOS_STRING);
            combo.getEventList().add(t);
        }
        
        combo.getEventList().addAll(CacheProductos.instance().getHabilitados().into(Tables.PRODUCTO));
        
    }
    
    public static void fillProductoFactCombo(GlazedCombo combo, Boolean includeTodos ){
        combo.removeAllItemsFromEventList();
        if(includeTodos){
            ProductofactRecord e = new ProductofactRecord();
            e.setIdProductofact(Utiles.TODOS);
            e.setNombre(Utiles.TODOS_STRING);
            combo.getEventList().add(e);
            combo.setSelectedItem(e);
        }
        
        combo.getEventList().addAll(CacheProductoFact.instance().getProductoFactHabilitados());
        
    }    
    
    public static void fillZonaCombo(JComboBox combo, Boolean selectZonaUsuario, Boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            ZonaRecord e = new ZonaRecord();
            e.setIdZona(Utiles.TODOS);
            e.setNombre(Utiles.TODOS_STRING);
            combo.addItem(e);
            combo.setSelectedItem(e);
        }
        
        CacheZona.instance().getZonasHabilitadas().forEach(z->{
            combo.addItem(z);
        });
        
        if(selectZonaUsuario){
            int id_zonaUsuario = Principal.usuarioData.getId_zonaSistema();
            if (id_zonaUsuario > 0) {
                ZonaRecord zona = CacheZona.instance().getZona(id_zonaUsuario);
                if (zona != null){
                    combo.setSelectedItem(zona);
                }
            }
            combo.setEnabled(id_zonaUsuario == 0);        
        }
    }
    
    public static void fillEntidadCombo(GlazedCombo combo, Integer id_zona, Boolean includeTodos){
        combo.removeAllItemsFromEventList();
        if (includeTodos){
            EntidadRecord e = new EntidadRecord();
            e.setIdEntidad(Utiles.TODOS);            
            e.setNombre(Utiles.TODOS_STRING);
            combo.getEventList().add(e);
            combo.setSelectedItem(e);
        }
                    
        if (id_zona.equals(Utiles.TODOS)){
            combo.getEventList().addAll(CacheEntidad.instance().getEntidadesHabilitadas());
        }
        else{
            combo.getEventList().addAll(CacheEntidad.instance().getEntidadesForZona(id_zona));
        }
    }
    
    public static void fillProfesionalCombo(GlazedCombo combo, Integer id_zona, Boolean includeTodos){
        combo.removeAllItemsFromEventList();
        if (includeTodos){
            ProfesionalRecord e = new ProfesionalRecord();
            e.setIdProfesional(Utiles.TODOS);            
            e.setNombre(Utiles.TODOS_STRING);
            combo.getEventList().add(e);
            combo.setSelectedItem(e);
        }        
        
        if (id_zona.equals(Utiles.TODOS)){
                combo.getEventList().addAll(CacheProfesional.instance().getProfesionalesHabilitados());
        }
        else{
            combo.getEventList().addAll(CacheProfesional.instance().getProfesionalesForZona(id_zona));
        }
    }    
    
    public static void fillProveedorCombo(GlazedCombo combo, Boolean includeTodos){
        combo.removeAllItemsFromEventList();
        if (includeTodos){
            ProveedorRecord e = new ProveedorRecord();
            e.setIdProveedor(Utiles.TODOS);            
            e.setNombre(Utiles.TODOS_STRING);
            combo.getEventList().add(e);
            combo.setSelectedItem(e);
        }        
        
        combo.getEventList().addAll(CacheProveedores.instance().getProveedoresHabilitados());
    }
    
    public static void fillProveedorCombo(JComboBox combo, Boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            ProveedorRecord e = new ProveedorRecord();
            e.setIdProveedor(Utiles.TODOS);            
            e.setNombre(Utiles.TODOS_STRING);
            combo.addItem(e);
            combo.setSelectedItem(e);
        }        
        
        CacheProveedores.instance().getProveedoresHabilitados().forEach(z->{
            combo.addItem(z);
        });
    }
    
    public static void fillVendedorCombo(JComboBox combo, Integer id_zona, Boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            VendedorRecord e = new VendedorRecord();
            e.setIdVendedor(Utiles.TODOS);            
            e.setNombre(Utiles.TODOS_STRING);
            combo.addItem(e);
            combo.setSelectedItem(e);
        }        
        
        if (id_zona.equals(Utiles.TODOS)){
                CacheVendedor.instance().getVendedoresHabilitados().forEach(z->{
                combo.addItem(z);
            });
        }
        else{
            CacheVendedor.instance().getVendedoresForZona(id_zona).forEach(z->{
                combo.addItem(z);
            });
        }
    }
    
    public static void fillVendedorCombo(JComboBox combo, Integer id_zona, ProfesionalRecord profesional){
        combo.removeAllItems();
        
        
        if (id_zona.equals(Utiles.ZONA.MENDOZA) && Principal.VENDEDORES_HABILITADOS.contains(profesional.getIdProfesional())){
                combo.addItem(CacheVendedor.instance().getVendedor(profesional.getIdVendedor()));
        }
        else{
            CacheVendedor.instance().getVendedoresForZona(id_zona).forEach(z->{
                combo.addItem(z);
            });
        }
    }
    
    public static void fillTecnicoCombo(JComboBox combo, boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            TecnicoRecord e = new TecnicoRecord();
            e.setIdTecnico(Utiles.TODOS);            
            e.setNombre(Utiles.TODOS_STRING);
            combo.addItem(e);
            combo.setSelectedItem(e);
        }        
        CacheTecnico.instance().getTecnicosHabilitados().forEach(z->{
            combo.addItem(z);
        });
    }
    
    public static void fillPrestacionCombo(JComboBox combo, boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            PrestacionRecord e = new PrestacionRecord();
            e.setIdPrestacion(Utiles.TODOS);            
            e.setNombre(Utiles.TODOS_STRING);
            combo.addItem(e);
            combo.setSelectedItem(e);
        }        
        CachePrestaciones.instance().getPrestacionesHabilitadas().forEach(z->{
            combo.addItem(z);
        });
    }
    
    public static void fillReclamoInternoCombo(JComboBox combo, boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            ReclamointernoRecord ri = new ReclamointernoRecord();
            ri.setIdReclamointerno(Utiles.TODOS);            
            ri.setNombre(Utiles.TODOS_STRING);
            combo.addItem(ri);
            combo.setSelectedItem(ri);
        }
        CacheReclamoInterno.instance().getReclamosInternosHabilitados().forEach(z->{
            combo.addItem(z);
        });
    }
    
    public static void fillFormaDePagoCombo(JComboBox combo, Integer id_formaDePago){
        combo.removeAllItems();
        boolean msg = false;
        if(id_formaDePago != 0){
            FormapagoRecord fp = CacheFormaDePago.instance().getFormaDePago(id_formaDePago);
            combo.addItem(fp);
            combo.addItem(CacheFormaDePago.instance().getFormaDePago(7));
            combo.setSelectedItem(fp);
        } else {
            CacheFormaDePago.instance().getFormasDePago().forEach(z->{
                combo.addItem(z);
            });
        } 
    }
    
    public static void fillReclamoExternoCombo(JComboBox combo, boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            ReclamoexternoRecord re = new ReclamoexternoRecord();
            re.setIdReclamoexterno(Utiles.TODOS);            
            re.setNombre(Utiles.TODOS_STRING);
            combo.addItem(re);
            combo.setSelectedItem(re);
        }
        CacheReclamoExterno.instance().getReclamosExternosHabilitados().forEach(z->{
            combo.addItem(z);
        });
    }
    
    public static void fillUsuarioCombo(JComboBox combo, boolean includeTodos){
        combo.removeAllItems();
        if (includeTodos){
            UsuarioRecord re = new UsuarioRecord();
            re.setIdUsuario((UInteger.valueOf(Utiles.TODOS)));            
            re.setNombre(Utiles.TODOS_STRING);
            combo.addItem(re);
            combo.setSelectedItem(re);
        }
        CacheUsuario.instance().getUsuariosHabilitados().forEach(z->{
            combo.addItem(z);
        });
    }
    public static Integer getSelectedId(JComboBox combo){       
        
        if (combo.getSelectedIndex() == -1)
            return Utiles.TODOS;
        
        Object t = combo.getSelectedItem();
        
        if(t instanceof ZonaRecord){
            ZonaRecord e = (ZonaRecord) t;
            return e.getIdZona();
        }
        
        if(t instanceof FormapagoRecord){
            FormapagoRecord e = (FormapagoRecord) t;
            return e.getIdFormapago();
        }
        
        if(t instanceof UsuarioRecord){
            UsuarioRecord e = (UsuarioRecord) t;
            return e.getIdUsuario().intValue();
        }
        
        if(t instanceof EntidadRecord){
            EntidadRecord e = (EntidadRecord) t;
            return e.getIdEntidad();
        }
        
        if(t instanceof ProfesionalRecord){
            ProfesionalRecord e = (ProfesionalRecord) t;
            return e.getIdProfesional();
        }
        
        if(t instanceof VendedorRecord){
            VendedorRecord e = (VendedorRecord) t;
            return e.getIdVendedor();
        }        
        
        if(t instanceof ProductoRecord){
            ProductoRecord e = (ProductoRecord) t;
            return e.getIdProducto();
        }
        
        if(t instanceof ProductofactRecord){
            ProductofactRecord e = (ProductofactRecord) t;
            return e.getIdProductofact();
        }
        
        if(t instanceof ProveedorRecord){
            ProveedorRecord e = (ProveedorRecord) t;
            return e.getIdProveedor();
        } 
        
        if(t instanceof TecnicoRecord){
            TecnicoRecord e = (TecnicoRecord) t;
            return e.getIdTecnico();
        }
        
        if(t instanceof PrestacionRecord){
            PrestacionRecord e = (PrestacionRecord) t;
            return e.getIdPrestacion();
        }
        
        if(t instanceof ReclamointernoRecord){
            ReclamointernoRecord e = (ReclamointernoRecord) t;
            return e.getIdReclamointerno();
        }
        
        if(t instanceof ReclamoexternoRecord){
            ReclamoexternoRecord e = (ReclamoexternoRecord) t;
            return e.getIdReclamoexterno();
        }        
        
        return null;
    }    
    
    public static void setSelectedItemById(JComboBox combo, Integer id){       
        
        Object t = combo.getItemAt(1);
        /*
        if(t instanceof ZonaRecord){
            ZonaRecord e = (ZonaRecord) t;            
            combo.setSelectedItem(e);
        }
        
        if(t instanceof EntidadRecord){
            EntidadRecord e = (EntidadRecord) t;
            combo.setSelectedItem(e);
        }
        
        if(t instanceof ProfesionalRecord){
            ProfesionalRecord e = (ProfesionalRecord) t;
            combo.setSelectedItem(e);
        }
        
        if(t instanceof VendedorRecord){
            VendedorRecord e = (VendedorRecord) t;
            combo.setSelectedItem(e);
        }        
        
        if(t instanceof ProductoRecord){
            ProductoRecord e = (ProductoRecord) t;
            combo.setSelectedItem(e);
        }
        */
        if(t instanceof UsuarioRecord){
            UsuarioRecord e = (UsuarioRecord) t;
            combo.setSelectedItem(CacheUsuario.instance().getUsuario(id));
        }
        
        if(t instanceof ProveedorRecord){
            combo.setSelectedItem(CacheProveedores.instance().getProveedor(id));
        } 
        
        if(t instanceof TecnicoRecord){
            combo.setSelectedItem(CacheTecnico.instance().getTecnico(id));
        }
        
        if(t instanceof PrestacionRecord){
            combo.setSelectedItem(CachePrestaciones.instance().getPrestacion(id));
        }
        
        if(t instanceof ReclamointernoRecord){
            combo.setSelectedItem(CacheReclamoInterno.instance().getReclamoInterno(id));
        }
        
        if(t instanceof ReclamoexternoRecord){
            combo.setSelectedItem(CacheReclamoExterno.instance().getReclamoExterno(id));
        }        

    }    
}
