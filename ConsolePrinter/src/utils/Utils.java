package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Utils {

	public static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}

		return result;
	}

	// Image to 2DArray - Fast implementation does not work after resizing image
	public static int[][] convertTo2D(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}

	// Get mapped Color ID - takes in pixel array and color array and returns index
	// of the closest matching color
	// of the pixel color in the color array

	public static int[][] getMappedColorID(int[][] pixels, int[] colors) {
		int mappedID[][] = new int[pixels.length][pixels[0].length];

		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				mappedID[i][j] = getClosestColorID(pixels[i][j], colors);
			}
		}

		return mappedID;
	}

	public static int getClosestColorID(int pixel, int[] colors) {
		int id = 0;
		int min = Integer.MAX_VALUE;

		int pixR = (pixel >> 16) & 0xFF;
		int pixG = (pixel >> 8) & 0xFF;
		int pixB = pixel & 0xFF;

		int rMean, r, g, b, colR, colG, colB;
		int dist;

		for (int i = 0; i < colors.length; i++) {

			colR = (colors[i] >> 16) & 0xFF;
			colG = (colors[i] >> 8) & 0xFF;
			colB = colors[i] & 0xFF;

			rMean = (pixR + colR) / 2;
			r = pixR - colR;
			g = pixG - colG;
			b = pixB - colB;
			dist = (((512 + rMean) * r * r) >> 8) + 4 * g * g + (((767 - rMean) * b * b) >> 8);

			if (dist < min) {
				min = dist;
				id = i;
			}
		}

		return id;
	}

	// Accepts image and returns it after grayscaling int
	public static BufferedImage GrayScale(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		for (int i = 0; i < height; i++) {

			for (int j = 0; j < width; j++) {

				Color c = new Color(image.getRGB(j, i));
				int red = (int) (c.getRed() * 0.299);
				int green = (int) (c.getGreen() * 0.587);
				int blue = (int) (c.getBlue() * 0.114);
				Color newColor = new Color(red + green + blue,

						red + green + blue, red + green + blue);

				image.setRGB(j, i, newColor.getRGB());
			}
		}
		return image;
	}

}
