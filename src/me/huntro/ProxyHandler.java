package me.huntro;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProxyHandler
{
	private static Proxy proxy;
	
	public static URLConnection openConnection(URL url)
	{
		try
		{
			if(proxy == null)
			{
				return url.openConnection();
			}
			
			return url.openConnection(proxy);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			return null;
		}
	}
	
	public static void setProxy(String proxyUrl)
	{
		Matcher matcher = Pattern.compile("https?://((?:\\d{1,3}\\.){3}\\d{1,3}):(\\d{1,5})").matcher(proxyUrl);
		
		if(matcher.matches())
		{
			String host = matcher.group(1);
			int port = Integer.parseInt(matcher.group(2));
			
			proxy = new Proxy(Type.HTTP, new InetSocketAddress(host, port));
		}
	}
}
