package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManagerAPI {

    private static NetworkManager mAPI;

    static {
        setupNetworkManagerAPI();
    }

    private static void setupNetworkManagerAPI() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("localhost:8080") // TODO: Slóð á server
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        mAPI = retrofit.create(NetworkManager.class);
    }

    public static NetworkManager getAPI() {
        return mAPI;
    }

    public User getUser(UUID id) {
        Call<User> callSync = mAPI.getUser(id);

        try {
            Response<User> response = callSync.execute();
            return response.body(); //TODO: GSON breyta, hugsanlega
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public User getUser(String username) {
        Call<User> callSync = mAPI.getUser(username);

        try {
            Response<User> response = callSync.execute();
            return response.body(); //TODO: GSON breyta, hugsanlega
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User createAccount(User user) {
        Call<User> callSync = mAPI.createAccount(user);

        try {
            Response<User> response = callSync.execute();
            return response.body(); //TODO: GSON breyta, hugsanlega
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
