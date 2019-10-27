package br.com.rms.blocodenotas;

import android.app.Application;

import br.com.rms.blocodenotas.database.DataBaseHelper;
import br.com.rms.blocodenotas.database.NotesDataBaseHelper;
import br.com.rms.blocodenotas.servicelocator.Cache;
import br.com.rms.blocodenotas.servicelocator.ServiceLocator;

public class App extends Application {

    ServiceLocator service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = configureServiceLocator();
        addServices();
    }

    private void addServices() {
        service
                .put(App.class, () -> App.this)
                .put(DataBaseHelper.class, this::buildDataBaseHelper)
                .put(NotesDataBaseHelper.class, this::buildNotesDataBase);
    }

    private ServiceLocator configureServiceLocator() {
        Cache cache = Cache.create();
        return ServiceLocator.create(cache);
    }

    private DataBaseHelper buildDataBaseHelper() {
        return new DataBaseHelper(service.get(App.class));

    }

    private NotesDataBaseHelper buildNotesDataBase() {
        return new NotesDataBaseHelper(service.get(DataBaseHelper.class));
    }
}
