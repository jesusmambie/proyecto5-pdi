/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.awt.Image;
/**
 *
 * @author AnthonyLA
 */
public class Imagen {
    
    private static Image imagenFinal;
    
    
    public Image getImagen()
    {
        return this.imagenFinal;
    }
    
    public void setImagen(Image imagenFinal)
    {
        this.imagenFinal = imagenFinal;
    }
}
