/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdi;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import vista.Frame;

/**
 *
 * @author AnthonyLA
 */
public class PDI {

    
    public void fotoNegativa(Image img)
    {
        System.out.println("grises");
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
