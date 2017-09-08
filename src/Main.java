import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * Application single entry point class.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * Margin from the vertical borders.
	 */
	private static final int X_MAGIN = 30;

	/**
	 * Margin from the horizontal borders.
	 */
	private static final int Y_MAGIN = 10;

	/**
	 * Symbols coordinates.
	 */
	private static final int COORDINATES[][] = {

			{ 126 + X_MAGIN, 190 + Y_MAGIN, 314 - X_MAGIN, 330 - Y_MAGIN },
			{ 336 + X_MAGIN, 190 + Y_MAGIN, 524 - X_MAGIN, 330 - Y_MAGIN },
			{ 546 + X_MAGIN, 190 + Y_MAGIN, 734 - X_MAGIN, 330 - Y_MAGIN },
			{ 756 + X_MAGIN, 190 + Y_MAGIN, 944 - X_MAGIN, 330 - Y_MAGIN },
			{ 966 + X_MAGIN, 190 + Y_MAGIN, 1154 - X_MAGIN, 330 - Y_MAGIN },
			{ 126 + X_MAGIN, 332 + Y_MAGIN, 314 - X_MAGIN, 472 - Y_MAGIN },
			{ 336 + X_MAGIN, 332 + Y_MAGIN, 524 - X_MAGIN, 472 - Y_MAGIN },
			{ 546 + X_MAGIN, 332 + Y_MAGIN, 734 - X_MAGIN, 472 - Y_MAGIN },
			{ 756 + X_MAGIN, 332 + Y_MAGIN, 944 - X_MAGIN, 472 - Y_MAGIN },
			{ 966 + X_MAGIN, 332 + Y_MAGIN, 1154 - X_MAGIN, 472 - Y_MAGIN },
			{ 126 + X_MAGIN, 474 + Y_MAGIN, 314 - X_MAGIN, 614 - Y_MAGIN },
			{ 336 + X_MAGIN, 474 + Y_MAGIN, 524 - X_MAGIN, 614 - Y_MAGIN },
			{ 546 + X_MAGIN, 474 + Y_MAGIN, 734 - X_MAGIN, 614 - Y_MAGIN },
			{ 756 + X_MAGIN, 474 + Y_MAGIN, 944 - X_MAGIN, 614 - Y_MAGIN },
			{ 966 + X_MAGIN, 474 + Y_MAGIN, 1154 - X_MAGIN, 614 - Y_MAGIN },
			{ 126 + X_MAGIN, 616 + Y_MAGIN, 314 - X_MAGIN, 756 - Y_MAGIN },
			{ 336 + X_MAGIN, 616 + Y_MAGIN, 524 - X_MAGIN, 756 - Y_MAGIN },
			{ 546 + X_MAGIN, 616 + Y_MAGIN, 734 - X_MAGIN, 756 - Y_MAGIN },
			{ 756 + X_MAGIN, 616 + Y_MAGIN, 944 - X_MAGIN, 756 - Y_MAGIN },
			{ 966 + X_MAGIN, 616 + Y_MAGIN, 1154 - X_MAGIN, 756 - Y_MAGIN },

	};

	/**
	 * Compare two images for similarity.
	 * 
	 * @param image1
	 *            First image.
	 * @param image2
	 *            Second image.
	 * 
	 * @return Similarity as weighted difference number. It it is zero the
	 *         images are identical.
	 */
	public double compare(BufferedImage image1, BufferedImage image2) {
		DataBuffer buffer1 = image1.getData().getDataBuffer();
		int size = buffer1.getSize();

		DataBuffer buffer2 = image2.getData().getDataBuffer();
		if (size != buffer2.getSize()) {
			throw new RuntimeException(
					String.format("Size of the images is different: %d <> %d !", size, buffer2.getSize()));
		}

		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum += Math.abs(buffer1.getElem(i) - buffer2.getElem(i));
		}

		return sum / size;
	}

	/**
	 * Calculate average color for the image.
	 * 
	 * @param image
	 *            Image as input.
	 * @return Average color as integer value.
	 */
	private static int averageColor1(BufferedImage image) {
		long red = 0;
		long green = 0;
		long blue = 0;

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));

				red += pixel.getRed();
				green += pixel.getGreen();
				blue += pixel.getBlue();
			}
		}

		double count = image.getWidth() * image.getHeight();
		return (new Color((int) Math.round((red / count)), (int) Math.round((green / count)),
				(int) Math.round((blue / count)))).getRGB();
	}

	/**
	 * Calculate average color for the image.
	 * 
	 * @param image
	 *            Image as input.
	 * @return Average color as integer value.
	 */
	private static int averageColor2(BufferedImage image) {
		long sum = 0;

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				sum += image.getRGB(x, y);
			}
		}

		return (int) (sum /= image.getWidth() * image.getHeight());
	}

	/**
	 * Calculate average color for the image.
	 * 
	 * @param image
	 *            Image as input.
	 * @return Average color as integer value.
	 */
	private static int averageColor3(BufferedImage image) {
		long red = 0;
		long green = 0;
		long blue = 0;

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));

				red += pixel.getRed();
				green += pixel.getGreen();
				blue += pixel.getBlue();
			}
		}

		return (int) Math.round((red + green + blue) / (3D * image.getWidth() * image.getHeight()));
	}

	/**
	 * Calculate average color for the image.
	 * 
	 * @param image
	 *            Image as input.
	 * @return Average color as integer value.
	 */
	private static int averageColor4(BufferedImage image) {
		double hue = 0;
		double saturation = 0;
		double brightness = 0;

		float hsb[] = { 0, 0, 0 };

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));
				Color.RGBtoHSB(pixel.getRed(), pixel.getGreen(), pixel.getBlue(), hsb);

				hue += hsb[0];
				saturation += hsb[1];
				brightness += hsb[2];
			}
		}

		hue /= image.getWidth() * image.getHeight();
		saturation /= image.getWidth() * image.getHeight();
		brightness /= image.getWidth() * image.getHeight();

		return (Color.getHSBColor((float) hue, (float) saturation, (float) brightness)).getRGB();
	}

	/**
	 * Calculate average color for the image.
	 * 
	 * @param image
	 *            Image as input.
	 * @return Average color as integer value.
	 */
	private static int averageColor5(BufferedImage image) {
		long red = 0;
		long green = 0;
		long blue = 0;

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));

				red += pixel.getRed();
				green += pixel.getGreen();
				blue += pixel.getBlue();
			}
		}

		double count = image.getWidth() * image.getHeight();
		return 1000000 * (int) Math.round((red / count)) + 1000 * (int) Math.round((green / count))
				+ (int) Math.round((blue / count));
	}

	/**
	 * Capture symbols in particular image.
	 * 
	 * @param file
	 *            Full file path and name.
	 * 
	 * @throws IOException
	 *             Exception if there is a problem with the file.
	 */
	private static int[][] captureSymbols(String file, String result, int[][] indices) throws IOException {
		Image input = ImageIO.read(new File(file));

		BufferedImage output = new BufferedImage(input.getWidth(null), input.getHeight(null),
				BufferedImage.TYPE_INT_RGB);

		Graphics g = output.getGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, output.getWidth(), output.getHeight());

		int i = 0, j = 0;
		for (int[] corners : COORDINATES) {
			BufferedImage symbol = ((BufferedImage) input).getSubimage(corners[0], corners[1],
					corners[2] - corners[0] + 1, corners[3] - corners[1] + 1);

			// TODO Histogram should be used.
			indices[i][j] = averageColor5(symbol);

			g.drawImage(input, corners[0], corners[1], corners[2], corners[3], corners[0], corners[1], corners[2],
					corners[3], null);

			j++;
			if (j >= indices[i].length) {
				i++;
				j = 0;
			}
		}

		ImageIO.write((RenderedImage) output, "png", new File(result));

		return indices;
	}

	/**
	 * Application single entry point method.
	 * 
	 * @param args
	 *            Command line arguments.
	 * 
	 * @throws IOException
	 *             Image file problem.
	 */
	public static void main(String[] args) throws IOException {
		for (File file : (new File("./dat")).listFiles()) {
			if (file.isFile() == false) {
				continue;
			}

			if (file.getName().contains(".png") == false) {
				continue;
			}

			System.out.println(Arrays.deepToString(captureSymbols(file.getPath(),
					file.getPath().substring(0, file.getPath().length() - file.getName().length()) + "result"
							+ file.getName(),
					new int[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } })));
		}
	}

}
