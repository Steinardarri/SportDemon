package is.hi.hbvg601.team16.sportdemon.services.implementations;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Gson demonGson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080") // Local server
                .baseUrl("https://sportdemonserver.up.railway.app") // Railway Server
                .addConverterFactory(GsonConverterFactory.create(demonGson))
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
     * @return String með 'Failure' eða 'Success', eftir því hvernig tókst að vista
     */
    public String createAccount(User user) {
        AtomicReference<Response<User>> response = new AtomicReference<>();
        AtomicReference<String> returnString = new AtomicReference<>();

        executor.execute(() -> {
            //Background work here
            Call<User> callSync = mAPI.createAccount(user);
            try {
                response.set(callSync.execute());
                System.out.println(response.get().body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            handler.post(() -> {
                //UI Thread work here
                if(response.get().body() == null) {
                    returnString.set("Failure");
                } else {
                    returnString.set("Success");
                }
            });
        });

        return returnString.get();
    }

    public User login(String username, String password) {
        AtomicReference<Response<User>> response = new AtomicReference<>();
        AtomicReference<User> returnUser = new AtomicReference<>(null);

        executor.execute(() -> {
            //Background work here
            User u = new User(username, password,"");
            Call<User> callSync = mAPI.login(u);
            try {
                response.set(callSync.execute());
                System.out.println(response.get().body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            handler.post(() -> {
                //UI Thread work here
                returnUser.set(response.get().body());
            });
        });

        return returnUser.get();
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
