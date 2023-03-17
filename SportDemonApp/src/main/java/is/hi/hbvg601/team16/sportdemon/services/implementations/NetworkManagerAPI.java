package is.hi.hbvg601.team16.sportdemon.services.implementations;

import android.os.Handler;
import android.os.Looper;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManagerAPI {

    private static NetworkManager mAPI;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    static {
        setupNetworkManagerAPI();
    }

    private static void setupNetworkManagerAPI() {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080") // Local server
//                    .baseUrl("https://sportdemonserver-production.up.railway.app") // Railway Server
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            mAPI = retrofit.create(NetworkManager.class);
    }

    public static NetworkManager getAPI() {
        return mAPI;
    }

    /*
     * Network manager föll til að senda á server
     * Notar NetworManager.java fyrir köll
     */

    // User Service

    /**
     *
     * @param user sem á að vista
     * @return String með 'Failure' eða 'Success', eftir því hvernig tókst að vista
     */
    public String createAccount(User user) {
        AtomicReference<Response<String>> response = new AtomicReference<>();
        AtomicReference<String> returnString = new AtomicReference<>("Failure");

        executor.execute(() -> {
            //Background work here
            Call<String> callSync = mAPI.createAccount(user);
            try {
                response.set(callSync.execute());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            handler.post(() -> {
                //UI Thread work here
                if (response.get() != null) {
                    returnString.set(response.get().body());
                }
            });
        });

        return returnString.get();
    }

    public User getUser(UUID id) {
        AtomicReference<Response<User>> response = new AtomicReference<>();
        AtomicReference<User> returnUser = new AtomicReference<>(null);

        executor.execute(() -> {
            //Background work here
            Call<User> callSync = mAPI.getUser(id);
            try {
                response.set(callSync.execute());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            handler.post(() -> {
                //UI Thread work here
                if (response.get() != null) {
                    returnUser.set(response.get().body());
                }
            });
        });

        return returnUser.get();
    }

    public User getUser(String username) {
        AtomicReference<Response<User>> response = new AtomicReference<>();
        AtomicReference<User> returnUser = new AtomicReference<>(null);

        executor.execute(() -> {
            //Background work here
            Call<User> callSync = mAPI.getUser(username);
            try {
                response.set(callSync.execute());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            handler.post(() -> {
                //UI Thread work here
                if (response.get() != null) {
                    returnUser.set(response.get().body());
                }
            });
        });

        return returnUser.get();
    }

    // Workout Service


    // Exercise Service


}
