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
    private static boolean[] tipoGuardado = new boolean[3];

    public static void setImagenOrginal(BufferedImage imagenOrinal) {
        Imagen.imagenOrinal = imagenOrinal;
    }
              
    public static void setImagenTemporal(BufferedImage imagenTemporal) {
        Imagen.imagenTemporal = imagenTemporal;
    }
    
    public static void setTipoGuardado(boolean[] tipoGuardado) {
        Imagen.tipoGuardado = tipoGuardado;
    }
    
    public static BufferedImage getImagenOrginal() {
        return imagenOrinal;
    }
    public static BufferedImage getImagenTemporal() {
        return imagenTemporal;
    }
    
    public static boolean[] getTipoGuardado() {
        return tipoGuardado;
    }

    public static BufferedImage getImagenOriginal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
