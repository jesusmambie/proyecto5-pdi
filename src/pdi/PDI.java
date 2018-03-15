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

    public ImageIcon ResizeImage(String path, javax.swing.JLabel labelImagen) {
        ImageIcon MyImage = new ImageIcon(path);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
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
