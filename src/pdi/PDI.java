/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pdi;

import java.util.Arrays;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RescaleOp;
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
import java.util.Arrays;
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
    private float cantidad;
    private float cantidad2;
    
    //returns image information in console
    public void Information(BufferedImage img)
    {
        width = img.getWidth();
        height = img.getHeight();
        ColorModel model = img.getColorModel();
        int bpp = model.getPixelSize();
        Set<Integer> pixels = new HashSet<>();
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
        double diagonal_pixels = Math.sqrt(Math.pow(width,2)+Math.pow(height,2)); 
        //pixels per inch are diagonal pixes / diagonal inches
        //aun no tenemos diagonal inches
        System.out.println("Dimensiones: "+width+"x"+height);
        System.out.println("Bits por pixel: "+bpp);
        System.out.println("Colores unicos: "+pixels.size());
        System.out.println("Dots per inch: ");
    }
    
    //exports histogram of image
    public void Histograma(BufferedImage img) throws IOException
    {
        height = img.getHeight();
        width = img.getWidth();
        BufferedImage histogram = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        int[] grays = new int[height*width];
        int[] histogram_array = new int[256];
        Arrays.fill(histogram_array, 0);
        int i = 0, limit;
        
        for (int h = 0; h<height; h++) {
            for (int w = 0; w<width; w++) {
                pixel = img.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                
                gray = (red + green + blue)/3;
                grays[i]=gray;
                i++;
            }
        }
        for (int j = 0; j<width*height; j++) {
            histogram_array[grays[j]]++;
        }
        int max_value = Arrays.stream(histogram_array).max().getAsInt();
        red = 255;
        green = 255;
        blue = 255;
        int white_pixel =  (red<<16) | (green<<8) | blue;
        for (i = 0; i<256; i++) {
            for (int j = 0; j<256; j++) {
                histogram.setRGB(i, j, white_pixel);
            }
        }
        int value;
        for (i = 0; i<255; i++) {
            value = 255*histogram_array[i]/max_value;
            limit = 255 - value;
            for (int j = limit; j<255; j++) {
                histogram.setRGB(i, j, 0);
            }
        }
        
        File f = new File("histogram.png");
        ImageIO.write(histogram, "PNG", f);
        System.out.println("Histogram saved in project directory");
    }
    
    //modifica el brillo
    public BufferedImage ModificarBrillo (BufferedImage img, int nivel)
    {   
        height = img.getHeight();
        width = img.getWidth();
        
        if (nivel>0) {
            cantidad = nivel*50;
            for(int h=0; h<height; h++) {
                for(int w=0; w<width; w++) {
                    pixel = img.getRGB(w, h);
                    red = (pixel>>16)&0xff;
                    green = (pixel>>8)&0xff;
                    blue = pixel&0xff;
                    red+=cantidad;
                    green+=cantidad;
                    blue+=cantidad;
                    if (red>255) { red=255; }
                    if (green>255) { green=255; }
                    if (blue>255) { blue=255; }
                    pixel =  (red<<16) | (green <<8) | blue;
                    img.setRGB(w, h, pixel);
                }
            }
        }else{
            nivel = Math.abs(nivel);
            cantidad = nivel*50;
            for(int h=0; h<height; h++) {
                for(int w=0; w<width; w++) {
                    pixel = img.getRGB(w, h);
                    red = (pixel>>16)&0xff;
                    green = (pixel>>8)&0xff;
                    blue = pixel&0xff;
                    red-=cantidad;
                    green-=cantidad;
                    blue-=cantidad;
                    if (red<0) { red=0; }
                    if (green<0) { green=0; }
                    if (blue<0) { blue=0; }
                    pixel =  (red<<16) | (green <<8) | blue;
                    img.setRGB(w, h, pixel);
                }
            }
        }
        
        return img;
    }
    
    //modifica contraste
    public BufferedImage ModificarContraste (BufferedImage img, int nivel_contraste)
    {
        height = img.getHeight();
        width = img.getWidth();
        
        if (nivel_contraste>0) {
            cantidad = (float) (1 + (nivel_contraste*0.2));
            cantidad2 = (float) (1 - (nivel_contraste*0.2));
            for(int h=0; h<height; h++) {
                for(int w=0; w<width; w++) {
                    pixel = img.getRGB(w, h);
                    red = (pixel>>16)&0xff;
                    green = (pixel>>8)&0xff;
                    blue = pixel&0xff;
                    if((red+green+blue)/3 < 128) {
                        red=(int) (red*cantidad2);
                        green=(int) (green*cantidad2);
                        blue=(int) (blue*cantidad2);
                        if (red<0) { red=0; }
                        if (green<0) { green=0; }
                        if (blue<0) { blue=0; }
                        pixel =  (red<<16) | (green <<8) | blue;
                        img.setRGB(w, h, pixel);
                    } else {
                        red=(int) (red*cantidad);
                        green=(int) (green*cantidad);
                        blue=(int) (blue*cantidad);
                        if (red>255) { red=255; }
                        if (green>255) { green=255; }
                        if (blue>255) { blue=255; }
                        pixel =  (red<<16) | (green <<8) | blue;
                        img.setRGB(w, h, pixel);
                    }
                }
            }
        }else{
            nivel_contraste = Math.abs(nivel_contraste);
            cantidad = (float) (1 + (nivel_contraste*0.2));
            cantidad2 = (float) (1 - (nivel_contraste*0.2));
            for(int h=0; h<height; h++) {
                for(int w=0; w<width; w++) {
                    pixel = img.getRGB(w, h);
                    red = (pixel>>16)&0xff;
                    green = (pixel>>8)&0xff;
                    blue = pixel&0xff;
                    if((red+green+blue)/3 < 128) {
                        red=(int) (red*cantidad);
                        green=(int) (green*cantidad);
                        blue=(int) (blue*cantidad);
                        if (red>128) { red=128; }
                        if (green>128) { green=128; }
                        if (blue>128) { blue=128; }
                        pixel =  (red<<16) | (green <<8) | blue;
                        img.setRGB(w, h, pixel);
                    } else {
                        red=(int) (red*cantidad2);
                        green=(int) (green*cantidad2);
                        blue=(int) (blue*cantidad2);
                        if (red<128) { red=128; }
                        if (green<128) { green=128; }
                        if (blue<128) { blue=128; }
                        pixel =  (red<<16) | (green <<8) | blue;
                        img.setRGB(w, h, pixel);
                    }
                }
            }
        }
        return img;
    }
    
    //umbralizar la imagen
    public BufferedImage Umbralizacion (BufferedImage img, int umbral)
    {   
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
                
                if((red+green+blue)/3 < umbral)
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
        
        return img;
    }
    public int[] getLimits (String kernel) {
        int[] limits = new int [4];
        switch (kernel) {
            case "1x2" :
                limits = new int[] {0,1,0,0};
            break;
            case "2x1" :
                limits = new int[] {0,0,0,1};
            break;
            case "1x3" :
                limits = new int[] {0,2,0,0};
            break;
            case "3x1" :
                limits = new int[] {0,0,0,2};
            break;
            case "3x3" :
                limits = new int[] {-1,1,-1,1};
            break;
            case "1x5" :
                limits = new int[] {0,4,0,0};
            break;
            case "5x1" :
                limits = new int[] {0,0,0,4};
            break;
            case "5x5" :
                limits = new int[] {-2,2,-2,2};
            break;
            case "1x7" :
                limits = new int[] {0,6,0,0};
            break;
            case "7x1" :
                limits = new int[] {0,0,0,6};
            break;
            case "7x7" :
                limits = new int[] {-3,3,-3,3};
            break;
        }
        return limits;
    }
    
    public int GetMediana(int[] array, int size) {
        int mediana, aux;
        if (size%2==0){
            aux = (array[size/2]+array[(size/2)-1])/2;
            mediana = aux;
        } else {
            aux = (int)size/2;
            mediana = array[aux];
        }
        return mediana;
    }
    public BufferedImage FiltroMediana(BufferedImage img, String type) 
    {
        int [] kernel = getLimits(type);
        height = img.getHeight();
        width = img.getWidth();
        int index;
        int[] red_values = new int[13];
        int[] green_values = new int[13];
        int[] blue_values = new int[13];
        for (int h = 0; h<height; h++) {
            for (int w = 0; w<width; w++) {
                index=0;
                red = 0;
                green = 0;
                blue = 0;
                for (int i=kernel[0];i<kernel[1]+1;i++) {
                    if (w+i>=0 && w+i<width) {
                        pixel = img.getRGB(w+i, h);
                        red = (pixel>>16)&0xff;
                        green = (pixel>>8)&0xff;
                        blue = pixel&0xff;
                        red_values[index] = red;
                        green_values[index] = green;
                        blue_values[index] = blue;
                        index++;
                    }
                }
                for (int i=kernel[2];i<kernel[3]+1;i++) {
                    if (h+i>=0 && h+i<height && i!=0) {
                        pixel = img.getRGB(w, h+i);
                        red = (pixel>>16)&0xff;
                        green = (pixel>>8)&0xff;
                        blue = pixel&0xff;
                        red_values[index] = red;
                        green_values[index] = green;
                        blue_values[index] = blue;
                        index++;
                    }
                }
                Arrays.sort(red_values);
                Arrays.sort(green_values);
                Arrays.sort(blue_values);
                red = GetMediana(red_values, index);
                green = GetMediana(green_values, index);
                blue = GetMediana(blue_values, index);
                pixel = (red<<16) | (green <<8) | blue;
                img.setRGB(w, h, pixel);
            }
        }
        return img;
    }
    
    public BufferedImage FiltroPromedio(BufferedImage img, String type) 
    {
        int [] kernel = new int [4];
        kernel = getLimits(type);
        height = img.getHeight();
        width = img.getWidth();
        int index, avg_red, avg_green, avg_blue;
        for (int h = 0; h<height; h++) {
            for (int w = 0; w<width; w++) {
                index=0;
                avg_red = 0;
                avg_green = 0;
                avg_blue = 0;
                for (int i=kernel[0];i<kernel[1]+1;i++) {
                    if (w+i>=0 && w+i<width) {
                        pixel = img.getRGB(w+i, h);
                        red = (pixel>>16)&0xff;
                        green = (pixel>>8)&0xff;
                        blue = pixel&0xff;
                        avg_red+=red;
                        avg_green+=green;
                        avg_blue+=blue;
                        index++;
                    }
                }
                for (int i=kernel[2];i<kernel[3]+1;i++) {
                    if (h+i>=0 && h+i<height && i!=0) {
                        pixel = img.getRGB(w, h+i);
                        red = (pixel>>16)&0xff;
                        green = (pixel>>8)&0xff;
                        blue = pixel&0xff;
                        avg_red+=red;
                        avg_green+=green;
                        avg_blue+=blue;
                        index++;
                    }
                }
                avg_red = avg_red / index;
                avg_green = avg_green / index;
                avg_blue = avg_blue / index;
                pixel = (avg_red<<16) | (avg_green <<8) | avg_blue;
                img.setRGB(w, h, pixel);
            }
        }
        return img;
    }
    
    public BufferedImage FiltroGaussiano(BufferedImage img, String type) {
        
        int [] kernel = getLimits(type);
        height = img.getHeight();
        width = img.getWidth();
        int index, avg_red, avg_green, avg_blue;
        int[] gauss = new int[108];
        for (int h = 0; h<height; h++) {
            for (int w = 0; w<width; w++) {
                index=0;
                avg_red = 0;
                avg_green = 0;
                avg_blue = 0;
                for (int i=kernel[0];i<kernel[1]+1;i++) {
                    if (w+i>=0 && w+i<width) {
                        pixel = img.getRGB(w+i, h);
                        red = (pixel>>16)&0xff;
                        green = (pixel>>8)&0xff;
                        blue = pixel&0xff;
                        
                        if (kernel[1]==0){
                            index = 1;
                        }
                        // 3 columnas
                        if (kernel[1] == 1) {    
                            if (i == -1 || i == 1) {
                                avg_red += red;
                                avg_green += green;
                                avg_blue += blue;
                                index += 1;
                            }
                            if (i == 0) {
                                avg_red += red * 2;
                                avg_green += green * 2;
                                avg_blue += blue * 2;
                                index += 2;
                            }
                        }
                        
                        // 5 columnas
                        if (kernel[1] == 2) {
                            if (i == -2 || i == 2) {
                                avg_red += red;
                                avg_green += green;
                                avg_blue += blue;
                                index += 1;
                            }
                            if (i == -1 || i == 1) {
                                avg_red += red * 4;
                                avg_green += green * 4;
                                avg_blue += blue * 4;
                                index += 4;
                            }
                            if (i == 0) {
                                avg_red += red * 6;
                                avg_green += green * 6;
                                avg_blue += blue * 6;
                                index += 6;
                            }
                        }
                        
                        // 7 columnas 
                        if (kernel[1] == 3) {
                            
                            if (i == -3 || i == 3) {
                                avg_red += red;
                                avg_green += green;
                                avg_blue += blue;
                                index += 1;
                            }
                            if (i == -2 || i == 2) {
                                avg_red += red * 6;
                                avg_green += green * 6;
                                avg_blue += blue * 6;
                                index += 6;
                            }
                            if (i == -1 || i == 1) {
                                avg_red += red * 15;
                                avg_green += green * 15;
                                avg_blue += blue * 15;
                                index += 15;
                            }
                            if (i == 0) {
                                avg_red += red * 20;
                                avg_green += green * 20;
                                avg_blue += blue * 20;
                                index += 20;
                            }
                        }
                    }
                }
                for (int i=kernel[2];i<kernel[3]+1;i++) {
                    if (h+i>=0 && h+i<height && i!=0) {
                        pixel = img.getRGB(w, h+i);
                        red = (pixel>>16)&0xff;
                        green = (pixel>>8)&0xff;
                        blue = pixel&0xff;
                        
                        if (kernel[3]==0){
                            index = 1;
                        }
                        // 3 filas
                        if (kernel[3] == 1) {    
                            if (i == -1 || i == 1) {
                                avg_red += red;
                                avg_green += green;
                                avg_blue += blue;
                                index += 1;
                            }
                            if (i == 0) {
                                avg_red += red * 2;
                                avg_green += green * 2;
                                avg_blue += blue * 2;
                                index += 2;
                            }
                        }
                        
                        // 5 filas
                        if (kernel[3] == 2) {
                            if (i == -2 || i == 2) {
                                avg_red += red;
                                avg_green += green;
                                avg_blue += blue;
                                index += 1;
                            }
                            if (i == -1 || i == 1) {
                                avg_red += red * 4;
                                avg_green += green * 4;
                                avg_blue += blue * 4;
                                index += 4;
                            }
                            if (i == 0) {
                                avg_red += red * 6;
                                avg_green += green * 6;
                                avg_blue += blue * 6;
                                index += 6;
                            }
                        }
                        
                        // 7 filas
                        if (kernel[3] == 3) {
                            
                            if (i == -3 || i == 3) {
                                avg_red += red;
                                avg_green += green;
                                avg_blue += blue;
                                index += 1;
                            }
                            if (i == -2 || i == 2) {
                                avg_red += red * 6;
                                avg_green += green * 6;
                                avg_blue += blue * 6;
                                index += 6;
                            }
                            if (i == -1 || i == 1) {
                                avg_red += red * 15;
                                avg_green += green * 15;
                                avg_blue += blue * 15;
                                index += 15;
                            }
                            if (i == 0) {
                                avg_red += red * 20;
                                avg_green += green * 20;
                                avg_blue += blue * 20;
                                index += 20;
                            }
                        }
                    }
                }
                avg_red = avg_red / index;
                avg_green = avg_green / index;
                avg_blue = avg_blue / index;
                pixel = (avg_red<<16) | (avg_green <<8) | avg_blue;
                img.setRGB(w, h, pixel);
            }
        }
        return img;
    }
    
    public BufferedImage FiltroPrewitt(BufferedImage img) 
    {
        BufferedImage imagen = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        height = img.getHeight();
        width = img.getWidth();
        int superior_gray = 0;
        int white =  (255<<16) | (255<<8) | 255;
        int g =  (128<<16) | (128<<8) | 128;
        int index, total_red, total_green, total_blue;
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                imagen.setRGB(w, h, 0);
            }
        }    
        
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                total_red = 0;
                total_green = 0;
                total_blue = 0;
                
                //inferior izquierdo+
                if (w-1>=0 && h+1<height) {
                    pixel = img.getRGB(w-1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = (total_red+total_green+total_blue);
                }
                //inferior+
                if (h+1<height) {
                    pixel = img.getRGB(w, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                }
                //iferior derecho+
                if (w+1<width && h+1<height) {
                    pixel = img.getRGB(w+1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                }
                
                //superior izquierdo
                if (w-1>=0 && h-1>=0) {
                    pixel = img.getRGB(w-1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = (total_red+total_green+total_blue);
                }
                //superior
                if (h-1>=0) {
                    pixel = img.getRGB(w, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                }
                //superior derecho
                if (w+1<width && h-1>=0) {
                    pixel = img.getRGB(w+1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                }
                gray = gray/9;
                superior_gray = superior_gray/9;
                int check = gray-superior_gray;
                
                if (check > 0) {
                    imagen.setRGB(w, h, white);
                }
                if (check < 15 && check > -15) {
                    imagen.setRGB(w, h, g);
                }
            }
        }
        return imagen;
    }
    
        public BufferedImage FiltroPrewittBW(BufferedImage img) 
    {
        BufferedImage imagen = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        height = img.getHeight();
        width = img.getWidth();
        int superior_gray = 0;
        int white =  (255<<16) | (255<<8) | 255;
        int g =  (128<<16) | (128<<8) | 128;
        int index, total_red, total_green, total_blue;
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                imagen.setRGB(w, h, 0);
            }
        }    
        
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                total_red = 0;
                total_green = 0;
                total_blue = 0;
                
                //inferior izquierdo+
                if (w-1>=0 && h+1<height) {
                    pixel = img.getRGB(w-1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = (total_red+total_green+total_blue);
                }
                //inferior+
                if (h+1<height) {
                    pixel = img.getRGB(w, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                }
                //iferior derecho+
                if (w+1<width && h+1<height) {
                    pixel = img.getRGB(w+1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                }
                
                //superior izquierdo
                if (w-1>=0 && h-1>=0) {
                    pixel = img.getRGB(w-1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = (total_red+total_green+total_blue);
                }
                //superior
                if (h-1>=0) {
                    pixel = img.getRGB(w, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                }
                //superior derecho
                if (w+1<width && h-1>=0) {
                    pixel = img.getRGB(w+1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                }
                gray = gray/9;
                superior_gray = superior_gray/9;
                int check = gray-superior_gray;
                
                if (check > 20) {
                    imagen.setRGB(w, h, white);
                }
            }
        }
        return imagen;
    }
    
    public BufferedImage FiltroSobel(BufferedImage img) 
    {
        BufferedImage imagen = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        height = img.getHeight();
        width = img.getWidth();
        int superior_gray = 0;
        int white =  (255<<16) | (255<<8) | 255;
        int g =  (128<<16) | (128<<8) | 128;
        int index, total_red, total_green, total_blue;
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                imagen.setRGB(w, h, 0);
            }
        }  
        
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                total_red = 0;
                total_green = 0;
                total_blue = 0;
                
                //inferior izquierdo+
                if (w-1>=0 && h+1<height) {
                    pixel = img.getRGB(w-1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = (total_red+total_green+total_blue);
                }
                //inferior+
                if (h+1<height) {
                    pixel = img.getRGB(w, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                    gray += total_red+total_green+total_blue;
                }
                //iferior derecho+
                if (w+1<width && h+1<height) {
                    pixel = img.getRGB(w+1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                }
                
                //superior izquierdo
                if (w-1>=0 && h-1>=0) {
                    pixel = img.getRGB(w-1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = (total_red+total_green+total_blue);
                }
                //superior
                if (h-1>=0) {
                    pixel = img.getRGB(w, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                    superior_gray += total_red+total_green+total_blue;
                }
                //superior derecho
                if (w+1<width && h-1>=0) {
                    pixel = img.getRGB(w+1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                }
                gray = gray/11;
                superior_gray = superior_gray/11;
                int check = gray-superior_gray;
                
                if (check > 0) {
                    imagen.setRGB(w, h, white);
                }
                if (check < 15 && check > -15) {
                    imagen.setRGB(w, h, g);
                }

            }
        }
        return imagen;
    }
    
    public BufferedImage FiltroSobelBW(BufferedImage img) 
    {
        BufferedImage imagen = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        height = img.getHeight();
        width = img.getWidth();
        int superior_gray = 0;
        int white =  (255<<16) | (255<<8) | 255;
        int g =  (128<<16) | (128<<8) | 128;
        int index, total_red, total_green, total_blue;
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                imagen.setRGB(w, h, 0);
            }
        }  
        
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                total_red = 0;
                total_green = 0;
                total_blue = 0;
                
                //inferior izquierdo+
                if (w-1>=0 && h+1<height) {
                    pixel = img.getRGB(w-1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = (total_red+total_green+total_blue);
                }
                //inferior+
                if (h+1<height) {
                    pixel = img.getRGB(w, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                    gray += total_red+total_green+total_blue;
                }
                //iferior derecho+
                if (w+1<width && h+1<height) {
                    pixel = img.getRGB(w+1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray += total_red+total_green+total_blue;
                }
                
                //superior izquierdo
                if (w-1>=0 && h-1>=0) {
                    pixel = img.getRGB(w-1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = (total_red+total_green+total_blue);
                }
                //superior
                if (h-1>=0) {
                    pixel = img.getRGB(w, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                    superior_gray += total_red+total_green+total_blue;
                }
                //superior derecho
                if (w+1<width && h-1>=0) {
                    pixel = img.getRGB(w+1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray += total_red+total_green+total_blue;
                }
                gray = gray/11;
                superior_gray = superior_gray/11;
                int check = gray-superior_gray;
                
                if (check > 15) {
                    imagen.setRGB(w, h, white);
                }
            }
        }
        return imagen;
    }
    
     public BufferedImage FiltroRoberts(BufferedImage img) 
    {
        BufferedImage imagen = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        height = img.getHeight();
        width = img.getWidth();
        int superior_gray = 0;
        int white =  (255<<16) | (255<<8) | 255;
        int g =  (128<<16) | (128<<8) | 128;
        int index, total_red, total_green, total_blue;
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                imagen.setRGB(w, h, 0);
            }
        }  
        
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                total_red = 0;
                total_green = 0;
                total_blue = 0;
                
                //inferior izquierdo+
                if (w-1>=0 && h+1<height) {
                    pixel = img.getRGB(w-1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = (total_red+total_green+total_blue)*3;
                }
                //inferior+
                if (h+1<height) {
                    pixel = img.getRGB(w, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = gray + (total_red+total_green+total_blue)*10;
                }
                //iferior derecho+
                if (w+1<width && h+1<height) {
                    pixel = img.getRGB(w+1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = gray + (total_red+total_green+total_blue)*3;
                }
                
                //superior izquierdo
                if (w-1>=0 && h-1>=0) {
                    pixel = img.getRGB(w-1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = (total_red+total_green+total_blue)*3;
                }
                //superior
                if (h-1>=0) {
                    pixel = img.getRGB(w, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = superior_gray + (total_red+total_green+total_blue)*10;
                }
                //superior derecho
                if (w+1<width && h-1>=0) {
                    pixel = img.getRGB(w+1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = superior_gray + (total_red+total_green+total_blue)*3;
                }
                gray = gray/48;
                superior_gray = superior_gray/48;
                int check = gray-superior_gray;
                
                if (check > 0) {
                    imagen.setRGB(w, h, white);
                }
                if (check < 15 && check > -15) {
                    imagen.setRGB(w, h, g);
                }

            }
        }
        return imagen;
    }
     
     public BufferedImage FiltroRobertsBW(BufferedImage img) 
    {
        BufferedImage imagen = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        height = img.getHeight();
        width = img.getWidth();
        int superior_gray = 0;
        int white =  (255<<16) | (255<<8) | 255;
        int g =  (128<<16) | (128<<8) | 128;
        int index, total_red, total_green, total_blue;
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                imagen.setRGB(w, h, 0);
            }
        }  
        
        for (int h = 1; h<height-1; h++) {
            for (int w = 1; w<width-1; w++) {
                total_red = 0;
                total_green = 0;
                total_blue = 0;
                
                //inferior izquierdo+
                if (w-1>=0 && h+1<height) {
                    pixel = img.getRGB(w-1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = (total_red+total_green+total_blue)*3;
                }
                //inferior+
                if (h+1<height) {
                    pixel = img.getRGB(w, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = gray + (total_red+total_green+total_blue)*10;
                }
                //iferior derecho+
                if (w+1<width && h+1<height) {
                    pixel = img.getRGB(w+1, h+1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    gray = gray + (total_red+total_green+total_blue)*3;
                }
                
                //superior izquierdo
                if (w-1>=0 && h-1>=0) {
                    pixel = img.getRGB(w-1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = (total_red+total_green+total_blue)*3;
                }
                //superior
                if (h-1>=0) {
                    pixel = img.getRGB(w, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = superior_gray + (total_red+total_green+total_blue)*10;
                }
                //superior derecho
                if (w+1<width && h-1>=0) {
                    pixel = img.getRGB(w+1, h-1);
                    total_red = (pixel>>16)&0xff;
                    total_green = (pixel>>8)&0xff;
                    total_blue = pixel&0xff;
                    superior_gray = superior_gray + (total_red+total_green+total_blue)*3;
                }
                gray = gray/48;
                superior_gray = superior_gray/48;
                int check = gray-superior_gray;
                
                if (check > 15) {
                    imagen.setRGB(w, h, white);
                }
            }
        }
        return imagen;
    }
    
    // Convierte la imagen negativa.
    public BufferedImage FotoNegativa(BufferedImage img)
    {
        Imagen.setImagenOrginal(img);
        
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
        Imagen.setImagenOrginal(img);
        
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
        Imagen.setImagenOrginal(img);
        final int BLACK = 0;
        final int WHITE = 255;
        height = img.getHeight();
        width = img.getWidth();
        
        for(int h=0; h<height; h++) {
            for(int w=0; w<width; w++) {
                pixel = img.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                if((red+green+blue)/3 < 128) {
                    red = BLACK;
                    green = BLACK;
                    blue = BLACK;
                }else {
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
                BufferedImage img = Imagen.getImagenOrginal();
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
    
    // Lee caracteres de un archivo Netpbm ignorando espacios.
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
      
    // Lectura de todas las imágines Netpbm.
    public BufferedImage Netpbm(String tipoNetpbm, String ruta) throws FileNotFoundException, IOException{
                
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
                if(tipoNetpbm.toLowerCase().equalsIgnoreCase( "pgm") || tipoNetpbm.toLowerCase().equalsIgnoreCase( "ppm"))
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
        switch(tipoNetpbm.toLowerCase())
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
