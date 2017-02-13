package com.example.metje.fixedpricemeal;

/**
 * Created by metje on 2/12/2017.
 */

public class ImageModel
{
	private String name;
	private int image_drawable;

	public ImageModel(String name, int image_drawable)
	{
		this.name = name;
		this.image_drawable = image_drawable;
	}

	public int getImage_drawable()
	{
		return image_drawable;
	}

	public String getName()
	{
		return name;
	}
}
