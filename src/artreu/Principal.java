/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artreu;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import weka.core.SerializationHelper;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.filters.unsupervised.instance.imagefilter.ColorLayoutFilter;
import weka.filters.unsupervised.attribute.Remove;
/**
 *
 * @author rabdos7
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        this.setResizable(false); 
    }
    
    public static String directorio = "";
    public static String archivo = "";
    public static String ruta = "";
    public static String parcial = "";
    boolean estado = true;
    
    private void guardarResultado(String resultado){
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(parcial+".txt");
            pw = new PrintWriter(fichero);
            pw.println("Edad: "+jftfEdad.getText()+"\n"+
                        "Fuerza: "+jftfFuerza.getText()+"\n"+
                        "Resultado: "+resultado);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    private void diagnostico() throws Exception{
        if (archivo.length()<=0) {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una imagen", "Advertencia", JOptionPane.WARNING_MESSAGE);     
        }else if (jftfEdad.getText().length()<=0) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese la edad", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }else if (jftfFuerza.getText().length()<=0) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese la fuerza", "Advertencia", JOptionPane.WARNING_MESSAGE);    
        }else{
        RandomForest rforest = (RandomForest) SerializationHelper.read("src/assets/rforest.model");
       
       Attribute imagen = new Attribute("Filename",true);
       Attribute edad = new Attribute("Edad");
       Attribute fuerza = new Attribute("Fuerza");
      
       ArrayList <Attribute> atributos = new <Attribute> ArrayList();
       ArrayList <String> clases = new <String> ArrayList();
       clases.add("DiagnosticoConAR");
       clases.add("DiagnosticoSinAR");
       atributos.add(imagen);
       atributos.add(edad);
       atributos.add(fuerza);
      
       Attribute clase = new Attribute("Diagnostico",clases);
      atributos.add(clase);
      
      Instances data = new Instances("ARWEKA",atributos,0);
      double [] instancia = new double[data.numAttributes()];
      instancia[0] = data.attribute("Filename").addStringValue(archivo);
      instancia[1] = Integer.parseInt(jftfEdad.getText());
      instancia[2] = Float.parseFloat(jftfFuerza.getText());
        
      data.add(new DenseInstance(1.0,instancia));
      
     ColorLayoutFilter color = new ColorLayoutFilter ();
     Remove remove = new Remove();
      String [] opsColor = new String []{"-D",directorio};
     int [] indices = new int[]{4,6,13,18,22,24,32,34,35,36};
     color.setOptions(opsColor);
     remove.setAttributeIndicesArray(indices);
     remove.setInvertSelection(true);
     color.setInputFormat(data);
     Instances colorData = ColorLayoutFilter.useFilter(data, color);
     remove.setInputFormat(colorData);
     Instances newData = Remove.useFilter(colorData, remove);
     
     newData.setClassIndex(newData.numAttributes()-1);
 
     Instance inst = newData.instance(0);
     double pred = rforest.classifyInstance(inst);
            if (pred == 0.0) {
                lblDiagnostico.setText("Alta probabilidad de Artritis");
                guardarResultado("Alta probabilidad de Artritis");
            }else if (pred == 1.0){
            lblDiagnostico.setText("Baja probabilidad de Artritis");
            guardarResultado("Baja probabilidad de Artritis");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnFoto = new javax.swing.JButton();
        btnDiagnostico = new javax.swing.JButton();
        lblEdad = new javax.swing.JLabel();
        lblFuerza = new javax.swing.JLabel();
        foto = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblDiagnostico = new javax.swing.JLabel();
        jftfEdad = new javax.swing.JFormattedTextField();
        jftfFuerza = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        btnFoto.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        btnFoto.setText("Foto");
        btnFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFotoActionPerformed(evt);
            }
        });

        btnDiagnostico.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        btnDiagnostico.setText("Pre-diagnostico");
        btnDiagnostico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosticoActionPerformed(evt);
            }
        });

        lblEdad.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        lblEdad.setText("Edad");

        lblFuerza.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        lblFuerza.setText("Fuerza");

        foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTitulo.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblTitulo.setText("Artritis Reumatoide");

        lblDiagnostico.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N

        jftfEdad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDiagnostico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFuerza, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEdad, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jftfEdad)
                                    .addComponent(jftfFuerza, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDiagnostico)
                        .addGap(42, 42, 42))))
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(lblTitulo)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo)
                        .addGap(23, 23, 23)
                        .addComponent(foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEdad)
                            .addComponent(jftfEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFuerza)
                            .addComponent(jftfFuerza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDiagnostico)
                    .addComponent(btnFoto))
                .addContainerGap())
        );

        btnFoto.getAccessibleContext().setAccessibleName("jbFoto");
        btnDiagnostico.getAccessibleContext().setAccessibleName("btnDiagnostico");
        lblEdad.getAccessibleContext().setAccessibleName("lblEdad");
        lblFuerza.getAccessibleContext().setAccessibleName("lblFuerza");
        lblTitulo.getAccessibleContext().setAccessibleName("lblTitulo");
        lblDiagnostico.getAccessibleContext().setAccessibleName("lblDiagnostico");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotoActionPerformed
        // TODO add your handling code here:
        ruta = "";
        directorio = "";
        archivo ="";
        parcial ="";
        foto.setIcon(null);
        jftfEdad.setText("");
        jftfFuerza.setText("");
        lblDiagnostico.setText("");
        this.repaint();
        String [] options = {"Elegir desde archivo","Tomar foto"};
        int seleccion = JOptionPane.showOptionDialog(null,"Elija la fuente de la imagen","Seleccionar imagen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (seleccion == 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes", ImageIO.getReaderFileSuffixes());
            chooser.setFileFilter(filtro);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            if (f!=null) {
            directorio = chooser.getCurrentDirectory().toString();
            archivo = f.getName();
            ruta = chooser.getSelectedFile().toString();
            String temp = f.getAbsolutePath();
            String t = "";
            char [] c = temp.toCharArray();
                int max = temp.lastIndexOf(".");
                for (int i = 0; i < max; i++) {
                    t+=c[i];
                }
                parcial = t;
            ImageIcon imagen = new ImageIcon(f.toString());
            Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_DEFAULT));
            foto.setIcon(icono);
            this.repaint();
            }
        }
        if (seleccion == 1) {
        estado = false;
        Foto ventana = new Foto ();
        ventana.setTitle("Pre-Diagnostico Artritis Reumatoide");
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);    
        }
    }//GEN-LAST:event_btnFotoActionPerformed

    private void btnDiagnosticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosticoActionPerformed
        try {
            // TODO add your handling code here:
            diagnostico();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDiagnosticoActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
            ImageIcon imagen = new ImageIcon(ruta);
            Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_DEFAULT));
            foto.setIcon(icono);
            this.repaint();
    }//GEN-LAST:event_formWindowGainedFocus

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDiagnostico;
    private javax.swing.JButton btnFoto;
    private javax.swing.JLabel foto;
    private javax.swing.JFormattedTextField jftfEdad;
    private javax.swing.JFormattedTextField jftfFuerza;
    private javax.swing.JLabel lblDiagnostico;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblFuerza;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
