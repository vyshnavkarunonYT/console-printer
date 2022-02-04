package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import utils.Utils;

public class ImageToConsoleBW {

	static int scaledImageWidth = 36;
	static int scaledImageHeight = 36;
	
	static BufferedImage image = null;
	static BufferedImage scaledImage = null;
	
	static String imagePath = "./res/images/pikachu.jpg";

	static Color [] colors = { Color.WHITE, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.BLACK };
	static int[] colorsRGB = new int[colors.length];
	
	static char [] consoleCode = {' ','*','&','@'};

	public static void main(String[] args) {

		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			scaledImage = Thumbnails.of(image)
			        .size(scaledImageWidth, scaledImageHeight)
			        .asBufferedImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Stores the pixel data of the image in a 2D array with each pixel being represented by its RGB value
		int[][] pixels = Utils.convertTo2D(scaledImage);
		
		
		for(int i=0; i<colors.length; i++) {
			colorsRGB[i] = colors[i].getRGB();
		}
		
		int pixelCodes[][] = Utils.getMappedColorID(pixels, colorsRGB);

		for(int i=0; i<pixels.length; i++) {
			for(int j=0; j<pixels[0].length; j++) {
				System.out.print(consoleCode[pixelCodes[i][j]]+""+consoleCode[pixelCodes[i][j]]);
			}
			System.out.println();
		}
	}
}
