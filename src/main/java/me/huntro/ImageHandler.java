package me.huntro;

import java.util.ArrayList;

public class ImageHandler
{
	private static int imageCount = 802;

	private ArrayList<Image> imgs;
	private int next = 0;


	public ImageHandler()
	{
		imgs = new ArrayList<>();

		fetchImages();
	}

	private void fetchImages()
	{
		final Thread imageFetcher = new Thread(() ->
		{
			for(int i = 1; i <= imageCount; i++)
			{
				try
				{
					imgs.add(Cache.getPokemonImage(i));
				}
				catch(Exception e)
				{
					System.out.println("Couldn't fetch image (" + String.format("%03d", i) + ")");
					imageCount--;
				}

				synchronized(this)
				{
					if(imgs.size() > next)
					{
						this.notify();
					}
				}
			}
		});

		imageFetcher.start();
	}

	public Image getNextImage()
	{
		synchronized(this)
		{
			while(imgs.size() <= next)
			{
				try
				{
					wait();
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		Image img = imgs.get(next++);

		if(next >= imageCount)
		{
			next = 0;
		}

		return img;
	}
}
