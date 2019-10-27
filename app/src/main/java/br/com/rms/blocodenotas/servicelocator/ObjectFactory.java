package br.com.rms.blocodenotas.servicelocator;

public interface ObjectFactory<T> {
    T build();
}
