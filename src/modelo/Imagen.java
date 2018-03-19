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
    
    private static BufferedImage imagenFinal;
    
    
    public BufferedImage getImagen()
    {
        return this.imagenFinal;
    }
    
    public void setImagen(BufferedImage imagenFinal)
    {
        this.imagenFinal = imagenFinal;
    }
}
