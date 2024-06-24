package Renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    int nX = 800;
    int nY = 500;

    Color blueColor = new Color(0, 0, 255);
    Color pinkColor = new Color(255, 105, 180);

    @Test
    void writeToImage() {
        ImageWriter imageWriter = new ImageWriter("pinkGridOnBlue", nX, nY);
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, pinkColor);
                }
                else {
                    imageWriter.writePixel(i, j, blueColor);
                }
            }
        }
        imageWriter.writeToImage();
    }

}