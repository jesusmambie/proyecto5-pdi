/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pdi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import modelo.Imagen;
import static sun.security.krb5.Confounder.bytes;
import vista.Frame;
import java.util.Arrays;

/**
 *
 * @author AnthonyLA
 */
public class PDI {
    
    private int height = 0;
    private int width = 0;
    
    private int red;
    private int green;
    private int blue;
    private int gray;
    
    private int pixel;
    // Convierte la imagen negativa.
    public BufferedImage FotoNegativa(BufferedImage img)
    {
        Imagen.setImagenOrinal(img);
        
        height = img.getHeight();
        width = img.getWidth();
        
        for (int h = 0; h<height; h++)
        {
            for (int w = 0; w<width; w++)
            {
                pixel = img.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                
                red = 255 - red;
                green = 255 - green;
                blue = 255 -  blue;
                
                pixel =  (red<<16) | (green<<8) | blue;
                
                img.setRGB(w, h, pixel);
            }
        }
        Imagen.setImagenTemporal(img);
        return (img);
    }
    
    //Convierte la imagen a escala de grises
    public BufferedImage FotoEscalaGrises(BufferedImage img)
    {
        Imagen.setImagenOrinal(img);
        
        height = img.getHeight();
        width = img.getWidth();
        
        for (int h = 0; h<height; h++)
        {
            for (int w = 0; w<width; w++)
            {
                pixel = img.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                
                gray = (red + green + blue)/3;
                
                pixel =  (gray<<16) | (gray<<8) | gray;
                
                img.setRGB(w, h, pixel);
            }
        }
        Imagen.setImagenTemporal(img);
        return (img);
    }
    
    // Convierte la imagen en blanco y negro.
    public BufferedImage FotoBlancoNegro(BufferedImage img) throws IOException
    {
        Imagen.setImagenOrinal(img);
        
        final int BLACK = 0;
        final int WHITE = 255;
        
        height = img.getHeight();
        width = img.getWidth();
        
        for(int h=0; h<height; h++)
        {
            for(int w=0; w<width; w++)
            {
                pixel = img.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                
                if(red+green+blue < 128)
                {
                    red = BLACK;
                    green = BLACK;
                    blue = BLACK;
                }else
                {
                    red = WHITE;
                    green = WHITE;
                    blue = WHITE;
                }
                
                pixel =  (red<<16) | (green <<8) | blue;
                
                img.setRGB(w, h, pixel);
            }
        }
        
        Imagen.setImagenTemporal(img);
        return img;
    }
    
    public void GuardarImagen(BufferedImage imagenGuardar) throws IOException
    {
        
        boolean[] casoGuardado = Imagen.getTipoGuardado();
        int i = 0;
        while(casoGuardado[i] != true) i++;
        
        switch(i)
        {
            // Guardar imagen negativa.
            case 0:
                BufferedImage imagenNegativa = new BufferedImage(imagenGuardar.getWidth(),imagenGuardar.getHeight(),BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics_ = imagenNegativa.createGraphics();
                graphics_.drawImage(imagenGuardar, 0, 0, null);
                ImageIO.write(imagenNegativa, "BMP", new File("imagenNegativa.bmp"));
                break;
                
                // Guardar imagen escala de grises.
            case 1:
                BufferedImage imagenGris = new BufferedImage(imagenGuardar.getWidth(),imagenGuardar.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
                Graphics2D graphics = imagenGris.createGraphics();
                graphics.drawImage(imagenGuardar, 0, 0, null);
                ImageIO.write(imagenGris, "BMP", new File("imagenEscalaGrises.bmp"));
                break;
                
                // Guardar imagen blanco y negro.
            case 2:
                BufferedImage img = Imagen.getImagenOrinal();
                final int BLACK = 0;
                final int WHITE = 255;
                
                height = img.getHeight();
                width = img.getWidth();
                
                for(int h=0; h<height; h++)
                {
                    for(int w=0; w<width; w++)
                    {
                        pixel = img.getRGB(w, h);
                        red = (pixel>>16)&0xff;
                        green = (pixel>>8)&0xff;
                        blue = pixel&0xff;
                        
                        if(red+green+blue < 128)
                        {
                            red = BLACK;
                            green = BLACK;
                            blue = BLACK;
                        }else
                        {
                            red = WHITE;
                            green = WHITE;
                            blue = WHITE;
                        }
                        
                        pixel =  (red<<16) | (green <<8) | blue;
                        
                        img.setRGB(w, h, pixel);
                    }
                }
                
                BufferedImage blancoNegroByte = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
                Graphics2D grafica = blancoNegroByte.createGraphics();
                grafica.drawImage(img, 0, 0, null);
                ImageIO.write(blancoNegroByte, "BMP", new File("imagenBlancoNegro.bmp"));
                break;
                
        }
    }
    
    // Rotar imagen.
    public BufferedImage FotoRotacion(BufferedImage img)
    {
        //Imagen.setImagen(img);
        
        height = img.getHeight();
        width = img.getWidth();
        
        BufferedImage rotatedImage = new BufferedImage(height,width,img.getType());
        for (int w=0;w<width;w++)
        {
            for (int h=0;h<height;h++)
            {
                rotatedImage.setRGB(h,width-w-1,img.getRGB(w,h));
            }
        }
        Imagen.setImagenTemporal(rotatedImage);
        return rotatedImage;
    }
    // Cuenta colores unicos
    public int FotoColoresUnicos(BufferedImage img)
    {
        Set<Integer> pixels = new HashSet<>();
        //Imagen.setImagen(img);
        
        height = img.getHeight();
        width = img.getWidth();
        
        for(int h=0; h<height; h++)
        {
            for(int w=0; w<width; w++)
            {
                pixel = img.getRGB(w, h);
                if (!pixels.contains(pixel)) {
                    pixels.add(pixel);
                }
            }
        }
        System.out.println(pixels.size());
        return pixels.size();
    }
    
    //Hace la compresion RLE
    public void CompresionRLE(BufferedImage img, String format)
    {
        int cont, width, height;
        int[][] mat = new int[2][2];
        ArrayList<Integer> compress=new ArrayList<>(); 
        switch (format) {
            case "pbm" :
            cont = 1;
            width = 1; 
            height = 1;
            mat[0][0] = 0;
            mat[1][0] = 0;
            mat[0][1] = 1;
            mat[1][1] = 1;

            for (int h=0; h<height+1; h++) {
                for(int w=1; w<=width+1; w++)
                {
                        if ((w!=width+1) && (mat[w-1][h] == mat[w][h])) {
                            cont++;
                        }else{
                            compress.add(cont);
                            compress.add(mat[w-1][h]);
                            cont = 1;
                        }
                }
            }

                for (Integer s : compress) {  //aca hay q poner a escribir en archivo
                    System.out.print(s);
                }
            break;
            
            //si es formato pgm (grayscale)
            case "pgm" :
            cont = 1;
            width = 1; 
            height = 1;
            mat[0][0] = 0;
            mat[1][0] = 0;
            mat[0][1] = 1;
            mat[1][1] = 1;

            for (int h=0; h<height+1; h++) {
                for(int w=1; w<=width+1; w++)
                {
                        if ((w!=width+1) && (mat[w-1][h] == mat[w][h])) {
                            cont++;
                        }else{
                            compress.add(cont);
                            compress.add(mat[w-1][h]);
                            cont = 1;
                        }
                }
            }

                for (Integer s : compress) {  //aca hay q poner a escribir en archivo
                    System.out.print(s);
                }
            break;
            
            //si es formato ppm (color)
            case "ppm":
            for(int h=0; h<10; h++)
            
            break;
        }
    }
    
    // Lee un byte.
    private static int LeerByte( InputStream in ) throws IOException
    {
        int b = in.read();
        if ( b == -1 )
            throw new EOFException();
        return b;
    }
    
    // Lee caracteres de un archivo netbmp ignorando espacios.
    private static char LeerCaracter( InputStream archivo ) throws IOException{
        char c;
        c = (char) LeerByte( archivo );
        if ( c == '#' )
        {
            do
            {
                c = (char) LeerByte( archivo );
            }
            while ( c != '\n' && c != '\r' );
        }
        return c;
    }
    
    // Lectura de todas las imágines Netbmp.
    public BufferedImage Netbmp(String tipoNetbmp, String ruta) throws FileNotFoundException, IOException
    {
        BufferedImage img = null;
//                new BufferedImage(0, 0, BufferedImage.TYPE_BYTE_GRAY);
        InputStream entrada = new FileInputStream(ruta);
        int rgb = 0;        // Almacenará el color.
        char c;             // Representa 1 caracter del archivo.
        ArrayList<Integer> indices = new ArrayList<Integer>();
        int i = entrada.read();
        // Leo buscando los índices.
        while(i != -1)
        {
            c = LeerCaracter(entrada);
            // CONDICIONES PARA SABER LOS INDICES.
        }
        
        width = indices.get(0);     // Indice ancho.
        height = indices.get(1);    // Indice largo.
        
        switch(tipoNetbmp.toLowerCase())
        {
            case "ppm":
                
                break;
            case "pgm":
                break;
            case "pbm":
                for(int h=0; h<height; h++)
                {
                    for(int w=0; w<width; w++)
                    {
                        c = LeerCaracter( entrada );
                        if ( c == '1' )
                        {
                            rgb = 0xff000000;
                            pixel = (rgb << 16) | (rgb << 8) | rgb ;
                            img.setRGB(w, h, pixel);
                        }            
                        else if ( c == '0' )
                        {
                            rgb = 0xffffffff;
                            pixel = (rgb << 16) | (rgb << 8) | rgb ;
                            img.setRGB(w, h, pixel);
                        }
                        else
                            throw new IOException( "bit no válido" );
                    }
                }
                break;
        }
        Imagen.setImagenTemporal(img);
        return (img);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Frame vista = new Frame();
        
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setVisible(true);
    }
    
}
