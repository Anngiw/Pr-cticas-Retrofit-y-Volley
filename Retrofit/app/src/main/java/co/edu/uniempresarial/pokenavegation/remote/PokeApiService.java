package co.edu.uniempresarial.pokenavegation.remote;

import co.edu.uniempresarial.pokenavegation.model.PokemonDetailResponse;
import  co.edu.uniempresarial.pokenavegation.model.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemon(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
    //Agrega el metodo para consultar el detalle de un Pokémon por su nombre usando @Path
    @GET("pokemon/{name}")
    Call<PokemonDetailResponse> getPokemonDetail(
            @Path("name") String name
    );
}
