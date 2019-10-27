package br.com.rms.blocodenotas.servicelocator;

import java.util.Hashtable;
import java.util.Map;

public class Cache {

    private Map<String, ObjectFactory> factoryMap;

    private Cache(Map<String, ObjectFactory> factoryMap) {
        this.factoryMap = factoryMap;
    }

    public static Cache create() {
        return new Cache(new Hashtable());
    }

    <T> void put(String buildName, ObjectFactory<T> factory) {
        factoryMap.put(buildName, factory);
    }

    ObjectFactory get(String buildName) {
        return factoryMap.get(buildName);
    }

    public void remove(String buildName) {
        factoryMap.remove(buildName);
    }

    public void clear() {
        factoryMap.clear();
    }
}
