package de.MiniDigger.RideThaMob;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

//import org.bukkit.configuration.file.FileConfiguration;

public class Lang {

	// private static FileConfiguration config;
	private static List<String> lines;
	private static HashMap<LangType, String> trans;

	public Lang() {
		// config = RideThaMob.pl.getConfig();
		try {
			lines = Files.readAllLines(
					Paths.get(RideThaMob.pl.getDataFolder() + File.separator
							+ "msgs.properties"), Charset.defaultCharset());
		} catch (IOException e) {
			Bukkit.getConsoleSender()
					.sendMessage(
							ChatColor.RED
									+ "[RideThaMob] WARINING: Failed to load messages!");
			File f = new File(RideThaMob.pl.getDataFolder(), "msgs.properties");
			try {
				f.createNewFile();

				ClassLoader cl = this.getClass().getClassLoader();
				InputStream is = cl
						.getResourceAsStream("default_msgs.properties");

				Scanner scanner = new Scanner(is);
				PrintWriter out = new PrintWriter(f);

				while (scanner.hasNext()) {
					out.println(scanner.nextLine());
				}

				try {
					scanner.close();
					out.close();
					is.close();
				} catch (Exception ex) {

				}
				
				Bukkit.getConsoleSender()
						.sendMessage(
								ChatColor.GREEN
										+ "[RideThaMob] INFO: Successfully imported default language (en_US)!");
				// config = RideThaMob.pl.getConfig();
				try {
					lines = Files.readAllLines(
							Paths.get(RideThaMob.pl.getDataFolder() + File.separator
									+ "msgs.properties"), Charset.defaultCharset());
				} catch (IOException ex) {
					Bukkit.getConsoleSender()
					.sendMessage(
							ChatColor.RED
									+ "[RideThaMob] WARINING: Failed to create a new msg file!!");
				}
			} catch (IOException e1) {
				Bukkit.getConsoleSender()
						.sendMessage(
								ChatColor.RED
										+ "[RideThaMob] WARINING: Failed to create a new msg file!");
			}
		}
		trans = new HashMap<>();
	}

	public static String _(LangType type) {
		return get(type);
	}

	public static String _(LangType type, String... args) {
		String s = get(type);
		for (int i = 0; i < args.length; i++) {
			s = s.replaceAll("%" + i + "%", args[i]);
		}
		return s;
	}

	private static String get(LangType type) {
		if (trans.containsKey(type)) {
			return trans.get(type);
		}
		for (String s : lines) {
			if (s.contains(type.name())) {
				String r = s.replaceFirst(type.name() + " = ", "");
				r = r.replaceAll("\"", "");
				trans.put(type, r);
				return r;
			}
		}
		if(lines.size() == 0){
			new Lang();
		}
		return type.name();
		// return config.getString("msgs." + type.name().toLowerCase());
	}
}
