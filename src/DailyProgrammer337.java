/*
 * Created by Dave on 12/6/17.
 */

import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class DailyProgrammer337 {

    public static void main(String[] args) {

        System.out.print("Starting to unscrambling your images...\n\n");
        String[] fileNames = {"imageOne.png", "imageTwo.png", "imageThree.png"};
        for (String s: fileNames) {
            unscrambleImage(s);
        }
        System.out.print("Done unscrambling your images!");
    }

    private static void unscrambleImage(String imageName) {
        String outputName = getOutputFilename(imageName);

        File input = new File(imageName);
        File output = new File(outputName);

        try {
            BufferedImage image = ImageIO.read(input);

            // need to put this image into a 2d array
            int [][] pixels = createPixelArray(image);
            pixels = unscrambleHorizontalRows(image, pixels);

            //write the array back to the image buffer
            for(int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    image.setRGB(j, i, pixels[i][j]);
                }
            }
            //save the file
            ImageIO.write(image, "png", output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] createPixelArray(BufferedImage image) {
        int[][] pixels = new int[image.getHeight()][image.getWidth()];

        for(int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pixels[x][y] = image.getRGB(y, x);
            }
        }
        return pixels;
    }

    private static int[][] unscrambleHorizontalRows(BufferedImage image, int[][] pixels) {
        for(int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(pixels[x][y], true);
                if (isRed(pixelColor)) {
                    int[] pivotedRow = pivotRow(pixels[x], y);
                    pixels[x] = pivotedRow;
                }
            }
        }
        return pixels;
    }

    private static boolean isRed(Color pixel) {
        return pixel.getRed() != pixel.getBlue() || pixel.getGreen() != pixel.getBlue();
    }

    private static int[] pivotRow(int[] row, int index) {
        int[] pivotedRow = new int[row.length];
        System.arraycopy(row, index, pivotedRow, 0, row.length - index);
        System.arraycopy(row, 0, pivotedRow, row.length - index, index);

        return pivotedRow;
    }

    private static String getOutputFilename(String inputFilename) {
        int index = inputFilename.indexOf('.');
        if (index != -1) {
            return inputFilename.substring(0, index).concat("Output.png");
        }
        else {
            return inputFilename.concat("Output");
        }
    }
}