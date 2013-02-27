package fr.neatmonster.nocheatplus.utilities.build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ResourceUtil {
	
	public static Boolean getBoolean(String input, Boolean preset){
		if (input == null) return preset;
		input = input.trim().toLowerCase();
		if (input.matches("1|true|yes")) return true;
		else if (input.matches("0|false|no")) return false;
		else return preset;
	}
	
	/**
	 * Might have a newline at the end.<br>
	 * TODO: Move to other utility.
	 * 
	 * @param name
	 * @param clazz
	 * @param folderPart
	 * @return
	 */
	public static String fetchResource(Class<?> clazz, String path) {
		String className = clazz.getSimpleName() + ".class";
		String classPath = clazz.getResource(className).toString();
		if (!classPath.startsWith("jar")) return null;
		String absPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/"+path;
		try {
			URL url = new URL(absPath);
			BufferedReader r = null;
			try {
				Object obj  = url.getContent();
				if (obj instanceof InputStream){
					r = new BufferedReader(new InputStreamReader((InputStream) obj));
					StringBuilder builder = new StringBuilder();
					String last = r.readLine();
					while (last != null){
						builder.append(last);
						builder.append("\n"); // does not hurt if one too many.
						last = r.readLine();
					}
					r.close();
					return builder.toString();
				}
				else return null;
			} catch (IOException e) {
				if (r != null){
					try {
						r.close();
					} catch (IOException e1) {
					}
				}
				return null;
			}
		} catch (MalformedURLException e) {
		}
		return null;
	}
	
	/**
	 * New line separated entries, lines starting with '#' are ignored (trim + check), otherwise ini-file style x=y.<br>
	 * All keys and values are trimmed, lines without assignment still get added, all mappings will be the empty string or some content.
	 * @param input
	 * @param map
	 */
	public static void parseToMap(String input, Map<String, String> map){
		final String[] split = input.split("\n");
		for (final String line : split){
			final String trimmed = line.trim();
			if (trimmed.startsWith("#")) continue;
			final String[] parts = line.split("=", 2);
			if (parts.length == 1){
				map.put(parts[0].trim(), "");
			}
			else{
				map.put(parts[0].trim(), parts[1].trim());
			}
		}
	}
}