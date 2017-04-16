package de.joesaxo.library.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static de.joesaxo.library.plugin.GenericPluginLoader.*;

public class GenericPluginManager<P> {
	
	private Class<P> pluginType;
	
	private P[] pluginClasses;
	
	public GenericPluginManager(Class<P> pluginType) {
		this.pluginType = pluginType;
	}

	public int loadPluginsFromFile(File file) {
        P[] newPlugins;
        try {
            newPlugins = instantiatePlugins(filterClasses(loadFile(file)));
        } catch (ClassNotFoundException e) {
            return -1;
        } catch (FileNotFoundException e) {
            return -1;
        } catch (IOException e) {
            return -1;
        }
        addClasses(newPlugins);
		return newPlugins.length;
	}

	public int loadPluginsFromPath(File path, String fileType) {
		if (fileType == null) fileType = "";
		P[] newPlugins;
		try {
			newPlugins = instantiatePlugins(filterClasses(loadPath(path, fileType)));
		} catch (FileNotFoundException e) {
			return -1;
		} catch (ClassNotFoundException e) {
            return -1;
        } catch (IOException e) {
            return -1;
        }
        addClasses(newPlugins);
		return newPlugins.length;
	}

	public int loadPluginsFromPath(File path) {
        return loadPluginsFromPath(path, null);
    }

	protected Class<P>[] filterClasses(Class<?>[] classes) {
		return classFilter(classes, pluginType);
	}

	private P[] instantiatePlugins(Class<P>[] plugins) {
		P[] instances = null;
		for (int i = 0; i < plugins.length; i++) {
			P instance;
			try {
				instance = (P) plugins[i].newInstance();
				if (instances == null) instances = createArray(instance, plugins.length);
				instances[i] = instance;
			} catch (InstantiationException e) {
				instantiationException(e, plugins[i].getName());
			} catch (IllegalAccessException e) {
				illegalAccessException(e, plugins[i].getName());
			}
		}
		return removeEmpty(instances);
	}

	private void addClasses(P[] newClasses) {
		if (newClasses == null || newClasses.length <= 0) return;
		if (pluginClasses == null) {
			pluginClasses = newClasses;
			return;
		}
		P[] newClassArray = createArray(newClasses[0], pluginClasses.length + newClasses.length);
		for (int i = 0; i < pluginClasses.length; i++) {
			newClassArray[i] = pluginClasses[i];
		}

		for (int i = 0; i < newClasses.length; i++) {
			newClassArray[pluginClasses.length + i] = newClasses[i];
		}
		pluginClasses = newClassArray;
	}

	public P[] getLoadedPlugins() {
		return pluginClasses;
	}

	public void instantiationException(InstantiationException e, String pluginName) {}
	
	public void illegalAccessException(IllegalAccessException e, String pluginName) {}
    
}
