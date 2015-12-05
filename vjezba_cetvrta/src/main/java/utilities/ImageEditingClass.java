package utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by marko on 12/1/15.
 */
public class ImageEditingClass {

    public static void kreirajSlike(String folderPath, String imageName) throws IOException {
        // Slika "sahovska_ploca.jpg" sadrži 64 crno-bijelih polja s figurama
        File file = new File(imageName);
        FileInputStream fis = new FileInputStream(file);

        // Pozivanje metode za čitanje slike
        BufferedImage image = ImageIO.read(fis);

        // Definiranje broja redaka i stupaca slike
        // 8 redaka i 8 stupaca za bolja polja na šahovskoj ploči
        int rows = 8;
        int cols = 8;
        int chunks = rows * cols;

        // određivanje veličine jednog polja na šahovskoj ploči
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / rows;

        // Definiranje polja koje sadrži sve dijelove šahovske ploče
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {

                // Inicijalizacija polja s dijelovima slike
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // "Crtanje" pojedinih dijelova slike
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight,
                        chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth,
                        chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        System.out.println("Rezanje slike je završeno.");

        // Zapisivanje dijelova slika u zasebne datoteke koje se nazivaju kao i polja
        // na šahovskoj ploči
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                String polje;
                int index = 8 - i;
                int letterInt = 65 + j;
                char letter = (char) letterInt;
                polje = folderPath + letter + String.valueOf(index);
                ImageIO.write(imgs[i * cols + j], "jpg", new File(polje + ".jpg"));

                System.out.println("Kreiranje slika je završeno.");
            }
        }
    }
}
