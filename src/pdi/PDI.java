/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pdi;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import modelo.Imagen;
import vista.Frame;


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
    private int maxGray;
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
    public void CompresionRLE(String format, int[][] mat, int width, int height, int max_value) throws FileNotFoundException, UnsupportedEncodingException  //recibe matriz bitmap, limites de matriz y el formato(pbm,pgm,ppm)
    {
        int cont;
        ArrayList<Integer> compress=new ArrayList<>(); 
        if ("pbm".equals(format) || "pgm".equals(format)) {
            cont = 1;

            for (int h=0; h<height; h++) {
                for(int w=1; w<=width; w++)
                {
                    if ((w!=width) && (mat[h][w-1] == mat[h][w])) {
                        cont++;
                    }else{
                        compress.add(cont);
                        compress.add(mat[h][w-1]);
                        cont = 1;
                    }
                }
            }

            if ("pbm".equals(format)) {
                for (int i = 0; i<compress.size();i++) { 
                    System.out.print(compress.get(i)+" ");
                }
                System.out.println();

                try (PrintWriter writer = new PrintWriter("file_pbm.rle", "UTF-8")) {
                    writer.println("P1");
                    writer.println(width+" "+height);
                    for (int i = 0; i<compress.size();i++) { 
                        writer.print(compress.get(i)+" ");
                    }
                    writer.close();
                }
            }
            if ("pgm".equals(format)) {
                for (int i = 0; i<compress.size();i++) { 
                    System.out.print(compress.get(i)+" ");
                }
                System.out.println();

                try (PrintWriter writer = new PrintWriter("file_pgm.rle", "UTF-8")) {
                    writer.println("P2");
                    writer.println(width+" "+height);
                    writer.println(max_value);
                    for (int i = 0; i<compress.size();i++) { 
                        writer.print(compress.get(i)+" ");
                    }
                    writer.close();
                }
            }
        }
        if ("ppm".equals(format)) {
            cont = 1;

            for (int h=0; h<height; h++) {
                for(int w=3; w<=width; w=w+3)
                {
                    if ((w!=width) && (mat[h][w-3] == mat[h][w]) && (mat[h][w-2] == mat[h][w+1]) && (mat[h][w-1] == mat[h][w+2])) {
                        cont++;
                    }else{
                        compress.add(cont);
                        compress.add(mat[h][w-3]);
                        compress.add(mat[h][w-2]);
                        compress.add(mat[h][w-1]);
                        cont = 1;
                    }
                }
            }
            
            for (int i = 0; i<compress.size();i++) { 
                System.out.print(compress.get(i)+" ");
            }
            System.out.println();
            
            try (PrintWriter writer = new PrintWriter("file_ppm.rle", "UTF-8")) {
                writer.println("P3");
                writer.println(width/3+" "+height);
                writer.println(max_value);
                for (int i = 0; i<compress.size();i++) { 
                    writer.print(compress.get(i)+" ");
                }
                writer.close();
            }
        }
    }
    
    public void CargarRLE(String format, int[] arrayy, int width, int height, int max_value) throws FileNotFoundException, UnsupportedEncodingException{   //recibe limites, formato y Arreglo (imagen comprimida)
                                                                                //devuelve matriz de bitmap para imagen   
        int[][] bitmap = new int[height][width];
        int contador = 0;
        if ("pbm".equals(format) || "pgm".equals(format)) {
            int ar[] = new int[height*width];
                for (int i=0;i<arrayy.length;i=i+2){
                    for (int j=contador;j<arrayy[i]+contador;j++){
                        ar[j]=arrayy[i+1];
                    }
                    contador+=arrayy[i];
                }
                int indice = 0;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        bitmap[i][j] = ar[indice];
                        indice++;
                    }
                }
                if (format.equals("pbm")) {
                    //salida
                    System.out.println("P1");
                    System.out.println(height + " " + width);
                    for (int i = 0; i< height; i++) {
                        for (int j = 0; j < width; j++) {
                            System.out.print(bitmap[i][j] + " ");
                        }
                        System.out.println();
                    }
                } else {
                    System.out.println("P2");
                    System.out.println(height + " " + width);
                    System.out.print(max_value);
                    for (int i=0; i<height; i++) {
                        for (int j = 0; j < width; j++) {
                            System.out.print(bitmap[i][j] + " ");
                        }
                        System.out.println();
                    }
                }
        }
        if ("ppm".equals(format)) {
            int arr[] = new int[height*width];
            for (int i=0;i<arrayy.length;i=i+4){
                for (int j=0;j<arrayy[i];j++){
                    arr[contador]=arrayy[i+1];
                    arr[contador+1]=arrayy[i+2];
                    arr[contador+2]=arrayy[i+3];
                    contador+=3;
                }
            }
        
            int indice = 0;
            for (int i=0;i<height;i++) {
                for (int j = 0; j < width; j+=3) {
                    bitmap[i][j] = arr[indice];
                    bitmap[i][j+1] = arr[indice+1];
                    bitmap[i][j+2] = arr[indice+2];
                    indice+=3;
                }
            }
            System.out.println("P3");
            System.out.println(height + " " + width/3);
            System.out.print(max_value);
            for (int i=0;i<height;i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(bitmap[i][j] + " ");
                }
                System.out.println();
            }
        }
        CrearArchivoNetpbm(format, bitmap, height, width, max_value);
    }
   
    
    public void CrearArchivoNetpbm (String format, int[][]matrix, int height, int width, int max_value) throws FileNotFoundException, UnsupportedEncodingException {
        int length;
        switch (format){
            case "pbm":
            try (PrintWriter writer = new PrintWriter("bitmap.pbm", "UTF-8")) {
                writer.println("P1");
                writer.println(width+" "+height);
                for (int i=0;i<height;i++) {
                    for (int j = 0; j < width; j++) {
                        writer.print(matrix[i][j] + " ");
                    }
                    writer.println();
                }
                writer.close();
            }
            break;
            case "pgm":
            try (PrintWriter writer = new PrintWriter("bitmap.pgm", "UTF-8")) {
                writer.println("P2");
                writer.println(width+" "+height);
                writer.println(max_value);
                for (int i=0;i<height;i++) {
                    for (int j = 0; j < width; j++) {
                        length = String.valueOf(matrix[i][j]).length();
                        if (length == 1) {
                            writer.print(matrix[i][j] + "   ");
                        }
                        if (length == 2) {
                            writer.print(matrix[i][j] + "  ");
                        }
                        if (length == 3) {
                            writer.print(matrix[i][j] + " ");
                        }
                    }
                    writer.println();
                }
                writer.close();
            }
            break;
            case "ppm":
            try (PrintWriter writer = new PrintWriter("bitmap.ppm", "UTF-8")) {
                writer.println("P3");
                writer.println(width/3+" "+height);
                writer.println(max_value);
                for (int i=0;i<height;i++) {
                    for (int j = 0; j < width; j++) {
                        length = String.valueOf(matrix[i][j]).length();
                        if (length == 1) {
                            writer.print(matrix[i][j] + "   ");
                        }
                        if (length == 2) {
                            writer.print(matrix[i][j] + "  ");
                        }
                        if (length == 3) {
                            writer.print(matrix[i][j] + " ");
                        }
                    }
                    writer.println();
                }
                writer.close();
            }
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
    
    // Retorna el máximo valor del gris para PGM.
    public int getMaximoGris(BufferedReader reader) throws IOException
    {
        String c = "";
        while(reader.ready())
        {
            c = reader.readLine();
            if(!(c.contains("#")))
            {
                maxGray = Integer.parseInt(c);
                break;
            }
        }
        return maxGray;
    }
    
    
    /// Utility routine make an RGBdefault pixel from three color values.
    private static int makeRgb( int r, int g, int b )
    {
        return 0xff000000 | ( r << 16 ) | ( g << 8 ) | b;
    }
      
    // Lectura de todas las imágines Netbmp.
    public BufferedImage Netbmp(String tipoNetbmp, String ruta) throws FileNotFoundException, IOException{
                
        BufferedImage img = null;
        InputStream entrada = new FileInputStream(ruta);
        BufferedReader reader =new BufferedReader(new InputStreamReader(entrada));
        int rgb = 0;               // Almacenará el color.
        String c = "";             // Representa 1 caracter del archivo.
        ArrayList<Integer> indices = new ArrayList<Integer>();
        String[] chunks = null;
        int i = 0;
        boolean indicesListos = false;
        height = 0;
        width = 0;
        // Leo buscando los índices.
        while(reader.ready())
        {
            if(indicesListos) break;
            c = reader.readLine();
            if(!(c.contains("#") || c.contains("P")))
            {
                if(tipoNetbmp.toLowerCase().equalsIgnoreCase( "pgm") || tipoNetbmp.toLowerCase().equalsIgnoreCase( "ppm"))
                   maxGray = getMaximoGris(reader);
                chunks = c.split("\\s+");
                for(int j = 0; j < chunks.length; j++)
                {
                   if (chunks[j].isEmpty()) continue;
                   width = Integer.parseInt(chunks[j]);
                   height = Integer.parseInt(chunks[j+1]);
                   indicesListos = true;
                   break;
                }
            }
        }
        
        int q;
        switch(tipoNetbmp.toLowerCase())
        {
            case "ppm":
                img = new BufferedImage(width*3, height, BufferedImage.TYPE_INT_RGB);
                for (int h = 0; h < height; h++)
                {
                    c = reader.readLine();
                    chunks = c.split("\\s+");
                    
                    for (int w = 0; w < (chunks.length); w++)
                    {
                        red = Integer.parseInt(chunks[w]);
                        if(red!=0)red = 255-(maxGray - red);
                        green = Integer.parseInt(chunks[w+1]);
                        if(green!=0)green = 255-(maxGray - green);
                        blue = Integer.parseInt(chunks[w+2]);
                        if(blue!=0)blue = 255-(maxGray - blue);
                        
                        rgb = makeRgb(red, green, blue);
                        
                        img.setRGB(w, h, rgb);
                        img.setRGB(w+1, h, rgb);
                        img.setRGB(w+2, h, rgb);
                        w = w + 2;
                    }
                }
                break;
            case "pgm":
                img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                for (int h = 0; h < height; h++)
                {
                    c = reader.readLine();
                    chunks = c.split("\\s+");
                    
                    for (int w = 0; w < chunks.length; w++)
                    {
                        // se normaliza a 255.
                        pixel = (int) Math.round( ((double) Integer.parseInt(chunks[w])) / maxGray * 255);
                        img.setRGB(w, h, pixel);
                    }
                }
                break;
            case "pbm":
                img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY); 
                for(int h=0; h<height; h++)
                {
                    c = reader.readLine();
                    chunks = c.split("\\s+");
                    
                    for(q = 0; q < chunks.length; q++)
                    {
                        if ( chunks[q].equalsIgnoreCase("1") )
                        {
                            rgb = 0xff000000;
                            pixel = (rgb << 16) | (rgb << 8) | rgb ;
                            img.setRGB(q, h, pixel);
                        }
                        else if ( chunks[q].equalsIgnoreCase("0") )
                        {
                            rgb = 0xffffffff;
                            pixel = (rgb << 16) | (rgb << 8) | rgb ;
                            img.setRGB(q, h, pixel);
                        }
                        else
                            continue;
                    }
                    q = 0;
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
