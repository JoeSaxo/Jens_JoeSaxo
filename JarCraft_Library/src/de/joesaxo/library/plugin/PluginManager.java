package de.joesaxo.library.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.joesaxo.library.plugin.PluginLoader.*;

public class PluginManager <P> {
	
	private Class<P> pluginType;
	
	private List<P> pluginClasses;
	
	public PluginManager(Class<P> pluginType) {
		this.pluginType = pluginType;
		pluginClasses = new ArrayList<P>();
	}

	public int loadPluginsFromFile(File file) throws FileNotFoundException, IOException {
		List<P> newPlugins = instantiatePlugins(filterClasses(loadFile(file)));
		pluginClasses.addAll(newPlugins);
		return newPlugins.size();
	}

	public int loadPluginsFromPath(File path, String fileType) throws IOException {
		if (fileType == null) fileType = "";
		List<P> newPlugins;
		try {
			newPlugins = instantiatePlugins(filterClasses(loadPath(path, fileType)));
		} catch (FileNotFoundException e) {
			return -1;
		}
		pluginClasses.addAll(newPlugins);
		return newPlugins.size();
	}

	public int loadPluginsFromPath(File path) throws IOException {
		return loadPluginsFromPath(path, null);
	}

	protected List<Class<P>> filterClasses(List<Class<?>> classes) {
		return classFilter(classes, pluginType);
	}

	private List<P> instantiatePlugins(List<Class<P>> plugins) {
		List<P> instances = new ArrayList<P>();
		for (Class<P> cls : plugins) {
			P instance;
			try {
				instance = (P) cls.newInstance();
				instances.add(instance);
			} catch (InstantiationException e) {
				instantiationException(e, cls.getName());
			} catch (IllegalAccessException e) {
				illegalAccessException(e, cls.getName());
			}
		}
		return instances;
	}

	public List<P> getLoadedPlugins() {
		return pluginClasses;
	}

	public void instantiationException(InstantiationException e, String pluginName) {}
	
	public void illegalAccessException(IllegalAccessException e, String pluginName) {}
    
}
