package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import utils.Utils;

public class ImageToConsoleBW {
	
	
	//Maximum allowed image size in either dimension after scaling
	static int scaledImageWidth = 36;
	static int scaledImageHeight = 36;
	
	//Image Variables
	static BufferedImage image = null;
	static BufferedImage scaledImage = null;
	static String imagePath = "./res/images/pikachu.jpg";

	
	//Colors Array - Stores the list of colors that the image must be mapped to
	static Color [] colors = { Color.WHITE, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.BLACK };
	
	//Stores the integer value of colors image must be mapped to
	static int[] colorsRGB = new int[colors.length];
	
	//Stores the characters the image will be displayed as on the console terminal - ' ' - represents White Color and '@' represents Black
	static char [] consoleCode = {' ','*','&','@'};

	public static void main(String[] args) {
		
		//Reading the image from image path
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//Scaling down the image to the maximum allowed size
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
		
		
		//Converts the  map colors into their Integer values
		for(int i=0; i<colors.length; i++) {
			colorsRGB[i] = colors[i].getRGB();
		}
		
	
		//Maps the 2D pixel array into the closest matching ID of the colorsRGB array
		int pixelCodes[][] = Utils.getMappedColorID(pixels, colorsRGB);
	
		//Prints out the pixelCodes[][] arrays onto the screen as characters using the consoleCode[] array
		for(int i=0; i<pixels.length; i++) {
			for(int j=0; j<pixels[0].length; j++) {
				System.out.print(consoleCode[pixelCodes[i][j]]+""+consoleCode[pixelCodes[i][j]]);
			}
			System.out.println();
		}
	}
}
