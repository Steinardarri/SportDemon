package is.hi.hbvg601.team16.sportdemon.services.implementations;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;

public class NetworkManagerAPI {

    private static NetworkManager mAPI;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    static {
        setupNetworkManagerAPI();
    }

    private static void setupNetworkManagerAPI() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Gson demonGson = new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .setPrettyPrinting()
                .create();
        RxJava3CallAdapterFactory rxAdapter = RxJava3CallAdapterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080") // Local server
                .baseUrl("https://sportdemonserver-production.up.railway.app") // Railway Server
                .addConverterFactory(GsonConverterFactory.create(demonGson))
                .addCallAdapterFactory(rxAdapter)
                .client(httpClient.build())
                .build();

        mAPI = retrofit.create(NetworkManager.class);
    }

    /*
     * Network manager föll til að senda á server
     * Notar NetworkManager.java fyrir köll
     */

    // User Service

    /**
     * @param user sem á að vista
     * @return call til server repo, til að executa í activity
     */
    public Call<User> createAccount(User user) {
        return mAPI.createAccount(user);
    }

    public Call<User> login(String username, String password) {
        User user = new User(username, password, "");
        return mAPI.login(user);
    }

    public Call<User> getUser(UUID id) {
        return mAPI.getUser(id);
    }
    public Call<User> getUser(String username) {
        return mAPI.getUser(username);
    }

    // Workout Service


    // Exercise Service


}
