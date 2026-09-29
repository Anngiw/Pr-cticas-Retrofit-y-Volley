package co.edu.uniempresarial.pokenavegation.repository;

import co.edu.uniempresarial.pokenavegation.model.PokemonDetailResponse;
import co.edu.uniempresarial.pokenavegation.model.PokemonResponse;
import co.edu.uniempresarial.pokenavegation.remote.PokeApiService;
import co.edu.uniempresarial.pokenavegation.remote.RetrofitClient;
import retrofit2.Call;

public class PokemonRepository {

    //Declaración de la clase y el servicio
    private final PokeApiService service;

    public PokemonRepository(){
        service = RetrofitClient.getService();
    }

    //Metodo para obtener la lista general
    public Call<PokemonResponse> obtenerPokemon(
            int limit,
            int offser
    ){
        return  service.getPokemon(limit, offser);
    }

    // Metodo para obtener el detalle de un Pokémon específico
    public Call<PokemonDetailResponse> obtenerDetallePokemon(String name) {
        return service.getPokemonDetail(name);
    }

}
