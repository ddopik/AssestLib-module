package defaultIntializarion;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.File;

import defaultIntializarion.realm.RealmConfigFile;
import defaultIntializarion.realm.RealmDbMigration;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by ddopikMain on 2/28/2017.
 */


public class AppConfig extends Application {


    public static Realm realm;
    public AppConfig app;


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        MultiDex.install(app);

//        initRealm();
//        setRealmDefaultConfiguration();
//        intializeSteatho();
//        deleteCache(app);   ///for developing        ##################
//        initializeDepInj(); ///intializing Dagger Dependancy
    }

    public void initRealm() {
        Realm.init(this);
    }

    /**
     * delete App Cache and NetWork Cache
     **/
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    /**
     * initialize Realm Instance this method called after Realm.init(context)
     * Note -->this migrate change of tables if Happened
     *
     * @param isFirstLaunch true as app first Time launched
     * @param realmModules  --->realm module graph represent realm objects ex{@link }
     *                      <p>
     *                      Now you can return Realm instance through App by Calling ----> Realm.getDefaultInstance()
     */
    public static void setRealmDefaultConfiguration(boolean isFirstLaunch, Object realmModules) {
        if (isFirstLaunch) {
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().schemaVersion(RealmConfigFile.REALM_VERSION).migration(new RealmDbMigration()).
                    modules(realmModules).build();
            Realm.setDefaultConfiguration(realmConfiguration);
        }
    }


    /**
     * Inspect your Realm Tables through Google Browzer
     */
    public void intializeSteatho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }


//    private void initializeDepInj() {
//        if (appComponent == null) {
//            appComponent = DaggerAppComponent.builder()
//                    .mainAppModule(new MainAppModule(this)).build();
////            appComponent.inject(this);  //this App don't Need Any Dependancyes
//        }
//    }

}
