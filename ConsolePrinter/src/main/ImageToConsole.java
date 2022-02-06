package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import utils.ConsoleColors;
import utils.Utils;

public class ImageToConsole {

	static int scaledImageWidth = 36;
	static int scaledImageHeight = 36;
	
	static BufferedImage image = null;
	static BufferedImage scaledImage = null;
	
	
	//Stroes the path of the image
	static String imagePath = "./res/images/pikachu.jpg";
	
	
	//Color Defintions 
	static Color blackColor = new Color(0,0,0);
	static Color redColor = new Color(128,0,0);
	static Color greenColor = new Color(0,128,0);
	static Color yellowColor = new Color(128,128,0);
	static Color blueColor = new Color(0,0,128);
	static Color magentaColor = new Color(128,0,128);
	static Color cyanColor = new Color(0,128,128);
	static Color whiteColor = new Color(192,192,192); // Unused
	
	static Color brightBlackColor = new Color(128,128,128); // Unused
	static Color brightRedColor = new Color(255,0,0);
	static Color brightGreenColor = new Color(0,255,0);
	static Color brightYellowColor = new Color(255,255,0);
	static Color brightBlueColor = new Color(0,0,255);
	static Color brightMagentaColor = new Color(255,0,255);
	static Color brightCyanColor = new Color(0,255,255);
	static Color brightWhiteColor = new Color(255,255,255);
	
	

	static Color [] colors = {blackColor, redColor, greenColor, yellowColor, blueColor, magentaColor, cyanColor,
			brightRedColor, brightGreenColor, brightYellowColor, brightBlueColor, brightMagentaColor,
			brightCyanColor, brightWhiteColor};

	//Will store the integer value of the colors.
	static int[] colorsRGB = new int[colors.length];
	
	
	// ConsoleCodes store the string value of the ANSI BACKGROUND COLOR of the respective colors. If the first Color in the Colors array is black 
	// The first console code must code for BLACK_BACKGROUND and so on.
	static String [] consoleCode = {ConsoleColors.BLACK_BACKGROUND, ConsoleColors.RED_BACKGROUND, ConsoleColors.GREEN_BACKGROUND,
			ConsoleColors.YELLOW_BACKGROUND, ConsoleColors.BLUE_BACKGROUND, ConsoleColors.MAGENTA_BACKGROUND, ConsoleColors.CYAN_BACKGROUND, ConsoleColors.RED_BACKGROUND_BRIGHT,
			ConsoleColors.GREEN_BACKGROUND_BRIGHT, ConsoleColors.YELLOW_BACKGROUND_BRIGHT, ConsoleColors.BLUE_BACKGROUND_BRIGHT,
			ConsoleColors.MAGENTA_BACKGROUND_BRIGHT, ConsoleColors.CYAN_BACKGROUND_BRIGHT, ConsoleColors.WHITE_BACKGROUND_BRIGHT};

	public static void main(String[] args) {
		
		//Reading the image from the image path
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// Scaling the image down to the maximum allowed size
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
		
		//Converts the colors array into an int colorsRGB array so that color mapping can be performed
		for(int i=0; i<colors.length; i++) {
			colorsRGB[i] = colors[i].getRGB();
		}
		
		
		//Converts pixel 2D array into a 2D array of IDs containing the respective closest matched ID in the colors array for each pixel.
		int pixelCodes[][] = Utils.getMappedColorID(pixels, colorsRGB);

		
		//Prints out the image onto the screen using the ANSI Console Values
		for(int i=0; i<pixels.length; i++) {
			for(int j=0; j<pixels[0].length; j++) {
				System.out.print(consoleCode[pixelCodes[i][j]]+"  ");
			}
			System.out.println();
		}
	}
}
