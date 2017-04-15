package de.joesaxo.library.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class PluginLoader {
	
	@SuppressWarnings("resource")
	public static List<Class<?>> loadFile(File file) throws FileNotFoundException, IOException {
	    List<Class<?>> classes = new ArrayList<>();
        ClassLoader cl = createClassLoaderFromFile(file);

	    if (file.getName().toLowerCase().endsWith(".class")) {
	        Class<?> cls = loadClass(file, cl);
	        if (cls != null) {
	            classes.add(cls);
            }
            return classes;
        }

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

    public static ClassLoader createClassLoaderFromFile(File file) throws IOException{
        return new URLClassLoader(new URL[] { file.toURI().toURL() });
    }

    public static Class<?> loadClass(File file) throws IOException{
	    return loadClass(file, createClassLoaderFromFile(file));
    }

	public static Class<?> loadClass(File classFile, ClassLoader cl) {
        if (classFile.getName().toLowerCase().endsWith(".class")) {
            try {
                return (cl.loadClass(classFile.getName().substring(0, classFile.getName().length() - 6).replace('/', '.')));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
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

	 //compiler doesn't know that "iface" is P, so parsing "cls" to "Class<P>" is checked with "clsi.equals(iface)"
	public static <C> List<Class<C>> classFilter(List<Class<?>> classes, Class<C> iface) {
		List<Class<C>> pluggableclasses = new ArrayList<>();
		for (Class<?> cls : classes) {
			if (checkClass(cls, iface)) {
				pluggableclasses.add(convert(cls));
			}
		}
		return pluggableclasses;
	}

	public static <C> boolean checkClass(Class<?> cls, Class<C> iface) {
		if (iface.equals(cls.getSuperclass())) return true;

		for (Class<?> clsi : cls.getInterfaces()) {
			if(iface.equals(clsi)) {
				return true;
			}
		}

		return false;
	}

	public static <C> Class<C> convert(Class<?> cls) {
		return (Class<C>) cls;
	}


}
