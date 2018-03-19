/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdi;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import vista.Frame;

/**
 *
 * @author AnthonyLA
 */
public class PDI {

    // Vuelve la imagen negativa.
    public BufferedImage fotoNegativa(BufferedImage img)
    {
        int height = img.getHeight();
        int width = img.getWidth();
        
        int red;
        int green;
        int blue;
        
        for (int h = 0; h<height; h++)
        {
            for (int w = 0; w<width; w++)
            {
                int pixel = img.getRGB(w, h);
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
        return (img);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Frame vista = new Frame();

        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setSize(200, 200);
        vista.setVisible(true);
    }

}
