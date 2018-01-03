/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.buscadores.General1;
import ar.com.bosoft.clases.IconoEspera;
import ar.com.bosoft.clases.MiMenu;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.data.EmpresaData;
import ar.com.bosoft.data.UsuarioData;
import ar.com.dialogos.AvisoEspera;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import matera.cache.CacheMgr;
import matera.jooq.Tables;
import matera.jooq.tables.Revisionsoftware;
import matera.logging.PrettyPrinter;
import org.apache.commons.lang.time.DateUtils;
import org.javalite.activejdbc.Base;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Principal extends javax.swing.JFrame {
    public static Utiles u;
    public static Conexion conexion;
    JFrame frameTmp;
    Ingreso ingreso;
    public static EmpresaData empresaData;
    public static UsuarioData usuarioData;
    String detalle, nombreSoft, sucursal;
    //
    // SE DEBE MODIFICAR CON CADA ACTUALIZACION!!
    public static int revision = 205;
    //
    //
    public static String equipo;
    boolean critica;
    
    IconoEspera iconoEspera;
    AvisoEspera avisoEspera;
    
    Timer buscaActualizacion, certificadosPendientes;
    
    ResultSet rs;
    
    public static General1 general1;
    
    private final JScrollPane scrollPane = new JScrollPane();
    
    public static ApplicationContext appContext;
    
    public static class TIPO_COMP{
        public static final Set RECIBOS = Collections.synchronizedSet(new HashSet());
        public static final Set NOTAS_CREDITO = Collections.synchronizedSet(new HashSet());        
        public static final Set FACTURAS = Collections.synchronizedSet(new HashSet());
    }
    
    public static final Set VENDEDORES_HABILITADOS = Collections.synchronizedSet(new HashSet());
    
    public Principal() {
        // Spring data configuration
        appContext = new FileSystemXmlApplicationContext("config/database-context.xml");
        
        if (!(Utiles.validaSistema())){
            JOptionPane.showMessageDialog(null,"El Sistema no encuentra los archivos necesarios para iniciar.\nComuníquese con BOSOFT", "Error de registro",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        this.conexion = new Conexion();
        Principal.general1 = new General1(null, true);
        
        if (!estadoSistema().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                    "El sistema está realizando algunas tareas y no puede ejecutarse en este momento.\n\nProceso actual: " + estadoSistema().toUpperCase() + "\n\nVuelva a intentarlo en unos instantes", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            this.jBtnApagarActionPerformed(null);
        }
        
        if (!isSameDate()){
            JOptionPane.showMessageDialog(null,"Su equipo y el sistema tienen diferente fecha, corrija la fecha de su equipo.", "Error de fecha",JOptionPane.ERROR_MESSAGE);
            System.exit(0);        
        }
        
        boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().contains("jdwp");
        // Enable Query Logger
        if (isDebug)
            ActiveDatabase.getDSLContext().configuration().set(new DefaultExecuteListenerProvider(new PrettyPrinter()));
        
        hayNuevaRevision(true);
        initComponents();
        scrollPane.getViewport().add(dp);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane,BorderLayout.CENTER);
        
        //setIconImage(new ImageIcon(getClass().getResource("imagenes/logoMyATQ.jpg")).getImage());
        registraEnter();
        cargaSucursal();
        cargaEquipo();
        
        init();
    }

    private void registraEnter(){
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false); 
        Action accion = new AbstractAction() {    
            @Override
            public void actionPerformed(ActionEvent e ){  
                getFocusOwner().transferFocus(); 
            } 
        };
        getRootPane().registerKeyboardAction(accion,"Enter",enter,javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    
    private String estadoSistema(){
        String proceso = "";
        try {
            ArrayList parametros = new ArrayList();
            
            rs = conexion.procAlmacenado("consultaEstadoSistema", parametros,
                    "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            if (rs.first()) {
                proceso = rs.getString("estado");
            }
        } catch (SQLException ex) {
            Utiles.enviaError("MyATQ",ex);
        }
        return proceso;
    }
    
    public Boolean isSameDate(){
        Date sysDate = (Date) Base.firstCell("Select CURDATE()");
        Date todayDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
        Boolean isSameDate = sysDate.equals(todayDate);
        return isSameDate;
    }
    
    private void update(Integer oldVersion, Integer newVersion, String url)
    {
        String[] run = {"java","-jar","updater/update.jar", oldVersion.toString(), newVersion.toString(), url};
        try {
            Runtime.getRuntime().exec(run);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se ha podido abrir la dirección de descarga", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        System.exit(0);
    }
    
    public Integer getPendingUpdates(){
        return ActiveDatabase
                .getDSLContext()
                .selectCount()
                .from(Tables.REVISIONSOFTWARE)
                .where(Tables.REVISIONSOFTWARE.ID_REVISIONSOFTWARE.greaterThan(revision))
                .fetchOneInto(Integer.class);
    }
    
    public Revisionsoftware getLastUpdate(){
        return ActiveDatabase
                .getDSLContext()
                .select()
                .from(Tables.REVISIONSOFTWARE)
                .where(Tables.REVISIONSOFTWARE.ID_REVISIONSOFTWARE.greaterThan(revision))
                .orderBy(Tables.REVISIONSOFTWARE.ID_REVISIONSOFTWARE.desc())
                .limit(1)
                .fetchOneInto(Revisionsoftware.class);
    }
    
    private void hayNuevaRevision(boolean todas){
        int ultimaRevision;
        String link = "";
        try {
            ArrayList parametros = new ArrayList();
            
            rs = conexion.procAlmacenado("consultaUltimaRevision", parametros,
                    "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rs.first();
            ultimaRevision = rs.getInt("revision");
            String c = rs.getString("critica");
            this.critica = c.equals("S");
            if (revision < ultimaRevision){
                link = rs.getString("link");
            }
            
            if (!link.isEmpty()){
                Integer pending = getPendingUpdates();
                CacheMgr.getCache().clear();
                if (this.critica) {
                    
                    int respuesta = JOptionPane.showConfirmDialog(null, "Existen "+ pending + " actualizaciones críticas del sistema.\n"
                            + "Necesita descargarla para continuar. ¿Desea hacerlo ahora?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        update(revision, ultimaRevision, link);
                    }else{
                        this.jBtnApagarActionPerformed(null);
                    }
                }
                else if (todas) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Existen "+pending+" actualizacines del sistema. ¿Desea descargarla ahora?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        update(revision, ultimaRevision, link);
                    }
                }                
            }
        } catch (SQLException ex) {
            Utiles.enviaError("MyATQ",ex);
        }
    }
    
    public static void muestra(JInternalFrame form){
        int x = (Principal.dp.getWidth() / 2) - (form.getWidth() / 2);
        int y = (Principal.dp.getHeight() / 2) - (form.getHeight() / 2);
        
        form.setLocation(x, y);
        
        if (!form.isShowing()) {
            dp.add(form);
            form.show();
            form.setClosable(true);
            form.setMaximizable(true);
            form.setAutoscrolls(true);            
            try {
                form.setMaximum(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }        
    }
    
    public static String getUsuarioName(){
        return Principal.usuarioData.getNombre();
    }
    
    public static Integer getIdUsuario(){
        return Principal.usuarioData.getId_usuario();
    }

    public static Integer getIdZona(){
        return Principal.usuarioData.getId_zona();
    }
    
    public static Integer getIdEmpresa(){
        return Principal.empresaData.getId_empresa();
    }
    
    public static String getEmpresaName(){
        return Principal.empresaData.getFantasia();
    }     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dp = new javax.swing.JDesktopPane();
        jBtnApagar = new javax.swing.JButton();
        jBtnHelp = new javax.swing.JButton();
        jBtnWeb = new javax.swing.JButton();
        jLblRevision = new javax.swing.JLabel();
        jBtnAlerta = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dp.setPreferredSize(new java.awt.Dimension(1366, 768));

        jBtnApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/enabled/apagar.png"))); // NOI18N
        jBtnApagar.setBorderPainted(false);
        jBtnApagar.setContentAreaFilled(false);
        jBtnApagar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnApagar.setFocusPainted(false);
        jBtnApagar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/apagar.png"))); // NOI18N
        jBtnApagar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/apagar.png"))); // NOI18N
        jBtnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnApagarActionPerformed(evt);
            }
        });
        dp.add(jBtnApagar);
        jBtnApagar.setBounds(70, 10, 64, 64);

        jBtnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/enabled/Help.png"))); // NOI18N
        jBtnHelp.setBorderPainted(false);
        jBtnHelp.setContentAreaFilled(false);
        jBtnHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnHelp.setFocusPainted(false);
        jBtnHelp.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/Help.png"))); // NOI18N
        jBtnHelp.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/Help.png"))); // NOI18N
        jBtnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnHelpActionPerformed(evt);
            }
        });
        dp.add(jBtnHelp);
        jBtnHelp.setBounds(140, 10, 64, 64);

        jBtnWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/enabled/internet.png"))); // NOI18N
        jBtnWeb.setBorderPainted(false);
        jBtnWeb.setContentAreaFilled(false);
        jBtnWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnWeb.setFocusPainted(false);
        jBtnWeb.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/internet.png"))); // NOI18N
        jBtnWeb.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/internet.png"))); // NOI18N
        jBtnWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnWebActionPerformed(evt);
            }
        });
        dp.add(jBtnWeb);
        jBtnWeb.setBounds(10, 10, 64, 64);

        jLblRevision.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblRevision.setForeground(new java.awt.Color(0, 153, 255));
        jLblRevision.setText("Revisión ");
        jLblRevision.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLblRevision.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblRevisionMouseClicked(evt);
            }
        });
        dp.add(jLblRevision);
        jLblRevision.setBounds(10, 90, 180, 17);

        jBtnAlerta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/enabled/alerta.png"))); // NOI18N
        jBtnAlerta.setToolTipText("Hay certificados sin controlar");
        jBtnAlerta.setBorderPainted(false);
        jBtnAlerta.setContentAreaFilled(false);
        jBtnAlerta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnAlerta.setFocusPainted(false);
        jBtnAlerta.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/alerta.png"))); // NOI18N
        jBtnAlerta.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/64x64/rollover/Help.png"))); // NOI18N
        jBtnAlerta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAlertaActionPerformed(evt);
            }
        });
        dp.add(jBtnAlerta);
        jBtnAlerta.setBounds(210, 10, 64, 64);

        jButton1.setText("LIMPIAR SISTEMA DE CACHE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        dp.add(jButton1);
        jButton1.setBounds(10, 330, 230, 50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dp, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnApagarActionPerformed
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {}
        }
        
        this.conexion.desconectar();
        System.exit(0);
    }//GEN-LAST:event_jBtnApagarActionPerformed

    private void jBtnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHelpActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("http://matera.macher.com.ar"));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnHelpActionPerformed

    private void jBtnWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnWebActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("http://matera.macher.com.ar"));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnWebActionPerformed

    private void jLblRevisionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblRevisionMouseClicked
        try {
            Desktop.getDesktop().browse(new URI("https://docs.google.com/document/d/1iVAlRxMuUI0_mM5hguPUxIifuW1CGs_u_aUQfYu3CoE/edit#"));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }//GEN-LAST:event_jLblRevisionMouseClicked

    private void jBtnAlertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAlertaActionPerformed
        try {
            this.jBtnAlerta.setVisible(false);
            this.AccionesMenu("51");
        } catch (SQLException | ParseException ex) {}
    }//GEN-LAST:event_jBtnAlertaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CacheMgr.getCache().clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void init(){
        Dimension tamaño = Toolkit.getDefaultToolkit().getScreenSize();
        
        //Posiciona Boton Internet
        int xI = tamaño.width  - 25 - this.jBtnWeb.getWidth();
        int yI = 25;
        this.jBtnWeb.setLocation(xI,yI);
        //Fin Posiciona Boton Internet
        
        //Posiciona Boton Help
        int xH = tamaño.width  - 25 - this.jBtnHelp.getWidth();
        int yH = 25 + this.jBtnWeb.getHeight() + 25;
        this.jBtnHelp.setLocation(xH,yH);
        //Fin Posiciona Boton Help
        
        //Posiciona Boton alerta
        int xR = tamaño.width  - 25 - this.jBtnAlerta.getWidth();
        int yR = 25 + this.jBtnWeb.getHeight() + this.jBtnAlerta.getHeight() + 40;
        this.jBtnAlerta.setLocation(xR,yR);
        this.jBtnAlerta.setVisible(false);
        //Fin Posiciona Boton Apagar
        
        //Posiciona Boton apagar
        int xA = tamaño.width  - 25 - this.jBtnApagar.getWidth();
        int yA = tamaño.height - 100 - this.jBtnApagar.getHeight();
        this.jBtnApagar.setLocation(xA,yA);
        //Fin Posiciona Boton Apagar
        
        //Posiciona Label revision
        this.jLblRevision.setText(this.jLblRevision.getText() + " " + revision);
        int xL = 25;
        int yL = tamaño.height - 200 - this.jLblRevision.getHeight();
        this.jLblRevision.setLocation(xL,yL);
        //Fin Label revision
        
        
        dp.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        ImgFondo pnlFondo = new ImgFondo();
        dp.add(pnlFondo, BorderLayout.CENTER);
        
        String pathIcono = System.getProperty("user.dir") + "\\iconos\\Bosoft.png";
        if (Utiles.existeFichero(pathIcono)){
            Image icon = Toolkit.getDefaultToolkit().getImage(pathIcono);
            setIconImage(icon);
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.getContentPane().add(dp);
        
        frameTmp = new JFrame();
        ingreso = new Ingreso(this.conexion, this, true);
        ingreso.setLocationRelativeTo(null);
        ingreso.setVisible(true);
        
        if (!ingreso.isValidated()){
            Utiles.showMessage("Login Incorrecto. Cerrando programa.");
            this.dispose();
            System.exit(0);
        }
            
            
        empresaData = ingreso.getEmpresaData();
        usuarioData = ingreso.getUsuarioData();
        
        this.setTitle("EMPRESA: " + empresaData.getFantasia() + "     Usuario: " + usuarioData.getNombre() + " - Base de Datos: " + conexion.schema);
        
        cargaMenu();
        this.iconoEspera = new IconoEspera(this);
        this.buscaActualizacion = new Timer(); 
        programaBuscaActualizacion();
        
        if (Principal.usuarioData.getId_usuario() == 10 ||    //QUATTRINI. GERMAN MAURICIO
            Principal.usuarioData.getId_usuario() == 14 || //VACCARONE DANILO EDGARDO
            Principal.usuarioData.getId_usuario() == 16 || //VACCARONE MARIO EDGARDO
            Principal.usuarioData.getId_usuario() == 75){  //ORELLANO MOFFICONE MARIA CARLA)
            
            this.certificadosPendientes = new Timer();
            programaCertificadosPendientes();
        }
        Facturas();
        Recibos();
        NotasCredito();
        VendedoresHabilitados();
    }
    
    private void programaBuscaActualizacion(){
        this.buscaActualizacion.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    hayNuevaRevision(false);
                }
            }, 60*1000, 60*1000);
    }
    
    private void programaCertificadosPendientes(){
        this.certificadosPendientes.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    hayCertificadosSinControl();
                }
            }, 10*1000, 2*60*1000);
    }
    
    private void hayCertificadosSinControl(){
        try {
            ArrayList parametros = new ArrayList();
            
            rs = conexion.procAlmacenado("consultaCertificadosSinControl", parametros,
                    "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rs.first();
            
            int cantidad = rs.getInt("cantidad");
            if (cantidad > 0) {
                this.jBtnAlerta.setVisible(true);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void cargaMenu(){
        this.setJMenuBar(null);
        ArrayList arrayMenu = new ArrayList();
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(usuarioData.getId_usuario());
            parametros.add(empresaData.getId_empresa());
            
            rs = conexion.procAlmacenado("creaMenu", parametros, 
                    empresaData.getFantasia(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].toString());
            
            rs.beforeFirst();
            while (rs.next()){
                String posicion = rs.getString("posicion");
                String nombre = rs.getString("nombre");
                int accion = rs.getInt("accion");
                
                Object[] fila = {posicion, nombre, accion};
                
                arrayMenu.add(fila);
            }
            
            if (arrayMenu.isEmpty()){
                JOptionPane.showMessageDialog(this,"No tiene accesos asignados.\nComuníquese con el Administrador del Sistema", "Información",JOptionPane.INFORMATION_MESSAGE);
                this.jBtnApagarActionPerformed(null);
            }
        }catch ( HeadlessException | SQLException ex){
            Utiles.enviaError("MyATQ",ex);
        }
        
        MiMenu mnPrin = new MiMenu(arrayMenu,"AccionesMenu",empresaData.getFantasia());
        this.setJMenuBar(mnPrin);
    }
    private void cargaSucursal(){
        Properties properties = new Properties();
    	InputStream input = null;
        try {
        	input = new FileInputStream("config/configuration.properties");
        	properties.load(input);
            this.sucursal = properties.getProperty("sucursal") == null ? "" : properties.getProperty("sucursal");
        } catch (IOException ex) {
            Utiles.enviaError("MyATQ",ex);
        }
    }
    
    private void cargaEquipo(){
        String eq;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            eq = localHost.getHostName() + ", IP: " + localHost.getHostAddress();
        } catch (UnknownHostException ex) {
            eq = "Imposible recuperar información de equipo";
        }
        
        Principal.equipo = (eq.length() > 255 ? eq.substring(0, 255) : eq);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }
    
        public class ImgFondo extends javax.swing.JPanel{
            public ImgFondo(){
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();        
                this.setSize(d);
            }

            @Override
            public void paintComponent (Graphics g){
                Dimension tamaño = Toolkit.getDefaultToolkit().getScreenSize();
                String pathFondo = /*System.getProperty("user.dir") + "\\*/"imagenes/"+tamaño.width+"x"+tamaño.height+".png";
                ImageIcon imagenFondo = new ImageIcon();
                if (Utiles.existeFichero(pathFondo)){
                    imagenFondo = new ImageIcon(pathFondo);
                }else{
                    pathFondo = /*System.getProperty("user.dir") + "\\*/"imagenes/1024x768.png";
                    if (Utiles.existeFichero(pathFondo)){
                        imagenFondo = new ImageIcon(pathFondo);
                    }
                }
                try{
                    g.drawImage(imagenFondo.getImage(), 0, 0, tamaño.width, tamaño.height, null);
                } catch (Exception e){}
                setOpaque(false);
                super.paintComponent(g);
            }
        }
        
        public void AccionesMenu (String Opc) throws SQLException, ParseException{
            JInternalFrame form = null;
            int id_empresa = empresaData.getId_empresa();
            String empresa = empresaData.getFantasia();
            int id_usuario = usuarioData.getId_usuario();
            String usuario = usuarioData.getNombre();
            String claveAccesos = empresaData.getClaveAccesos();
            
            Thread hilo = new Thread (this.iconoEspera.getRunnable());
            hilo.start();
            
            switch (Integer.parseInt(Opc)){
//                case x:
//                    form = new NombreForm(parametros);
//                    break;

                case 1:
                    form = new ABMProducto(conexion, id_empresa, empresa);
                    break;
                    
                case 2:
                    form = new AltaStock(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 3:
                    form = new AjusteStock(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 4:
                    form = new ConsultaStock(conexion, id_empresa, empresa);
                    break;
                    
                case 5:
                    form = new ControlPrecios(conexion, id_empresa, empresa);
                    break;
                    
                case 6:
                    form = new CambiaPrecios(conexion, id_empresa, empresa);
                    break;
                    
                case 7:
                    form = new ABMProductoFact(conexion, empresa);
                    break;
                    
                case 8:
                    form = new ABMCodConsumo(conexion, empresa);
                    break;
                    
                case 9:
                    form = new ABMClasiProducto(conexion, empresa);
                    break;
                    
                case 10:
                    form = new ABMCaja(conexion, empresa);
                    break;
                    
                case 11:
                    form = new CajaComposicion(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 12:
                    form = new ABMCajaDeposito(conexion, id_empresa, empresa);
                    break;
                    
                case 13:
                    form = new ABMFormaPago(conexion, empresa);
                    break;
                    
                case 14:
                    form = new ABMMantenimiento(conexion, empresa);
                    break;
                    
                case 15:
                    form = new ABMPlazo(conexion, empresa);
                    break;
                    
                case 16:
                    form = new NotaPresu(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 17:
                    form = new ABMEspecialidad(conexion, empresa);
                    break;
                    
                case 18:
                    form = new ABMSubespecialidad(conexion, empresa);
                    break;
                    
                case 19:
                    form = new ABMTecnico(conexion, id_empresa, empresa);
                    break;
                    
                case 20:
                    form = new ABMPrestacion(conexion, id_empresa, empresa);
                    break;
                    
                case 21:
                    form = new ABMClasiEntidad(conexion, empresa);
                    break;
                    
                case 22:
                    form = new ABMZona(conexion, empresa);
                    break;
                    
                case 23:
                    form = new ABMMoneda(conexion, empresa);
                    break;
                    
                case 24:
                    form = new ABMUsuario(conexion, id_empresa, empresa, id_usuario);
                    break;
                    
                case 25:
                    form = new ABMAgente(conexion, id_empresa, empresa);
                    break;
                    
                case 26:
                    form = new ABMDiaria(conexion, id_empresa, empresa);
                    break;
                    
                case 27:
                    form = new ABMEntidad(conexion, id_empresa, empresa);
                    break;
                    
                case 28:
                    form = new ABMProfesional(conexion, id_empresa, empresa);
                    break;
                    
                case 29:
                    form = new ABMProveedor(conexion, id_empresa, empresa);
                    break;
                    
                case 30:
                    form = new CumpleProf(conexion, id_empresa, empresa);
                    break;
                    
                case 31:
                    form = new Presupuesto(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 32:
                    form = new Comparativa(conexion, id_empresa, empresa);
                    break;
                    
                case 33:
                    form = new NegociosPendientes(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 34:
                    form = new AplicacionAnticipos(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 35:
                    form = new Preliquidacion(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 36:
                    form = new MovimientoMayorProfesional(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 37:
                    form = new ConsultaMayor(conexion, id_empresa, empresa);
                    break;
                    
                case 38:
                    form = new CargaFactura(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 39:
                    form = new ModificaFactura(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 40:
                    form = new AnalisisFacturacionComercial(conexion, id_empresa, empresa);
                    break;
                    
                case 41:
                    form = new PendienteFacturacion(conexion, id_empresa, empresa);
                    break;
                    
                case 42:
                    form = new CostoVentas(conexion, id_empresa, empresa);
                    break;
                    
                case 43:
                    form = new ConsultaEstrategia(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 44:
                    form = new RankingVenta(conexion, id_empresa, empresa);
                    break;
                    
                case 45:
                    form = new ControlEntidades(conexion, id_empresa, empresa);
                    break;
                    
                case 46:
                    form = new ConsultaEstrategiaEntidad(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 47:
                    form = new ConfirmaTurno(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 48:
                    form = new ConsultaConfirmados(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 49:
                    form = new MapaCirugia(conexion, id_empresa, empresa);
                    break;
                    
                case 50:
                    form = new RemitoRecepcion(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 51:
                    form = new CertificadoServicio(conexion, id_empresa, empresa, id_usuario, usuario);
                    break;
                    
                case 52:
                    form = new HonorariosTecnico(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 53:
                    form = new CargaRemito(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 54:
                    form = new ConsultaRemito(conexion, id_empresa, empresa);
                    break;
                    
                case 55:
                    form = new LiquidacionTecnico(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 56:
                    form = new OrdenPago(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 57:
                    form = new ConsultaOrdenPago(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 58:
                    form = new CargaRecibo(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 59:
                    form = new DetalleCobranza(conexion, id_empresa, empresa);
                    break;
                    
                case 60:
                    form = new AnalisisCobranza(conexion, id_empresa, empresa);
                    break;
                    
                case 61:
                    form = new Comisiones(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 62:
                    form = new AnulaCircuito(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 63:
                    form = new ModificaConsumido(conexion, id_empresa, empresa, usuario);
                    //form = null;
                    break;
                    
                case 64:
                    form = new GestionAccesos(conexion, id_empresa, empresa, id_usuario, claveAccesos);
                    break;
                    
                case 65:
                    form = new Cotizacion(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 66:
                    form = new Trazabilidad(conexion, id_empresa, empresa);
                    break;
                    
                case 67:
                    form = new HistoricoCajas(conexion, id_empresa, empresa);
                    break;
                    
                case 68:
                    form = new HistoricoOperaciones(conexion, id_empresa, empresa);
                    break;
                    
                case 69:
                    form = new RemitoTransito(conexion);
                    break;
                    
                case 70:
                    form = new IntersucursalSalida(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 71:
                    form = new IntersucursalRecepcion(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 72:
                    form = new IntersucursalDevolucion(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 73:
                    form = new IntersucursalRecibeDevolucion(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 74:
                    form = new CargaFacturaPresu(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 75:
                    form = new ConsultaAltaStock(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 76:
                    Presupuesto p = new Presupuesto(conexion, id_empresa, empresa, usuario);
                    p.setModifiable(true);
                    p.setTitle("Modificación de Presupuestos");
                    p.setBackground(Color.white);
                    form = p;
                    break;
                    
                case 77:
                    form = new ForecastEntidad(conexion, id_empresa, empresa);
                    break;
                    
                case 78:
                    form = new ForecastGrupo(conexion, id_empresa, empresa);
                    break;
                    
                case 79:
                    form = new ForecastCarga(conexion, id_empresa, empresa, usuario);
                    break;
                    
                case 80:
                    form = new StockCajas(conexion, id_empresa, empresa);
                    break;
                    
                case 81:
                    form = new RankingCirugias(conexion, id_empresa, empresa);
                    break;
                    
                case 82:
                    form = new AnalisisFacturacionAdm(conexion, id_empresa, empresa);
                    break;
                    
                case 83:
                    form = new ForecastSeguimiento(conexion, id_empresa, empresa);
                    break;
                    
                case 84:
                    form = new DiferenciasIntersucursal(conexion, id_empresa, empresa);
                    break;
                    
                case 85:
                    form = new ABMTipoOp();
                    break;
                    
                case 86: 
                    form = new ReclamoInterno();
                    break;
                    
                case 87:
                    form = new ReclamoExterno();
                    break;
                    
                case 88:
                    form = new ListaReclamos();
                    break;
                    
                case 89:
                    form = new ABMEmails();
                    break;
                    
                case 90:
                    form = new OLPTracking();
                    break;
            }
            
            this.iconoEspera.devuelveIconoOriginal();
            if (form != null)
                muestra(form);
        }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane dp;
    private javax.swing.JButton jBtnAlerta;
    private javax.swing.JButton jBtnApagar;
    private javax.swing.JButton jBtnHelp;
    private javax.swing.JButton jBtnWeb;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLblRevision;
    // End of variables declaration//GEN-END:variables
private void Facturas() {     
            TIPO_COMP.FACTURAS.add(Utiles.TIPO_COMP.FACTURA_A);
            TIPO_COMP.FACTURAS.add(Utiles.TIPO_COMP.FACTURA_B);
            TIPO_COMP.FACTURAS.add(Utiles.TIPO_COMP.FACTURA_C);
            TIPO_COMP.FACTURAS.add(Utiles.TIPO_COMP.FACTURA_EXP);
            TIPO_COMP.FACTURAS.add(Utiles.TIPO_COMP.FACTURA_X);
        }
        
private void Recibos() {
            TIPO_COMP.RECIBOS.add(Utiles.TIPO_COMP.RECIBO_A);
            TIPO_COMP.RECIBOS.add(Utiles.TIPO_COMP.RECIBO_B);
            TIPO_COMP.RECIBOS.add(Utiles.TIPO_COMP.RECIBO_C);
        }
        
private void NotasCredito() {
            TIPO_COMP.NOTAS_CREDITO.add(Utiles.TIPO_COMP.NOTA_CREDITO_A);
            TIPO_COMP.NOTAS_CREDITO.add(Utiles.TIPO_COMP.NOTA_CREDITO_B);
            TIPO_COMP.NOTAS_CREDITO.add(Utiles.TIPO_COMP.NOTA_CREDITO_C);
            TIPO_COMP.NOTAS_CREDITO.add(Utiles.TIPO_COMP.NOTA_CREDITO_EXP);
            TIPO_COMP.NOTAS_CREDITO.add(Utiles.TIPO_COMP.NOTA_CREDITO_X);
        }
private void VendedoresHabilitados(){
    
        VENDEDORES_HABILITADOS.add(10);
        VENDEDORES_HABILITADOS.add(11);
        VENDEDORES_HABILITADOS.add(24);
        VENDEDORES_HABILITADOS.add(38);
        VENDEDORES_HABILITADOS.add(41);
        VENDEDORES_HABILITADOS.add(42);
        VENDEDORES_HABILITADOS.add(44);
        VENDEDORES_HABILITADOS.add(45);
        VENDEDORES_HABILITADOS.add(46);
        VENDEDORES_HABILITADOS.add(47);
        VENDEDORES_HABILITADOS.add(50);
        VENDEDORES_HABILITADOS.add(52);
        VENDEDORES_HABILITADOS.add(53);
        VENDEDORES_HABILITADOS.add(54);
        VENDEDORES_HABILITADOS.add(55);
}

}
