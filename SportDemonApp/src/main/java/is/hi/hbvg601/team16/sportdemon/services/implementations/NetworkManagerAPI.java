package is.hi.hbvg601.team16.sportdemon.services.implementations;

import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManagerAPI {

    private static NetworkManager mAPI;

    static {
        setupNetworkManagerAPI();
    }

    private static void setupNetworkManagerAPI() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("localhost:8080") // Slóð á server
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mAPI = retrofit.create(NetworkManager.class);
    }

    public static NetworkManager getAPI() {
        return mAPI;
    }
}
