package me.huntro;

import java.io.InputStream;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Image extends javafx.scene.image.Image
{
	private Color averageColor;


	public Image(InputStream is)
	{
		super(is);

		averageColor = calcAverageColor();
	}

	private Color calcAverageColor()
	{
		PixelReader reader = this.getPixelReader();
		double pixelCount = 0;
		double r = 0, g = 0, b = 0;

		for(int y = 0;y < this.getHeight();y++)
		{
			for(int x = 0;x < this.getWidth();x++)
			{
				Color color = reader.getColor(x, y);

				if(color.isOpaque())
				{
					r += color.getRed();
					g += color.getGreen();
					b += color.getBlue();

					pixelCount++;
				}
			}
		}

		return new Color(r / pixelCount, g / pixelCount, b / pixelCount, 1);
	}

	public Color getAverageColor()
	{
		return averageColor;
	}
}
