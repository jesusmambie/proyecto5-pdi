/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Imagen;
import pdi.PDI;

/**
 *
 * @author AnthonyLA
 */
public class Frame extends javax.swing.JFrame {

    private PDI pdi = new PDI();

    /**
     * Creates new form Frame
     */
    public Frame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Cuadro = new javax.swing.JPanel();
        imagen = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        opcion = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        imagen.setText("jLabel1");

        javax.swing.GroupLayout CuadroLayout = new javax.swing.GroupLayout(Cuadro);
        Cuadro.setLayout(CuadroLayout);
        CuadroLayout.setHorizontalGroup(
            CuadroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CuadroLayout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(205, Short.MAX_VALUE))
        );
        CuadroLayout.setVerticalGroup(
            CuadroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CuadroLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jToggleButton1.setText("Cargar");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jToggleButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jToggleButton1KeyPressed(evt);
            }
        });

        opcion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Negativo", "Escala de grises", "Blanco y negro", "Colores únicos", "Rotacion" }));
        opcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionActionPerformed(evt);
            }
        });

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jToggleButton1)
                .addGap(34, 34, 34)
                .addComponent(opcion, 0, 159, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addGap(133, 133, 133))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Cuadro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Cuadro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(opcion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton1))
                .addGap(83, 83, 83))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "bpm", "gif", "png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            BufferedImage img = null;
            Image imagenFinal = null;
            try {
                URL url = new File(path).toURI().toURL();
                img = ImageIO.read(url);
                imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            } catch (MalformedURLException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            Imagen imagenModelo = new Imagen();
            imagenModelo.setImagen(img); // Se guarda la imagen en el modelo
            imagen.setIcon(new ImageIcon(imagenFinal));
            Cuadro.add(imagen);
            Cuadro.setVisible(true);
            
        } //if the user click on save in Jfilechooser
        else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No File Select");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jToggleButton1KeyPressed
        // 
    }//GEN-LAST:event_jToggleButton1KeyPressed

    private void opcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionActionPerformed
        String caso = opcion.getSelectedItem().toString().toLowerCase(); // Obtengo la opción del combo box.
        PDI controlador = new PDI();
        BufferedImage img;
        Image imagenFinal;
        switch(caso)
        {
            case "negativo":
                    img = controlador.FotoNegativa(new Imagen().getImagen()); // Se accede a la imagen desde el modelo.
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                break;
                case "escala de grises":
                    img = controlador.FotoEscalaGrises(new Imagen().getImagen()); // Se accede a la imagen desde el modelo.
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                break;
                case "blanco y negro":
                    img = controlador.FotoBlancoNegro(new Imagen().getImagen()); // Se accede a la imagen desde el modelo.
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                break;
                case "colores únicos":
                    int cont = controlador.FotoColoresUnicos(new Imagen().getImagen()); // Se accede a la imagen desde el modelo.
                    System.out.print(cont);
                break;
                case "rotacion":
                    img = controlador.FotoRotacion(new Imagen().getImagen()); // Se accede a la imagen desde el modelo.
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                break;
                default:
                    System.out.println("defecto");
        }
            
    }//GEN-LAST:event_opcionActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cuadro;
    private javax.swing.JLabel imagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JComboBox<String> opcion;
    // End of variables declaration//GEN-END:variables
}
