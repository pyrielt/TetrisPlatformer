package VueControleur;

import Modele.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class ImagesGetter {
    public static final String IMAGE_FOLDER = "data/";
    public static final String[] IMAGES_CASE_FILENAME = {
            "yellow.png",
            "magenta.png",
            "cyan.png",
            "orange.png",
            "green.png",
            "player.png",
            "error.png"};
    public static final Image[] IMAGES_CASE = loadTextures(IMAGES_CASE_FILENAME);
    public static final String[] IMAGES_BACKGROUND_FILENAME = {
            "Grid.png",
            "NextPiece.png",
            "Main.png",
            "GameOver.png",
            "Button.png",
            "ButtonHover.png",
            "Pause.png",
            "Info.png"};
    public static final Image[] IMAGES_BACKGROUND = loadTextures(IMAGES_BACKGROUND_FILENAME);

    public static Image[] loadTextures(String[] fileName){
        Image[] images = new Image[fileName.length];
        for (int i = 0; i < fileName.length; i++) {
            images[i] = loadTexture(IMAGE_FOLDER + fileName[i]);
        }
        return images;
    }

    public static Image loadTexture(String fileName){
        Image img = null;

        try {
            img = ImageIO.read(new File(fileName));

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Image could not be read");
            System.exit(1);
        }
        return img;
    }

    public static Image getImageCase(Colors color){
        if(color==Colors.PLAYER){
            return IMAGES_CASE[IMAGES_CASE.length-2];
        }
        if(!color.canRowDelete()
                ||color.id() < 0
                ||color.id() >= IMAGES_CASE_FILENAME.length){
            return IMAGES_CASE[IMAGES_CASE.length-1];
        }
        return IMAGES_CASE[color.id()];
    }

    public static Image getImageBackGround(int id){
        if(id<0 || id>IMAGES_BACKGROUND.length){
            return null;
        }
        return IMAGES_BACKGROUND[id];
    }
}
