/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.formularios;

import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.Conexion;
import ar.com.bosoft.crud.DepartamentoCRUD;
import ar.com.bosoft.crud.LocalidadCRUD;
import ar.com.bosoft.crud.OpcionCRUD;
import ar.com.bosoft.crud.ProvinciaCRUD;
import ar.com.bosoft.crud.UsuarioCRUD;
import ar.com.bosoft.crud.ZonaCRUD;
import ar.com.bosoft.data.DepartamentoData;
import ar.com.bosoft.data.LocalidadData;
import ar.com.bosoft.data.OpcionData;
import ar.com.bosoft.data.ProvinciaData;
import ar.com.bosoft.data.UsuarioData;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import matera.gui.combobox.ComboBoxMgr;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ABMUsuario extends javax.swing.JInternalFrame {
    Conexion conexion;
    DefaultTableModel modelo;
    TableRowSorter sorter;
    UsuarioCRUD usuarioCRUD;
    LocalidadCRUD localidadCRUD;
    DepartamentoCRUD departamentoCRUD;
    ProvinciaCRUD provinciaCRUD;
    OpcionCRUD opcionCRUD;
    ZonaCRUD zonaCRUD;
    ArrayList arrayZona, arrayUsuario, arrayProvincia, arrayDepartamento, arrayId_departamento, arrayLocalidad, arrayId_localidad, arrayOpcion;
    String empresa;
    int id_empresa, id_usuario, id_usuActual;
    
    /**
     * Creates new form AltaUsuario
     * @param conexion
     * @param empresa
     * @param id_empresa
     * @param id_usuario
     */
    public ABMUsuario(Conexion conexion, int id_empresa, String empresa, int id_usuario){
        this.conexion = conexion;
        this.empresa = empresa;
        this.id_empresa = id_empresa;
        this.id_usuActual = id_usuario;
        this.opcionCRUD = new OpcionCRUD(conexion, empresa);
        this.usuarioCRUD = new UsuarioCRUD(conexion, id_empresa, empresa);
        this.localidadCRUD = new LocalidadCRUD(conexion, empresa);
        this.departamentoCRUD = new DepartamentoCRUD(conexion, empresa);
        this.provinciaCRUD = new ProvinciaCRUD(conexion, empresa);
        this.zonaCRUD = new ZonaCRUD(conexion, empresa);
        
        this.arrayId_departamento = new ArrayList();
        this.arrayId_localidad = new ArrayList();
        
        initComponents();
        
        modelo = (DefaultTableModel) jTabla.getModel();
        jTabla.setModel(modelo);
        jTabla.setRowSorter (new TableRowSorter(modelo));
        sorter = new TableRowSorter(modelo);
        llenaComboProvincia();
        consultaDepartamento();
        consultaLocalidad();
        llenaComboHabilita();
        //llenaComboZona();
        ComboBoxMgr.fillZonaCombo(jComboZona, false, true);
        limpia();
        consulta();
        setJTexFieldChanged(jTxtBusca);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtBusca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jComboHabilita = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabla = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jFechaIngreso = new com.toedter.calendar.JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
        this.jFechaIngreso.getJCalendar().setTodayButtonVisible(true); 
        this.jFechaIngreso.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFechaIngreso.getJCalendar().setWeekOfYearVisible(false);
        jLabel17 = new javax.swing.JLabel();
        jFechaEgreso = new com.toedter.calendar.JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
        this.jFechaEgreso.getJCalendar().setTodayButtonVisible(true); 
        this.jFechaEgreso.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFechaEgreso.getJCalendar().setWeekOfYearVisible(false);
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        jBtnMostrar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPassword2 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jTxtEmail = new javax.swing.JTextField();
        jTxtEmail.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(45,false));
        jCheckUsaSistema = new javax.swing.JCheckBox();
        jTxtNombre = new javax.swing.JTextField();
        jTxtNombre.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(30,true));
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtDni = new javax.swing.JTextField();
        jTxtDni.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(10,true));
        jLabel9 = new javax.swing.JLabel();
        try{
            jFmtCuit = ar.com.bosoft.formatosCampos.ConMascara.class.newInstance().getjFmt("##-########-#",' ',true);
        }catch (Exception ex){
            Utiles.enviaError(this.empresa,ex);
        }
        jLabel10 = new javax.swing.JLabel();
        jFechaNac = new com.toedter.calendar.JDateChooser();
        this.jFechaNac.getJCalendar().setTodayButtonVisible(true); 
        this.jFechaNac.getJCalendar().setTodayButtonText("Hoy"); 
        this.jFechaNac.getJCalendar().setWeekOfYearVisible(false);
        jLabel11 = new javax.swing.JLabel();
        jTxtDireccion = new javax.swing.JTextField();
        jTxtDireccion.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(100,false));
        jLabel12 = new javax.swing.JLabel();
        jComboProvincia = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jComboDepartamento = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jComboLocalidad = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jTxtCodPostal = new javax.swing.JTextField();
        jTxtCodPostal.setDocument(new ar.com.bosoft.formatosCampos.JTxtFieldLimitado(10,true));
        jLabel18 = new javax.swing.JLabel();
        jComboZona = new javax.swing.JComboBox();

        setTitle("ABM Personal");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bosoft/Bosoft.png"))); // NOI18N

        jLabel5.setText("* Habilitado");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        jTxtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtBuscaFocusGained(evt);
            }
        });

        jLabel6.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtBusca, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("(*)Datos Obligatorios");

        jBtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/limpiar.png"))); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.setBorderPainted(false);
        jBtnLimpiar.setContentAreaFilled(false);
        jBtnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnLimpiar.setFocusPainted(false);
        jBtnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/limpiar.png"))); // NOI18N
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/guardar.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.setContentAreaFilled(false);
        jBtnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnGuardar.setFocusPainted(false);
        jBtnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/guardar.png"))); // NOI18N
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/enabled/salir.png"))); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.setBorderPainted(false);
        jBtnSalir.setContentAreaFilled(false);
        jBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnSalir.setFocusPainted(false);
        jBtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBtnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/32x32/rollover/salir.png"))); // NOI18N
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jComboHabilita.setNextFocusableComponent(jFechaIngreso);
        jComboHabilita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboHabilitaActionPerformed(evt);
            }
        });

        jTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Hab."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabla);
        if (jTabla.getColumnModel().getColumnCount() > 0) {
            jTabla.getColumnModel().getColumn(1).setMinWidth(50);
            jTabla.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTabla.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        jLabel16.setText("Fecha ingreso");

        jFechaIngreso.setNextFocusableComponent(jFechaEgreso);

        jLabel17.setText("Fecha egreso");

        jFechaEgreso.setNextFocusableComponent(jCheckUsaSistema);

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("* Contraseña");

        jPassword.setNextFocusableComponent(jPassword2);
        jPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordFocusLost(evt);
            }
        });

        jBtnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconos/16x16/enabled/eye_icon&16.png"))); // NOI18N
        jBtnMostrar.setBorderPainted(false);
        jBtnMostrar.setContentAreaFilled(false);
        jBtnMostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnMostrar.setFocusPainted(false);
        jBtnMostrar.setFocusable(false);
        jBtnMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBtnMostrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBtnMostrarMouseExited(evt);
            }
        });

        jLabel4.setText("* Repita la contraseña");

        jPassword2.setNextFocusableComponent(jTxtEmail);
        jPassword2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPassword2FocusGained(evt);
            }
        });

        jLabel8.setText("Email de contacto");

        jTxtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtEmailFocusLost(evt);
            }
        });

        jCheckUsaSistema.setText("Usa sistema");
        jCheckUsaSistema.setContentAreaFilled(false);
        jCheckUsaSistema.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckUsaSistema.setNextFocusableComponent(jPassword);
        jCheckUsaSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckUsaSistemaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jCheckUsaSistema))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTxtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPassword2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPassword, jPassword2});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jBtnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckUsaSistema)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTxtNombre.setNextFocusableComponent(jTxtDni);
        jTxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNombreFocusGained(evt);
            }
        });

        jLabel1.setText("* Nombre");

        jLabel2.setText("DNI");

        jTxtDni.setNextFocusableComponent(jFmtCuit);
        jTxtDni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDniFocusGained(evt);
            }
        });

        jLabel9.setText("CUIL");

        jFmtCuit.setNextFocusableComponent(jFechaNac);

        jLabel10.setText("Fecha nac.");

        jFechaNac.setNextFocusableComponent(jTxtDireccion);

        jLabel11.setText("Dirección");

        jTxtDireccion.setNextFocusableComponent(jComboProvincia);
        jTxtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtDireccionActionPerformed(evt);
            }
        });
        jTxtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtDireccionFocusGained(evt);
            }
        });

        jLabel12.setText("Provincia");

        jComboProvincia.setNextFocusableComponent(jComboDepartamento);
        jComboProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboProvinciaActionPerformed(evt);
            }
        });
        jComboProvincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboProvinciaKeyReleased(evt);
            }
        });

        jLabel13.setText("Departamento");

        jComboDepartamento.setNextFocusableComponent(jComboLocalidad);
        jComboDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDepartamentoActionPerformed(evt);
            }
        });
        jComboDepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboDepartamentoKeyReleased(evt);
            }
        });

        jLabel14.setText("Localidad");

        jComboLocalidad.setNextFocusableComponent(jTxtCodPostal);
        jComboLocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboLocalidadKeyReleased(evt);
            }
        });

        jLabel15.setText("C.P.");

        jTxtCodPostal.setNextFocusableComponent(jComboHabilita);
        jTxtCodPostal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtCodPostalFocusGained(evt);
            }
        });

        jLabel18.setText("Sucursal");

        jComboZona.setNextFocusableComponent(jTxtCodPostal);
        jComboZona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboZonaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(239, 239, 239)
                                .addComponent(jBtnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnLimpiar))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel18))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTxtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                                    .addComponent(jTxtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jFmtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTxtDireccion)
                                                    .addComponent(jComboProvincia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jComboDepartamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jComboLocalidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel17)
                                                        .addComponent(jLabel16))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jFechaEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel16))
                    .addComponent(jFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel17))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFmtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jComboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jComboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTxtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboHabilita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFechaEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnGuardar)
                        .addComponent(jBtnLimpiar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jComboZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnSalir)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void llenaComboProvincia(){
        arrayProvincia = provinciaCRUD.consulta();
        Iterator i = arrayProvincia.iterator();
        while (i.hasNext()){
            ProvinciaData tmp = (ProvinciaData) i.next();
            this.jComboProvincia.addItem(tmp.getNombre());
        }
    }
    
    private void consultaDepartamento(){
        arrayDepartamento = departamentoCRUD.consulta();
    }
    
    private void llenaComboDepartamento(int id_provincia){
        this.jComboDepartamento.removeAllItems();
        this.arrayId_departamento.clear();
        
        if (id_provincia != 0) {
            Iterator i = arrayDepartamento.iterator();
            while (i.hasNext()) {
                DepartamentoData tmp = (DepartamentoData) i.next();
                if (tmp.getId_provincia() == id_provincia){
                    this.arrayId_departamento.add(tmp.getId_departamento());
                    this.jComboDepartamento.addItem(tmp.getNombre());
                }   
            }
        }
        this.jComboDepartamento.setSelectedIndex(-1);
    }
    
    private void consultaLocalidad(){
        arrayLocalidad = localidadCRUD.consulta();
    }
    
    private void llenaComboLocalidad(int id_departamento){
        this.jComboLocalidad.removeAllItems();
        this.arrayId_localidad.clear();
        
        if (id_departamento != 0){
            Iterator i = this.arrayLocalidad.iterator();
            while (i.hasNext()) {
                LocalidadData tmp = (LocalidadData) i.next();
                if (tmp.getId_departamento() == id_departamento){
                    this.arrayId_localidad.add(tmp.getId_localidad());
                    this.jComboLocalidad.addItem(tmp.getNombre());
                }   
            }
        }
        this.jComboLocalidad.setSelectedIndex(-1);
    }
    
    private void llenaComboHabilita(){
        arrayOpcion = opcionCRUD.consulta(2);
        Iterator i = arrayOpcion.iterator();
        while (i.hasNext()){
            OpcionData tmp = (OpcionData) i.next();
            jComboHabilita.addItem(tmp.getNombre());
        }
    }
    /*
    private void llenaComboZona(){
        this.jComboBoxSucursal.addItem("-- TODAS --");
        arrayZona = zonaCRUD.consulta(true);
        Iterator i = arrayZona.iterator();
        while (i.hasNext()){
            ZonaData z = (ZonaData) i.next();
            this.jComboBoxSucursal.addItem(z.getNombre());
        }
    }
    */
    private void limpia(){
        this.jTxtNombre.setText("");
        this.jTxtDni.setText("");
        this.jFmtCuit.setValue("");
        this.jFechaNac.setDate(null);
        this.jTxtDireccion.setText("");
        this.jTxtCodPostal.setText("");
        this.jComboProvincia.setSelectedIndex(-1);
        this.jComboDepartamento.setSelectedIndex(-1);
        this.jComboLocalidad.setSelectedIndex(-1);
        this.jComboZona.setSelectedIndex(-1);
        this.jComboHabilita.setSelectedIndex(0);
        this.jFechaIngreso.setDate(null);
        this.jFechaEgreso.setDate(null);
        this.jCheckUsaSistema.setSelected(false);
        this.jCheckUsaSistema.setEnabled(true);
        this.jPassword.setText("");
        this.jPassword2.setText("");
        this.jTxtEmail.setText("");
        this.jTxtBusca.setText("");
        id_usuario = 0;
    }
    
    private void consulta(){
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        
        arrayUsuario = usuarioCRUD.consulta(false, false);
        Iterator i = arrayUsuario.iterator();
        while (i.hasNext()){
            UsuarioData tmp = (UsuarioData) i.next();
            String nombre = tmp.getNombre();
            String habilitado = tmp.getHabilita().equals("S") ? "SI" : "NO";
            Object [] nuevo = {nombre,habilitado};
            modelo.addRow(nuevo);
        }
    }
    
    private void setJTexFieldChanged(JTextField txt){
        DocumentListener documentListener = new DocumentListener() {
 
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
 
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                printIt(documentEvent);
            }
        };
        txt.getDocument().addDocumentListener(documentListener); 
    }
 
    private void printIt(DocumentEvent documentEvent) {
        DocumentEvent.EventType type = documentEvent.getType();
 
        if (type.equals(DocumentEvent.EventType.CHANGE)){
 
        }else if (type.equals(DocumentEvent.EventType.INSERT)){
            JTxtBuscaChanged();
        }else if (type.equals(DocumentEvent.EventType.REMOVE)){
            JTxtBuscaChanged();
        }
    }
 
    private void JTxtBuscaChanged(){
        String text = jTxtBusca.getText().trim().replaceAll("'", "´");
        if (text.length() == 0) {
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter("(?i).*" +text+ ".*"));
          jTabla.setRowSorter(sorter);
        }
    }

    private boolean validaObligatorios(){
        if (this.jTxtNombre.getText().equals("")) {            
            return false;
        }else{
            if (this.jCheckUsaSistema.isSelected()) {
                if (this.jPassword.getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean comparaPass(){
        return this.jPassword.getText().trim().equals(this.jPassword2.getText().trim());
    }
    
    private void jPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFocusLost
        if (this.jPassword.getText().length()>0){
            if (this.jPassword.getText().length()<6 || this.jPassword.getText().length()>15){
                JOptionPane.showMessageDialog(this,"La contraseña debe tener entre 6 y 15 caracteres", "Información",JOptionPane.INFORMATION_MESSAGE);
                this.jPassword.setText("");
                this.jPassword2.setText("");
                this.jPassword.requestFocus();
            }else if (!Utiles.SoloAlfanumerico(this.jPassword.getText().trim())){
                JOptionPane.showMessageDialog(this,"La contraseña sólo debe tener caracteres alfanuméricos", "Información",JOptionPane.INFORMATION_MESSAGE);
                this.jPassword.setText("");
                this.jPassword2.setText("");
                this.jPassword.requestFocus();
            }
        }
    }//GEN-LAST:event_jPasswordFocusLost

    private void jBtnMostrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrarMouseEntered
        if (this.jBtnMostrar.isEnabled()){
            this.jPassword.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_jBtnMostrarMouseEntered

    private void jBtnMostrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnMostrarMouseExited
        this.jPassword.setEchoChar('\u25cf');
    }//GEN-LAST:event_jBtnMostrarMouseExited

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpia();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        if (validaObligatorios()){
            if (this.jCheckUsaSistema.isSelected()) {
                if (!comparaPass()) {
                    JOptionPane.showMessageDialog(this,"Las contraseñas no coinciden", "Información",JOptionPane.INFORMATION_MESSAGE);
                    this.jPassword.setText("");
                    this.jPassword2.setText("");
                    this.jPassword.requestFocus();
                    return;
                } 
                
                if (!this.jTxtEmail.getText().isEmpty()){
                    if (!Utiles.esEmail(this.jTxtEmail.getText().trim())){
                        JOptionPane.showMessageDialog(this, "El e-mail de contacto no es válido", "Atención", JOptionPane.INFORMATION_MESSAGE);
                        this.jTxtEmail.requestFocus();
                        return;
                    }
                }
            }
            String nombre = this.jTxtNombre.getText();
            String documento = this.jTxtDni.getText();
            String cuil = this.jFmtCuit.getText().replace("-","");
            Date fechaNac = this.jFechaNac.getDate();
            String direccion = this.jTxtDireccion.getText();
            String codPostal = this.jTxtCodPostal.getText();
            
            int id_provincia = 0;
            if (this.jComboProvincia.getSelectedIndex() >= 0){
                ProvinciaData p = (ProvinciaData) arrayProvincia.get(this.jComboProvincia.getSelectedIndex());
                id_provincia = p.getId_provincia();
            }
            
            int id_departamento = 0;
            if (this.jComboDepartamento.getSelectedIndex() >= 0){
                id_departamento = (int) this.arrayId_departamento.get(this.jComboDepartamento.getSelectedIndex());            
            }
            
            int id_localidad = 0;
            if (this.jComboLocalidad.getSelectedIndex() >= 0){
                id_localidad = (int) this.arrayId_localidad.get(this.jComboLocalidad.getSelectedIndex());                
            }            
            
            Date fechaIngreso = this.jFechaIngreso.getDate();
            Date fechaEgreso = this.jFechaEgreso.getDate();
            String usaSistema = (this.jCheckUsaSistema.isSelected() ? "S" : "N");
            String contraseña = this.jPassword.getText();
            String email = this.jTxtEmail.getText();
            String habilita = this.jComboHabilita.getSelectedItem().toString().trim().substring(0, 1);

            if (habilita.equals("N") && this.id_usuActual == this.id_usuario){
                JOptionPane.showMessageDialog(this,"No puede deshabilitar su usuario", "Información",JOptionPane.INFORMATION_MESSAGE);
                limpia();
                return;
            }

            int id_zona = 0;
            if (this.jComboZona.getSelectedIndex() > 0) {
                id_zona = ComboBoxMgr.getSelectedId(jComboZona);
            }
            
            UsuarioData u = new UsuarioData();
            u.setId_usuario(this.id_usuario);
            u.setNombre(nombre);
            u.setDocumento(documento);
            u.setCuil(cuil);
            u.setFechaNac(fechaNac);
            u.setDireccion(direccion);
            u.setCodPostal(codPostal);
            u.setId_provincia(id_provincia);
            u.setId_departamento(id_departamento);
            u.setId_localidad(id_localidad);
            u.setFechaIngreso(fechaIngreso);
            u.setFechaEgreso(fechaEgreso);
            u.setUsaSistema(usaSistema);
            u.setContraseña(contraseña);
            u.setEmail(email);
            u.setId_zona(id_zona);
            u.setHabilita(habilita);

            usuarioCRUD.insert(u);

            limpia();
            consulta();     
        }else{
            JOptionPane.showMessageDialog(this,"Complete todos los datos obligatorios (*)", "Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaMouseClicked
        int indiceModelo = jTabla.convertRowIndexToModel (jTabla.getSelectedRow());
        UsuarioData u = (UsuarioData) arrayUsuario.get(indiceModelo);
        this.id_usuario = u.getId_usuario();
        this.jTxtNombre.setText(u.getNombre());
        this.jTxtDni.setText(u.getDocumento());
        this.jFmtCuit.setText(u.getCuil());
        this.jTxtDni.setText(u.getDocumento());
        this.jFechaNac.setDate(u.getFechaNac());
        this.jTxtDireccion.setText(u.getDireccion());
        this.jTxtCodPostal.setText(u.getCodPostal());
        
        this.jComboProvincia.setSelectedIndex(-1);
        this.jComboProvincia.setSelectedItem(u.getProvincia());
        this.jComboProvinciaActionPerformed(null);
        this.jComboZona.setSelectedIndex(u.getId_zona());
        
        
        this.jComboDepartamento.setSelectedIndex(-1);
        this.jComboDepartamento.setSelectedItem(u.getDepartamento());
        this.jComboDepartamentoActionPerformed(null);
        
        this.jComboLocalidad.setSelectedIndex(-1);
        this.jComboLocalidad.setSelectedItem(u.getLocalidad());
        
        this.jFechaIngreso.setDate(u.getFechaIngreso());
        this.jFechaEgreso.setDate(u.getFechaEgreso());
        
        this.jCheckUsaSistema.setSelected(u.getUsaSistema().equals("S"));
        this.jCheckUsaSistemaActionPerformed(null);
        
        this.jPassword.setText(u.getContraseña());
        this.jPassword2.setText(u.getContraseña());
        this.jTxtEmail.setText(u.getEmail());
        this.jComboHabilita.setSelectedItem(u.getHabilita().equals("S") ? "SI" : "NO");
        this.jTxtNombre.requestFocus();
    }//GEN-LAST:event_jTablaMouseClicked

    private void jTxtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtEmailFocusLost
        
    }//GEN-LAST:event_jTxtEmailFocusLost

    private void jTxtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNombreFocusGained
        this.jTxtNombre.select(0, this.jTxtNombre.getText().length());
    }//GEN-LAST:event_jTxtNombreFocusGained

    private void jPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFocusGained
        this.jPassword.select(0, this.jPassword.getText().length());
    }//GEN-LAST:event_jPasswordFocusGained

    private void jPassword2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPassword2FocusGained
        this.jPassword2.select(0, this.jPassword2.getText().length());
    }//GEN-LAST:event_jPassword2FocusGained

    private void jTxtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtEmailFocusGained
        this.jTxtEmail.select(0, this.jTxtEmail.getText().length());
    }//GEN-LAST:event_jTxtEmailFocusGained

    private void jTxtBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBuscaFocusGained
        this.jTxtBusca.select(0, this.jTxtBusca.getText().length());
    }//GEN-LAST:event_jTxtBuscaFocusGained

    private void jTxtDniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDniFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDniFocusGained

    private void jTxtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDireccionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDireccionFocusGained

    private void jComboProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboProvinciaActionPerformed
        if (this.jComboProvincia.getSelectedIndex() >= 0) {
            ProvinciaData tmp = (ProvinciaData) this.arrayProvincia.get(this.jComboProvincia.getSelectedIndex());
            try{
                this.llenaComboDepartamento(tmp.getId_provincia());
                this.llenaComboLocalidad(0);
            } catch (Exception ex){}
        }
    }//GEN-LAST:event_jComboProvinciaActionPerformed

    private void jComboProvinciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboProvinciaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboProvincia.setSelectedIndex(-1);
            this.jComboDepartamento.setSelectedIndex(-1);
            this.jComboLocalidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboProvinciaKeyReleased

    private void jComboDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDepartamentoActionPerformed
        if (this.jComboDepartamento.getSelectedIndex() >= 0) {
            int id_departamento = (int) this.arrayId_departamento.get(this.jComboDepartamento.getSelectedIndex());
            try{
                this.llenaComboLocalidad(id_departamento);
            } catch (Exception ex){}
        }
    }//GEN-LAST:event_jComboDepartamentoActionPerformed

    private void jComboDepartamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboDepartamentoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboDepartamento.setSelectedIndex(-1);
            this.jComboLocalidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboDepartamentoKeyReleased

    private void jComboLocalidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboLocalidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            this.jComboLocalidad.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jComboLocalidadKeyReleased

    private void jTxtCodPostalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCodPostalFocusGained
        this.jTxtCodPostal.select(0, this.jTxtCodPostal.getText().length());
    }//GEN-LAST:event_jTxtCodPostalFocusGained

    private void jCheckUsaSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckUsaSistemaActionPerformed
        this.jPassword.setEnabled(this.jCheckUsaSistema.isSelected());
        this.jPassword2.setEnabled(this.jCheckUsaSistema.isSelected());
        this.jTxtEmail.setEnabled(this.jCheckUsaSistema.isSelected());        
    }//GEN-LAST:event_jCheckUsaSistemaActionPerformed

    private void jComboHabilitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboHabilitaActionPerformed
        this.jCheckUsaSistema.setSelected(this.jComboHabilita.getSelectedItem().toString().equals("SI"));
        this.jCheckUsaSistema.setEnabled(this.jComboHabilita.getSelectedItem().toString().equals("SI"));
        this.jCheckUsaSistemaActionPerformed(null);
    }//GEN-LAST:event_jComboHabilitaActionPerformed

    private void jTxtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDireccionActionPerformed

    private void jComboZonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboZonaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboZonaKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnMostrar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JCheckBox jCheckUsaSistema;
    private javax.swing.JComboBox jComboDepartamento;
    private javax.swing.JComboBox jComboHabilita;
    private javax.swing.JComboBox jComboLocalidad;
    private javax.swing.JComboBox jComboProvincia;
    private javax.swing.JComboBox jComboZona;
    private com.toedter.calendar.JDateChooser jFechaEgreso;
    private com.toedter.calendar.JDateChooser jFechaIngreso;
    private com.toedter.calendar.JDateChooser jFechaNac;
    private javax.swing.JFormattedTextField jFmtCuit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JPasswordField jPassword2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabla;
    private javax.swing.JTextField jTxtBusca;
    private javax.swing.JTextField jTxtCodPostal;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtDni;
    private javax.swing.JTextField jTxtEmail;
    private javax.swing.JTextField jTxtNombre;
    // End of variables declaration//GEN-END:variables
}
