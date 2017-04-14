package de.joesaxo.library.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class PluginLoader {
	
	@SuppressWarnings("resource")
	public static List<Class<?>> loadFile(File file) throws FileNotFoundException, IOException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		ClassLoader cl = new URLClassLoader(new URL[] { file.toURI().toURL() });
		JarInputStream jaris = new JarInputStream(new FileInputStream(file));
		JarEntry ent = null;
		while ((ent = jaris.getNextJarEntry()) != null) {
			if (ent.getName().toLowerCase().endsWith(".class")) {
					try {
						classes.add(cl.loadClass(ent.getName().substring(0, ent.getName().length() - 6).replace('/', '.')));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			}
		}
		jaris.close();
		return classes;
	}

	public static List<Class<?>> loadPath(File path, String fileType) throws FileNotFoundException, IOException {
		if (fileType == null) fileType = "";
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : path.listFiles()) {
			if(file.toString().endsWith(fileType) && !file.isDirectory()) {
				classes.addAll(loadFile(file));
			}
		}
		return classes;
	}

	public static List<Class<?>> loadPath(File path) throws FileNotFoundException, IOException {
		return loadPath(path, null);
	}

	@SuppressWarnings("unchecked") //compiler doesn't know that "iface" is P, so parsing "cls" to "Class<P>" is checked with "clsi.equals(iface)"
	public static <C> List<Class<C>> filterClasses(List<Class<?>> classes, Class<C> iface) {
		List<Class<C>> pluggableclasses = new ArrayList<Class<C>>();
		for (Class<?> cls : classes) {
			boolean matches = false;
			if (iface.equals(cls.getSuperclass())) matches = true;
			
			for (Class<?> clsi : cls.getInterfaces()) {
				if(iface.equals(clsi)) {
					matches = true;
				}
			}
			if (matches) {
				pluggableclasses.add((Class<C>)cls);
			}
		}
		return pluggableclasses;
	}
}
