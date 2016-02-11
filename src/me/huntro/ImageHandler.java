package me.huntro;

import java.net.URL;
import java.util.ArrayList;

public class ImageHandler {
	
	private static final int IMAGE_COUNT = 721;
	
	private ArrayList<Image> imgs;
	private int next = 0;
	
	public ImageHandler() {
		
		imgs = new ArrayList<>();
		
		fetchImages();
	}
	
	private void fetchImages() {
		final Thread imageFetcher = new Thread(() -> {
			for(int i = 1;i <= IMAGE_COUNT;i++) {				
				String url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + String.format("%03d", i) + ".png";
				
				try {
					imgs.add(new Image(new URL(url).openStream()));
				}
				catch(Exception e) {
					System.out.println("Couldn't fetch image (" + url + ")");
				}
				
				synchronized(this) {
					if(imgs.size() > next) {
						this.notify();
					}
				}
			}
		});
		
		imageFetcher.start();
	}
	
	public Image getNextImage() {
				
		synchronized(this) {			
			while(imgs.size() <= next) {
				try {
					wait();
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Image img = imgs.get(next++);
		
		if(next >= IMAGE_COUNT) {
			next = 0;
		}
		
		return img;
	}
}