package me.huntro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Cache
{
	private static final String ICON_URL = "https://upload.wikimedia.org/wikipedia/en/3/39/Pokeball.PNG";

	private static final String POKEMON_IMG_URL = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/";
	private static final String POKEMON_IMG_FILE_TYPE = "png";

	private static final File cacheDir = new File(".cache");

	static
	{
		if(!cacheDir.exists())
		{
			cacheDir.mkdirs();
		}
	}


	public static Image getPokemonImage(int number)
	{
		File image = new File(cacheDir, number + "." + POKEMON_IMG_FILE_TYPE);

		if(!image.exists())
		{
			final String url = POKEMON_IMG_URL + String.format("%03d", number) + "." + POKEMON_IMG_FILE_TYPE;

			saveToFile(url, image);
		}

		return readImage(image);
	}

	public static Image getIcon()
	{
		File iconFile = new File(cacheDir, "icon.png");

		if(!iconFile.exists())
		{
			saveToFile(ICON_URL, iconFile);
		}

		return readImage(iconFile);
	}

	private static void saveToFile(String url, File file)
	{
		try
		{
			Files.copy(ProxyHandler.openConnection(new URL(url)).getInputStream(), file.toPath());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private static Image readImage(File file)
	{
		Image image = null;

		try
		{
			image = new Image(new FileInputStream(file));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return image;
	}
}
