package br.com.rms.blocodenotas.servicelocator;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class ServiceLocator {

    private Cache cache;

    private ServiceLocator(Cache cache) {
        this.cache = cache;
    }

    public static ServiceLocator create(Cache cache) {
        return new ServiceLocator(cache);
    }

    public <T> T get(Class<T> tClass, String name) {
        String buildName = buildName(tClass, name);
        ObjectFactory<T> factory = (ObjectFactory<T>) cache.get(buildName);
        return Objects.requireNonNull(factory).build();
    }

    public <T> T get(Class<T> tClass) {
        return get(tClass, null);
    }

    public <T> ServiceLocator put(Class<T> tClass, ObjectFactory<T> factory) {
        String buildName = buildName(tClass, null);
        cache.remove(buildName);
        cache.put(buildName, factory);
        return this;
    }

    private <T> String buildName(Class<T> type, String name) {
        if (name == null) {
            name = "";
        }
        return type.getName() + "_" + name;
    }
}
