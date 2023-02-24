package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkManager {

    @POST("signup")
    Call<User> createAccount(@Body User user);

}
