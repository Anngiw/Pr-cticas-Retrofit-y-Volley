package co.edu.uniempresarial.pokenavegation.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.edu.uniempresarial.pokenavegation.model.PokemonDetailResponse;
import co.edu.uniempresarial.pokenavegation.repository.PokemonRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Convierte esta clase en un ViewModel oficial de Android para que conserve la información ante rotaciones de pantalla.
public class PokemonDetailViewModel extends ViewModel {
    private final PokemonRepository repository = new PokemonRepository();
    private final MutableLiveData<PokemonDetailResponse> pokemonDetail = new MutableLiveData<>();


    //versión de solo lectura - puede observar los cambios para pintar la pantalla, pero no puede alterar ni borrar los datos
    public LiveData<PokemonDetailResponse> getPokemonDetail() {
        return pokemonDetail;
    }


    //Metodo que recibe el nombre del Pokemon
    public void fetchPokemonDetail(String name) {
        repository.obtenerDetallePokemon(name).enqueue(new Callback<PokemonDetailResponse>() {
                                               //enqueue : ejecutar una petición de red de forma asíncrona.
                                               // la petición se realiza en un hilo de fondo
            @Override

            //Se ejecuta cuando el servidor responde correctamente.
            public void onResponse(Call<PokemonDetailResponse> call, Response<PokemonDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemonDetail.setValue(response.body());//Toma los datos detallados que llegaron del servidor y se los asigna actualizando automáticamente la pantalla
                }
            }


            //Se ejecuta si ocurre un problema de conexión
            @Override
            public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}