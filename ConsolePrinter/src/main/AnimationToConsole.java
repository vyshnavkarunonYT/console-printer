package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import utils.ConsoleColors;
import utils.Utils;

public class AnimationToConsole {

	static int scaledImageWidth = 40;
	static int scaledImageHeight = 40;

	static BufferedImage image = null;
	static BufferedImage scaledImage = null;

	static String imagePath = "./res/animations/runningMan.png";

	static int noOfColors = 6;
	static Color[] colors;
	static int[] colorsRGB = new int[noOfColors];

	static char[] consoleCodeBW = { '@', '&', '+', '=', '-', ' ' };

	static String[] consoleCode = { ConsoleColors.BLACK_BACKGROUND, ConsoleColors.MAGENTA_BACKGROUND,
			ConsoleColors.RED_BACKGROUND_BRIGHT, ConsoleColors.GREEN_BACKGROUND_BRIGHT,
			ConsoleColors.BLUE_BACKGROUND_BRIGHT, ConsoleColors.WHITE_BACKGROUND_BRIGHT };

	public static void main(String[] args) {
		
		
		//Initializing the amount of colors the image needs to be mapped to
		colors = new Color[noOfColors];
		int mult = 255/(noOfColors-1);
		for(int i =0; i<noOfColors; i++) {
			colors[i] = new Color(mult*i, mult*i, mult*i);
		}

		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int spriteWidth = imageWidth/8;
		int spriteHeight = imageHeight/2;
		
		for(int imageIndex =  0; imageIndex<8; imageIndex++) {
			
			try {
				BufferedImage croppedImage = image.getSubimage( (imageIndex%8)*spriteWidth, (imageIndex/8)*spriteHeight, spriteWidth, spriteHeight);
				
				scaledImage = Thumbnails.of(croppedImage).size(scaledImageWidth, scaledImageHeight).asBufferedImage();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			scaledImage = Utils.GrayScale(scaledImage);
	
			// Stores the pixel data of the image in a 2D array with each pixel being
			// represented by its RGB value
			int[][] pixels = Utils.convertTo2D(scaledImage);
	
			for (int i = 0; i < colors.length; i++) {
				colorsRGB[i] = colors[i].getRGB();
			}
	
			int pixelCodes[][] = Utils.getMappedColorID(pixels, colorsRGB);
	
			for (int i = 0; i < pixels.length; i++) {
				for (int j = 0; j < pixels[0].length; j++) {
					System.out.print(consoleCodeBW[pixelCodes[i][j]] + "" + consoleCodeBW[pixelCodes[i][j]]);
				}
				System.out.println();
			}
			
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 System.out.print("\033[H\033[2J");  
			 System.out.flush();  
			 
			 if(imageIndex==7) {
				 imageIndex=0;
			 }
			
		}
	}
}
