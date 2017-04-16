package de.joesaxo.library.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class GenericPluginLoader {
	
	@SuppressWarnings("resource")
	public static Class<?>[] loadFile(File file) throws FileNotFoundException, ClassNotFoundException, IOException {
	    Class<?>[] classes = new Class<?>[loadableClasses(file)];
        ClassLoader cl = createClassLoaderFromFile(file);

	    if (file.getName().toLowerCase().endsWith(".class")) {
	        Class<?> cls = loadClass(file, cl);
	        if (cls != null) {
                return new Class<?>[]{cls};
            }
            return new Class<?>[]{};
        }

        int counter = 0;
		JarInputStream jaris = new JarInputStream(new FileInputStream(file));
		JarEntry ent = null;
		while ((ent = jaris.getNextJarEntry()) != null) {
			if (ent.getName().toLowerCase().endsWith(".class")) {
                try {
					classes[counter] = cl.loadClass(ent.getName().substring(0, ent.getName().length() - 6).replace('/', '.'));
				} catch (ClassNotFoundException e) {
                    classes[counter] = null;
					e.printStackTrace();
				}
                counter++;
			}
		}
		jaris.close();
		return removeEmpty(classes);
	}

	public static int loadableClasses(File file) throws IOException {
        if (file.getName().toLowerCase().endsWith(".class")) return 1;
	    int loadableClasses = 0;
        JarInputStream jaris = new JarInputStream(new FileInputStream(file));
        JarEntry ent = null;
        while ((ent = jaris.getNextJarEntry()) != null) {
            if (ent.getName().toLowerCase().endsWith(".class")) {
                loadableClasses++;
            }
        }
        jaris.close();
        return loadableClasses;
    }

    public static ClassLoader createClassLoaderFromFile(File file) throws IOException{
        return new URLClassLoader(new URL[] { file.toURI().toURL() });
    }

    public static Class<?> loadClass(File file) throws ClassNotFoundException, IOException {
	    return loadClass(file, createClassLoaderFromFile(file));
    }

	public static Class<?> loadClass(File classFile, ClassLoader cl) throws ClassNotFoundException {
        if (classFile.getName().toLowerCase().endsWith(".class")) {
            return (cl.loadClass(classFile.getName().substring(0, classFile.getName().length() - 6).replace('/', '.')));
        }
        return null;
    }

	public static Class<?>[] loadPath(File path, String fileType) throws FileNotFoundException, ClassNotFoundException, IOException {
        if (fileType == null) fileType = "";
        File[] files = path.listFiles();
        int loadableClasses = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].toString().endsWith(fileType) && !files[i].isDirectory()) {
                loadableClasses += loadableClasses(files[i]);
            } else {
                files[i] = null;
            }
        }
        files = removeEmpty(files);

        int loadedClasses = 0;
		Class<?>[] classes = new Class<?>[loadableClasses];
		for (int f = 0; f < files.length; f++) {
		    Class<?>[] newClasses = loadFile(files[f]);
		    for (int c = 0; c < newClasses.length; c++) {
		        classes[loadedClasses + c] = newClasses[c];
		        loadedClasses++;
            }
		}
		return removeEmpty(classes);
	}

	public static Class<?>[] loadPath(File path) throws FileNotFoundException, ClassNotFoundException, IOException {
		return loadPath(path, null);
	}

	 //compiler doesn't know that "iface" is P, so parsing "cls" to "Class<P>" is checked with "clsi.equals(iface)"
	public static <C> Class<C>[] classFilter(Class<?>[] classes, Class<C> iface) {
		Class<C>[] pluggableclasses = (Class<C>[])new Class<?>[classes.length];
		for (int i = 0; i < classes.length; i++) {
			if (checkClass(classes[i], iface)) {
				pluggableclasses[i] = (convert(classes[i]));
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


	public static <T> T[] removeEmpty(T[] array) {
		Class<T> arrayType = null;
		int empty = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				empty++;
			} else if (arrayType == null) {
				arrayType = (Class<T>)((T)array[i]).getClass();
			}
		}
		if (arrayType == null) return null;
		int skipped = 0;
		T[] newArray = (T[]) Array.newInstance(arrayType, array.length-empty);
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				newArray[i-skipped] = array[i];
			} else {
				skipped++;
			}
		}
		return newArray;
	}

    public static <E> E[] createArray(E element, int length) {
        return (E[]) Array.newInstance(element.getClass(), length);
    }

}
