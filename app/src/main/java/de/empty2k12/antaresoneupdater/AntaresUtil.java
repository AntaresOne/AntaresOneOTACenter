package de.empty2k12.antaresoneupdater;

import android.content.*;
import android.net.*;
import android.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import org.json.*;

public class AntaresUtil
{

	public String testIt(Context cont, boolean manuallyStarted)
	{
		ConnectivityManager connManager = (ConnectivityManager) cont.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mWifi.isConnected() || manuallyStarted)
		{
			URL url;
			try
			{
				url = new URL(urll);
				HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

				if (con != null)
				{
					try
					{
						BufferedReader br = 
							new BufferedReader(
							new InputStreamReader(con.getInputStream()));

						StringBuilder sb = new StringBuilder();

						String line;
						while ((line = br.readLine()) != null)
						{
							sb.append(line + "\n");
						}
						br.close();
						return sb.toString();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return "Error";
	}

	public List<DLFile> handleJson(String result)
	{
		List<DLFile> list = new ArrayList<DLFile>();
		try
		{
			JSONObject obj = new JSONObject(result);
			JSONArray jArray = obj.getJSONObject("DATA").getJSONArray("files");

			for (int i = 0; i < jArray.length(); i++)
			{
				JSONObject currentFile = jArray.getJSONObject(i);
				Log.e("ant", "added " + currentFile.getString("name") + " " + currentFile.getString("url"));
				list.add(new DLFile(currentFile.getString("name"), currentFile.getString("url")));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return list;
	}

	//cm-12-20141117-UNOFFICIAL-jfltexx.zip
	public int stripVersionNumber(String fullName)
	{
		String[] parts = fullName.split("-");
		try
		{
			if (!fullName.startsWith("cm"))
			{
				return Integer.parseInt(parts[1]);
			}
			return Integer.parseInt(parts[2]);
		}
		catch (Exception e)
		{
			Log.e("AntaresOne Updater", "stripVersionNumber has encountered a issue: " + fullName);
			e.printStackTrace();
		}
		return 0;
	}

	public String getRoDotCmDotDisplayDotVersion()
	{
		java.lang.Process p = null;
		try
		{
			p = new ProcessBuilder("/system/bin/getprop", "ro.cm.display.version").redirectErrorStream(true).start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null)
			{
				return line;
			}
			p.destroy();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "Error";
	}

	public DLFile max(List<DLFile> files)
	{
		if(files.size() == 0)
		{
			return new DLFile("", "");
		}
		DLFile currentBiggest = files.get(0);
		for (DLFile f : files)
		{
			int cNum = stripVersionNumber(currentBiggest.getName());
			int fNum = stripVersionNumber(f.getName());
			if (cNum < fNum)
			{
				currentBiggest = f;
				Log.e("ant", "replaced f: "+ cNum + " with" + fNum);
			}
		}
		return currentBiggest;
	}

	String urll = "https://www.androidfilehost.com/api/?action=folder&flid=21406&api_key=000aaa000aaa&uid=56789";


}
