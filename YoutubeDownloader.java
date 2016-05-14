package youtubeDownloader;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.io.*;


public class YoutubeDownloader {
	
	static int vidName = 1;
	static void download(String url, String label) {
		try {
			String cmd = "youtube-dl " + url + " -o ./" + "youtube" + "/";
			cmd += String.format("%s", vidName);
			vidName++;
			
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
			BufferedReader stdErr = new BufferedReader( new InputStreamReader(proc.getErrorStream()));
			
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}
			
			while ((s = stdErr.readLine()) != null) {
				System.out.println(s);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error when downloading file");
		}
	}
	
	public static void main(String args[]) throws ParseException {
		ArrayList<Videos> videos = new ArrayList<Videos>();
		
		File filein = new File("act.json");
		//File fileout = new File("newfile.json");
		String a = filein.getAbsolutePath();
				 
		try {
			Scanner input = new Scanner(filein);
			StringBuilder strBld = new StringBuilder();
			
			// scan each line in input and append each line to the string builder object
			while (input.hasNextLine())
			{
				strBld.append(input.nextLine());
				strBld.append("\n");
			}
			
			
			JSONParser parser = new JSONParser();
			// create a root json object which parsed by input file
			JSONObject objRoot = (JSONObject) parser.parse(strBld.toString());
			
			JSONObject database = (JSONObject) objRoot.get("database");

			Set<String> id = database.keySet();
			
			// initialize iterator for id set
			Iterator<String> idName = id.iterator();
			
			// iterate through the object and get value of each element
			while (idName.hasNext())
			{
				String name = idName.next();
				JSONObject obj = (JSONObject) database.get(name);
				String  subset = (String) obj.get("subset");
				
				
				if (subset.equals("training"))
				{
					JSONArray anno = (JSONArray)obj.get("annotations");
					
					for (int i = 0; i < anno.size(); i++)
					{
						JSONObject i1 = (JSONObject) anno.get(i);
						String label = (String) i1.get("label");
						
						JSONArray segment = (JSONArray) i1.get("segment");
						double start = (double) segment.get(0);
						double end = (double) segment.get(1);
						
						String url = (String) obj.get("url");
							
						Videos vid = new Videos();
						vid.setStart(start);
						vid.setEnd(end);
						vid.setLabel(label);
						vid.setUrl(url);
						
						videos.add(vid);
						
						System.out.printf("label: %s\turl: %s\tstart: %.0f\tend: %.0f\n", label, url, start, end);
						
						
					}
				}
			}
			
			for (int i = 0; i < videos.size(); i++)
			{
				System.out.printf("label: %s\turl: %s\n", videos.get(i).getLabel(), videos.get(i).getUrl());
				download(videos.get(i).getUrl(), videos.get(i).getLabel());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
}

