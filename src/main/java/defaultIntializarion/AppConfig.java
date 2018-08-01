package defaultIntializarion;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.File;
import java.util.LinkedList;

import defaultIntializarion.realm.RealmConfigFile;
import defaultIntializarion.realm.RealmDbMigration;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import network.BasicAuthInterceptor;
import okhttp3.OkHttpClient;


/**
 * Created by ddopikMain on 2/28/2017.
 */


public class AppConfig extends Application {


    public static Realm realm;
    public static AppConfig app;



    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        MultiDex.install(app);

//        initRealm(); //--> [1]order is must
//        setRealmDefaultConfiguration(); //--> [2]order is must
//        intializeSteatho();
//        deleteCache(app);   ///for developing        ##################
//        initializeDepInj(); ///intializing Dagger Dependancy
    }

    /**
     * use this method in case initializing object by --new ()-- keyword
     *
     * @param app app Context
     */
    public static void init(@NonNull final Application app) {
        AppConfig.app = (AppConfig) app;
    }


    public static AppConfig getApp() {
        if (app != null) {
            return app;
        }
        throw new NullPointerException("u should init AppContext  first");
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

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    public void initRealm() {
        Realm.init(this);
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


    public void initFastAndroidNetworking() {

/**
 * initializing block to add authentication to your Header Request
 * **/
//        BasicAuthInterceptor basicAuthInterceptor = new BasicAuthInterceptor(getApplicationContext());
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .addNetworkInterceptor(basicAuthInterceptor)
//                .build();
//        AndroidNetworking.initialize(this, okHttpClient);
///////////////////////
        /**
         * default initialization
         * */
        AndroidNetworking.initialize(this);
//
    }


}
