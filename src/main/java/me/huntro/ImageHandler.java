package me.huntro;

import java.util.ArrayList;

public class ImageHandler
{
	private static final int imageCount = 802;

	private final ArrayList<Image> imgs = new ArrayList<>(imageCount);
	private int next = 0;


	public ImageHandler()
	{
		fetchImages();
	}

	private void fetchImages()
	{
		final Thread imageFetcher = new Thread(() ->
		{
			for(int i = 1; i <= imageCount; ++i)
			{
				imgs.add(Cache.getPokemonImage(i));

				if(imgs.size() > next)
				{
					synchronized(this)
					{
						notify();
					}
				}
			}
		});

		imageFetcher.start();
	}

	public Image getNextImage()
	{
		while(imgs.size() <= next)
		{
			try
			{
				synchronized(this)
				{
					wait();
				}
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
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
