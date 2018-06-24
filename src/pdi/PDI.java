
package pdi;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import vista.Frame;

public class PDI {
    
    private int height = 0;
    private int width = 0;
    
    private int red, red2;
    private int green, green2;
    private int blue, blue2;
    private int gray;
    private int pixel, pixel2;
    private int maxGray;
    private float cantidad;
    private float cantidad2;
    int r = 175;
    int g = 175;
    int b = 175;
    int p =  (r<<16) | (g <<8) | b;
    
    //returns image information in console
    public BufferedImage CloudsRemover(BufferedImage img1, BufferedImage img2)
    {
        
        BufferedImage imagen = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
        BufferedImage imagen2 = new BufferedImage(img2.getWidth(), img2.getHeight(), img2.getType());
        
        //TRHESHOLDING
        imagen = Umbralizacion(img1, 115);
        imagen2 = Umbralizacion(img2, 115);
        
        //FILL GRAYS ON CLOUDS POSITIONS
        FillGrays(img1, imagen);
        FillGrays(img2, imagen2);
        
        //Combine images
        imagen = CombineImages(img1, img2);
   
        //Erase de rest of clouds spaces
        Promedio(imagen);

        return imagen;
    }
    
    public BufferedImage CombineImages(BufferedImage img1, BufferedImage img2){
        BufferedImage result = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
        height = result.getHeight();
        width = result.getWidth();
        
        for(int h=0; h<height; h++)
        {
            for(int w=0; w<width; w++)
            {
                pixel = img1.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                
                pixel2 = img2.getRGB(w, h);
                red2 = (pixel>>16)&0xff;
                green2 = (pixel>>8)&0xff;
                blue2 = pixel&0xff;
                
                if((red+green+blue == 525) && (red2+green2+blue2 != 525))
                {
                    result.setRGB(w, h, pixel2);
                }
                if((red+green+blue != 525) && (red2+green2+blue2 == 525))
                {
                    result.setRGB(w, h, pixel);
                }
                if((red+green+blue == 525) && (red2+green2+blue2 == 525))
                {
                    result.setRGB(w, h, pixel);
                }
                if((red+green+blue != 525) && (red2+green2+blue2 != 525))
                {
                    result.setRGB(w, h, pixel);
                }
            }
        }
        
        return result;
    }
    
    public BufferedImage FillGrays(BufferedImage img, BufferedImage imagen) {
    
        height = img.getHeight();
        width = img.getWidth();
        
        for(int h=0; h<height; h++)
        {
            for(int w=0; w<width; w++)
            {
                pixel = imagen.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                
                if((red+green+blue) == 0)
                {
                    img.setRGB(w, h, p);
                }
            }
        }
        
        return img;
    }
    
    public void Count (BufferedImage img) {
    
        height = img.getHeight();
        width = img.getWidth();
        int count = 0;
        for (int h = 0; h<height; h++) {
            for (int w = 0; w<width; w++) {
                    count++;
            }
        }
        System.out.println("Count from Count Function: "+count);
    }
    
    public BufferedImage Promedio(BufferedImage img) 
    {
        int [] kernel = new int[] {-100,100,-100,100};
        height = img.getHeight();
        width = img.getWidth();
        int index, avg_red, avg_green, avg_blue, count = 0;
        for (int h = 0; h<height; h++) {
            for (int w = 0; w<width; w++) {
                index=0;
                avg_red = 0;
                avg_green = 0;
                avg_blue = 0;
                pixel = img.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                if (red+green+blue == 525) {
                    count++;
                    for (int i=kernel[0];i<kernel[1]+1;i++) {
                        if (w+i>=0 && w+i<width) {
                            pixel = img.getRGB(w+i, h);
                            red = (pixel>>16)&0xff;
                            green = (pixel>>8)&0xff;
                            blue = pixel&0xff;
                            if (red+green+blue != 525) {
                                avg_red+=red;
                                avg_green+=green;
                                avg_blue+=blue;
                                index++;
                            }
                        }
                    }
                    for (int i=kernel[2];i<kernel[3]+1;i++) {
                        if (h+i>=0 && h+i<height && i!=0) {
                            pixel = img.getRGB(w, h+i);
                            red = (pixel>>16)&0xff;
                            green = (pixel>>8)&0xff;
                            blue = pixel&0xff;
                            if (red+green+blue != 525) {
                                avg_red+=red;
                                avg_green+=green;
                                avg_blue+=blue;
                                index++;
                            }
                        }
                    }
                }
                if (index != 0) {
                    avg_red = avg_red / index;
                    avg_green = avg_green / index;
                    avg_blue = avg_blue / index;
                    pixel = (avg_red<<16) | (avg_green <<8) | avg_blue;
                    img.setRGB(w, h, pixel);
                }
            }
        }
        System.out.println("Count from average: "+count);
        return img;
    }
    
    public BufferedImage FiltroPromedio(BufferedImage img) 
    {
        //BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int [] kernel = new int[] {-100,100,-100,100};
        height = img.getHeight();
        width = img.getWidth();
        int index, avg_red, avg_green, avg_blue;
        for (int h = 0; h<height; h++) {
            for (int w = 0; w<width; w++) {
                index=0;
                avg_red = 0;
                avg_green = 0;
                avg_blue = 0;
                pixel = img.getRGB(w, h);
                red = (pixel>>16)&0xff;
                green = (pixel>>8)&0xff;
                blue = pixel&0xff;
                if (red+green+blue == 525) {
                    for (int i=kernel[0];i<kernel[1]+1;i++) {
                        if (w+i>=0 && w+i<width) {
                            pixel = img.getRGB(w+i, h);
                            red = (pixel>>16)&0xff;
                            green = (pixel>>8)&0xff;
                            blue = pixel&0xff;
                            if (red+green+blue != 525) {
                                System.out.print("entro");
                                avg_red+=red;
                                avg_green+=green;
                                avg_blue+=blue;
                                index++;
                            }
                        }
                    }
                    for (int i=kernel[2];i<kernel[3]+1;i++) {
                        if (h+i>=0 && h+i<height && i!=0) {
                            pixel = img.getRGB(w, h+i);
                            red = (pixel>>16)&0xff;
                            green = (pixel>>8)&0xff;
                            blue = pixel&0xff;
                            if (red+green+blue != 525) {
                                avg_red+=red;
                                avg_green+=green;
                                avg_blue+=blue;
                                index++;
                            }
                        }
                    }
                }
                if (index != 0) {
                    avg_red = avg_red / index;
                    avg_green = avg_green / index;
                    avg_blue = avg_blue / index;
                    pixel = (avg_red<<16) | (avg_green <<8) | avg_blue;
                    img.setRGB(w, h, pixel);
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
        
        BufferedImage imagen = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        
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
                    red = WHITE;
                    green = WHITE;
                    blue = WHITE;
                }else
                {
                    red = BLACK;
                    green = BLACK;
                    blue = BLACK;
                }
                
                pixel =  (red<<16) | (green <<8) | blue;
                
                imagen.setRGB(w, h, pixel);
            }
        }
        
        return imagen;
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
