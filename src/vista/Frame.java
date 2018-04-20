/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    private int height = 0;
    private int width = 0;
    private int max_value = 255;
    private ArrayList<String> info = new ArrayList<>();
    private String global_case;
    PDI controlador = new PDI();
    BufferedImage img = null;
    BufferedImage myimg = null;
    Image imagenFinal;
    int umbral;
    int nivel_brillo;
    int nivel_contraste;

    void setImagenCargada (BufferedImage image) {
        File f = new File("imagen_cargada.png");
        try {
            ImageIO.write(image, "PNG", f);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void setImagenOriginal (BufferedImage image) {
        File f = new File("imagen_original.png");
        try {
            ImageIO.write(image, "PNG", f);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void setImagenTemporal (BufferedImage image) {
        File f = new File("imagen_temporal.png");
        try {
            ImageIO.write(image, "PNG", f);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    BufferedImage getImagenCargada () {
        BufferedImage result = null;
        try {
            result = ImageIO.read(new File("imagen_cargada.png"));
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    BufferedImage getImagenOriginal () {
        BufferedImage result = null;
        try {
            result = ImageIO.read(new File("imagen_original.png"));
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    BufferedImage getImagenTemporal () {
        BufferedImage result = null;
        try {
            result = ImageIO.read(new File("imagen_temporal.png"));
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
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
        guardarImagen = new javax.swing.JButton();
        more = new javax.swing.JButton();
        less = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        imagen.setText("jLabel1");

        javax.swing.GroupLayout CuadroLayout = new javax.swing.GroupLayout(Cuadro);
        Cuadro.setLayout(CuadroLayout);
        CuadroLayout.setHorizontalGroup(
            CuadroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CuadroLayout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        opcion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deshacer cambios", "Mostrar Información", "Histograma", "Modificar Brillo", "Modificar Contraste", "Umbralización", "Filtro promedio" }));
        opcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionActionPerformed(evt);
            }
        });

        guardarImagen.setText("Guardar");
        guardarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarImagenActionPerformed(evt);
            }
        });

        more.setText("+");
        more.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreActionPerformed(evt);
            }
        });

        less.setText("-");
        less.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lessActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Cuadro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(guardarImagen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1)
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(more)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(less)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(opcion, 0, 165, Short.MAX_VALUE)
                        .addGap(69, 69, 69))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Cuadro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(opcion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1)
                    .addComponent(guardarImagen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(more)
                    .addComponent(less))
                .addGap(42, 42, 42))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("empty-statement")
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        
        umbral = 128;
        nivel_brillo = 0;
        nivel_contraste = 0;
        more.setEnabled(false);
        less.setEnabled(false);
        PDI c = new PDI();
        boolean bandera = false;
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "bpm", "gif", "png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            
            BufferedImage img = null;
            Image imagenFinal = null;
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            int tamannoExt = path.length();
            String ext = path.substring(tamannoExt-3); // Se lee la extensión.
            
            // Si se carga una imagen Netpbm se trata de otra forma.
            if(!ext.equalsIgnoreCase("bmp")) try {
                if (ext.equals("rle")) {
                    bandera = true;
                    FileReader reader = null;
                    String line = null;
                    File read = file.getSelectedFile();
                    reader = new FileReader(read);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                
                        while((line = bufferedReader.readLine()) != null) {
                            info.add(line);
                        }
                        if (info.get(0).equals("P1")) {
                            String format = "pbm";
                            width = Character.getNumericValue(info.get(1).charAt(0));
                            height = Character.getNumericValue(info.get(1).charAt(2));
                            String ar[] = info.get(2).split("\\s+");
                            int array[] = new int[ar.length];
                            for (int i = 0; i< ar.length; i++) {
                                array[i] = Integer.parseInt(ar[i]);
                            }
                            System.out.println(format);
                            System.out.println(width + " " +height);
                            for (int i = 0; i< ar.length; i++) {
                                System.out.println(array[i] + " ");
                            }
                            System.out.println();
                            c.CargarRLE(format, array, width, height, 0);
                            File currentDirFile = new File("");
                            String helper = currentDirFile.getAbsolutePath();
                            helper.substring(0, helper.length() - 1);

                            img = pdi.Netpbm("pbm", helper +"/bitmap.pbm");
                        }
                        if (info.get(0).equals("P2")) {
                            String format = "pgm";
                            width = Character.getNumericValue(info.get(1).charAt(0));
                            height = Character.getNumericValue(info.get(1).charAt(2));
                            String[] val = info.get(2).split("\\s+");
                            max_value = Integer.parseInt(val[0]);
                            String ar[] = info.get(3).split("\\s+");
                            int array[] = new int[ar.length];
                            for (int i = 0; i< ar.length; i++) {
                                array[i] = Integer.parseInt(ar[i]);
                            }
                            System.out.println(format);
                            System.out.println(width + " " +height);
                            for (int i = 0; i< ar.length; i++) {
                                System.out.println(array[i] + " ");
                            }
                            System.out.println();
                            c.CargarRLE(format, array, width, height, max_value);
                            File currentDirFile = new File("");
                            String helper = currentDirFile.getAbsolutePath();
                            helper.substring(0, helper.length() - 1);

                            img = pdi.Netpbm("pbm", helper +"/bitmap.pgm");
                        }
                        if (info.get(0).equals("P3")) {
                            String format = "ppm";
                            width = (Character.getNumericValue(info.get(1).charAt(0)))*3;
                            height = Character.getNumericValue(info.get(1).charAt(2));
                            String[] val = info.get(2).split("\\s+");
                            max_value = Integer.parseInt(val[0]);
                            String ar[] = info.get(3).split("\\s+");
                            int array[] = new int[ar.length];
                            for (int i = 0; i< ar.length; i++) {
                                array[i] = Integer.parseInt(ar[i]);
                            }
                            System.out.println(format);
                            System.out.println(width + " " +height);
                            for (int i = 0; i< ar.length; i++) {
                                System.out.println(array[i] + " ");
                            }
                            System.out.println();
                            c.CargarRLE(format, array, width, height, max_value);
                            File currentDirFile = new File("");
                            String helper = currentDirFile.getAbsolutePath();
                            helper.substring(0, helper.length() - 1);

                            img = pdi.Netpbm("pbm", helper +"/bitmap.ppm");
                        }
                        
                } else {
                    bandera = true;
                    FileReader reader = null;
                    String line = null;
                    File read = file.getSelectedFile();
                    reader = new FileReader(read);
                    BufferedReader bufferedReader = new BufferedReader(reader);

                        while((line = bufferedReader.readLine()) != null) {
                            info.add(line);
                        }

                    img = pdi.Netpbm(ext, path);            // Buffer de la imagen Netpbm.
                }
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                URL url = new File(path).toURI().toURL();
                if (img == null && bandera == false) img = ImageIO.read(url);   // Si se leyó una Netpbm se obvia esta asignación.
                imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            } catch (MalformedURLException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            setImagenCargada(img);
            setImagenOriginal(img);
            setImagenTemporal(img);
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
        try {
            String caso = opcion.getSelectedItem().toString().toLowerCase(); // Obtengo la opción del combo box.
            global_case = caso;
            boolean[] tipoGuardado = {false,false,false};
            switch(caso)
            {
                case "deshacer cambios":
                    more.setEnabled(false);
                    less.setEnabled(false);
                    img = getImagenCargada();
                    setImagenOriginal(img);
                    setImagenTemporal(img);
                    umbral = 128;
                    nivel_brillo = 0;
                    nivel_contraste = 0;
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                    break;
                case "mostrar información":
                    more.setEnabled(false);
                    less.setEnabled(false);
                    setImagenOriginal(getImagenTemporal());
                    controlador.Information(Imagen.getImagenOrginal());
                    break;
                case "histograma":
                    more.setEnabled(false);
                    less.setEnabled(false);
                    setImagenOriginal(getImagenTemporal());
                    controlador.Histograma(getImagenOriginal());
                    break;
                case "modificar brillo":
                    more.setEnabled(true);
                    less.setEnabled(true);
                    setImagenOriginal(getImagenTemporal());
                    break;
                case "modificar contraste":
                    more.setEnabled(true);
                    less.setEnabled(true);
                    setImagenOriginal(getImagenTemporal());
                    break;
                case "umbralización":
                    more.setEnabled(true);
                    less.setEnabled(true);
                    setImagenOriginal(getImagenTemporal());
                    tipoGuardado[2]=true;       // bool para saber qué imagen guardaré en la opción "Guardar".
                    tipoGuardado[0]=false;
                    tipoGuardado[1]=false;
                    Imagen.setTipoGuardado(tipoGuardado);
                    myimg = getImagenOriginal();
                    img = controlador.FotoBlancoNegro(myimg);
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                    setImagenTemporal(img);
                    break;
                case "filtro promedio":
                    myimg = getImagenOriginal();
                    int [] array = {-7,7,-7,7};
                    img = controlador.FiltroPromedio(myimg, array);
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                    setImagenTemporal(img);
                    break;
                case "negativo":
                    tipoGuardado[0]=true;       // bool para saber qué imagen guardaré en la opción "Guardar".
                    tipoGuardado[1]=false;
                    tipoGuardado[2]=false;
                    Imagen.setTipoGuardado(tipoGuardado);
                    img = controlador.FotoNegativa(Imagen.getImagenOrginal()); // Se accede a la imagen desde el modelo.
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                    break;
                case "escala de grises":
                    tipoGuardado[1]=true;       // bool para saber qué imagen guardaré en la opción "Guardar".
                    tipoGuardado[0]=false;
                    tipoGuardado[2]=false;
                    Imagen.setTipoGuardado(tipoGuardado);
                    img = controlador.FotoEscalaGrises(Imagen.getImagenOrginal()); // Se accede a la imagen desde el modelo.
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                    break;
                case "blanco y negro":
                    tipoGuardado[2]=true;       // bool para saber qué imagen guardaré en la opción "Guardar".
                    tipoGuardado[0]=false;
                    tipoGuardado[1]=false;
                    Imagen.setTipoGuardado(tipoGuardado);
                        try {
                            img = controlador.FotoBlancoNegro(Imagen.getImagenOrginal()); // Se accede a la imagen desde el modelo.
                        } catch (IOException ex) {
                            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                    break;
                case "colores únicos":
                    controlador.FotoColoresUnicos((Imagen.getImagenTemporal() == null) ? (Imagen.getImagenOrginal()) : (Imagen.getImagenTemporal())); // Se accede a la imagen desde el modelo.
                    break;
                case "rotación":
                    BufferedImage imgTemp;
                    imgTemp = (Imagen.getImagenTemporal() == null) ? (Imagen.getImagenOrginal()) : (Imagen.getImagenTemporal());
                    img = controlador.FotoRotacion(imgTemp);
                    imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
                    imagen.setIcon(new ImageIcon(imagenFinal));
                    Cuadro.add(imagen);
                    break;
                case "compresión rle":
                    
                    int cont = 0;
                    String format;
                    if ("P1".equals(info.get(0))) {
                        format = "pbm";
                        width = Character.getNumericValue(info.get(1).charAt(0));
                        height = Character.getNumericValue(info.get(1).charAt(2));
                        int aux[] = new int[height*width];
                        int[][] mat = new int[height][width];
                        for (int i = 2; i<info.size();i++) {
                            for (int j = 0; j< info.get(i).length(); j++) {
                                if (info.get(i).charAt(j) != ' ') {
                                    aux[cont] = Character.getNumericValue(info.get(i).charAt(j));
                                    cont++;
                                }
                            }
                        }
                        cont=0;
                        for (int i = 0; i<height;i++) {
                            for (int j = 0; j<width;j++) {
                                mat[i][j] = aux[cont];
                                cont++;
                            }
                        }
                        try {
                            controlador.CompresionRLE(format, mat, width, height, max_value);
                        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        cont=0;
                    }
                    if ("P2".equals(info.get(0))) {
                        format = "pgm";
                        String temp;
                        width = Character.getNumericValue(info.get(1).charAt(0));
                        height = Character.getNumericValue(info.get(1).charAt(2));
                        String[] val = info.get(2).split("\\s+");
                        max_value = Integer.parseInt(val[0]);
                        int aux[] = new int[height*width];
                        int[][] mat = new int[height][width];
                        
                        for (int i = 3; i<info.size();i++) {
                            String[] parts = info.get(i).split("\\s+");
                            for (int j = 0; j < parts.length; j++) {
                                aux[cont] = Integer.parseInt(parts[j]);
                                cont++;
                            }
                        }
                        cont=0;
                        for (int i = 0; i<height;i++) {
                            for (int j = 0; j<width;j++) {
                                mat[i][j] = aux[cont];
                                cont++;
                            }
                        }
                        try {
                            controlador.CompresionRLE(format, mat, width, height, max_value);
                        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        cont=0;
                    }
                    if ("P3".equals(info.get(0))) {
                        format = "ppm";
                        String temp;
                        width = (Character.getNumericValue(info.get(1).charAt(0)))*3;
                        height = Character.getNumericValue(info.get(1).charAt(2));
                        String[] val = info.get(2).split("\\s+");
                        max_value = Integer.parseInt(val[0]);
                        int aux[] = new int[height*width];
                        int[][] mat = new int[height][width];
                        
                        for (int i = 3; i<info.size();i++) {
                            String[] parts = info.get(i).split("\\s+");
                            for (int j = 0; j < parts.length; j++) {
                                aux[cont] = Integer.parseInt(parts[j]);
                                cont++;
                            }
                        }
                        cont=0;
                        for (int i = 0; i<height;i++) {
                            for (int j = 0; j<width;j++) {
                                mat[i][j] = aux[cont];
                                cont++;
                            }
                        }
                        try {
                            controlador.CompresionRLE(format, mat, width, height, max_value);
                        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        cont=0;
                    }
                    break;
                default:
                    System.out.println("defecto");
            }
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }//GEN-LAST:event_opcionActionPerformed

    private void guardarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarImagenActionPerformed
        PDI controlador = new PDI();
        try {
            controlador.GuardarImagen(Imagen.getImagenTemporal());
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_guardarImagenActionPerformed

    private void moreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreActionPerformed
        if ("modificar brillo".equals(global_case)) {
            myimg = getImagenOriginal();
            if (nivel_brillo+1 > 5) { nivel_brillo=5; } else { nivel_brillo++; }
            img = controlador.ModificarBrillo(myimg, nivel_brillo); // Se accede a la imagen desde el modelo.
            imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(imagenFinal));
            Cuadro.add(imagen);
            setImagenTemporal(img);
        }
        if ("modificar contraste".equals(global_case)) {
            myimg = getImagenOriginal();
            if (nivel_contraste+1 > 4) { nivel_contraste=4; } else { nivel_contraste++; }
            img = controlador.ModificarContraste(myimg, nivel_contraste); // Se accede a la imagen desde el modelo.
            imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(imagenFinal));
            Cuadro.add(imagen);
            setImagenTemporal(img);
        }
        if ("umbralización".equals(global_case)) {
            myimg = getImagenOriginal();
            if (umbral+30>248) { umbral = 248; } else { umbral=umbral+30; }
            img = controlador.Umbralizacion(myimg, umbral); // Se accede a la imagen desde el modelo.
            imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(imagenFinal));
            Cuadro.add(imagen);
            setImagenTemporal(img);
        }
    }//GEN-LAST:event_moreActionPerformed

    private void lessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lessActionPerformed
        if ("modificar brillo".equals(global_case)) {
            myimg = getImagenOriginal();
            if (nivel_brillo-1 < -5) { nivel_brillo=-5; } else { nivel_brillo--; }
            img = controlador.ModificarBrillo(myimg, nivel_brillo); // Se accede a la imagen desde el modelo.
            imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(imagenFinal));
            Cuadro.add(imagen);
            setImagenTemporal(img);
        }
        if ("modificar contraste".equals(global_case)) {
            myimg = getImagenOriginal();
            if (nivel_contraste-1 < -4) { nivel_contraste=-4; } else { nivel_contraste--; }
            System.out.println(nivel_contraste);
            img = controlador.ModificarContraste(myimg, nivel_contraste); // Se accede a la imagen desde el modelo.
            imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(imagenFinal));
            Cuadro.add(imagen);
            setImagenTemporal(img);
        }
        if ("umbralización".equals(global_case)) {
            myimg = getImagenOriginal();
            if (umbral-30<8) { umbral = 8; } else { umbral=umbral-30; }
            img = controlador.Umbralizacion(myimg, umbral); // Se accede a la imagen desde el modelo.
            imagenFinal = img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(imagenFinal));
            Cuadro.add(imagen);
            setImagenTemporal(img);
        }
    }//GEN-LAST:event_lessActionPerformed

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
    private javax.swing.JButton guardarImagen;
    private javax.swing.JLabel imagen;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JButton less;
    private javax.swing.JButton more;
    private javax.swing.JComboBox<String> opcion;
    // End of variables declaration//GEN-END:variables
}
