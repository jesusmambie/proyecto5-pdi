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

    
    public static BufferedImage getImagenOrinal() {
        return imagenOrinal;
    }

    public static void setImagenOrinal(BufferedImage imagenOrinal) {
        Imagen.imagenOrinal = imagenOrinal;
    }
              
    public static void setImagenTemporal(BufferedImage imagenTemporal) {
        Imagen.imagenTemporal = imagenTemporal;
    }

    public static BufferedImage getImagenTemporal() {
        return imagenTemporal;
    }
    
    public static boolean[] getTipoGuardado() {
        return tipoGuardado;
    }

    public static void setTipoGuardado(boolean[] tipoGuardado) {
        Imagen.tipoGuardado = tipoGuardado;
    }
}
