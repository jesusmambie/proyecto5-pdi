/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.awt.image.BufferedImage;
/**
 *
 * @author AnthonyLA
 */
public class Imagen {
    
    private static BufferedImage imagenOrinal;
    private static BufferedImage imagenTemporal;
        
    public static BufferedImage getImagen()
    {
        return Imagen.imagenOrinal;
    }
    
    public static void setImagen(BufferedImage imagenFinal)
    {
        Imagen.imagenOrinal = imagenFinal;
    }
    
    public static void setImagenTemporal(BufferedImage imagenTemporal) {
        Imagen.imagenTemporal = imagenTemporal;
    }

    public static BufferedImage getImagenTemporal() {
        return imagenTemporal;
    }
}
