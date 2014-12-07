package de.empty2k12.antaresoneupdater;

public class DLFile
{

	String name;
	String dlLink;

	public DLFile(String name, String dlLink)
	{
		this.name = name;
		this.dlLink = dlLink;
	}

	public String getName()
	{
		return name;
	}

	public String getDownloadLink()
	{
		return dlLink;
	}
}
