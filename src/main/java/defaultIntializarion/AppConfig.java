package defaultIntializarion;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by ddopikMain on 2/28/2017.
 */


public class AppConfig extends Application  {


    public  AppConfig app;
    public static Realm realm;

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
//        intializeRealmInstance(); //intializing Realm Config Instance
//        intializeSteatho();
//        deleteCache(app);   ///for developing        ##################

//        initializeDepInj(); ///intializing Dagger Dependancy
    }

//
//    public AppConfig(Context context)
//    {
//        this.app=(AppConfig)context;
//    }








    public void intializeRealmInstance() {
        // Initialize Realm
        Realm.init(app); // Initialize Realm. Should only be done once when the application starts.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();

        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts. #################


        Realm.setDefaultConfiguration(realmConfig);
        this.realm = Realm.getDefaultInstance();

    }

    public void intializeSteatho()
    {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
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
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


//    private void initializeDepInj() {
//        if (appComponent == null) {
//            appComponent = DaggerAppComponent.builder()
//                    .mainAppModule(new MainAppModule(this)).build();
////            appComponent.inject(this);  //this App don't Need Any Dependancyes
//
//            Stetho.initialize(
//                    Stetho.newInitializerBuilder(this)
//                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
//                            .build());
//
//        }
//    }

}
