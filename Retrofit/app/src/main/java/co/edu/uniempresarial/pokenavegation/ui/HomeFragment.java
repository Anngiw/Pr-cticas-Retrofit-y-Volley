package co.edu.uniempresarial.pokenavegation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.uniempresarial.pokenavegation.R;
import co.edu.uniempresarial.pokenavegation.model.Pokemon;
import co.edu.uniempresarial.pokenavegation.model.PokemonResponse;
import co.edu.uniempresarial.pokenavegation.repository.PokemonRepository;
import co.edu.uniempresarial.pokenavegation.ui.adapter.PokemonAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    //ariables Globales
    private RecyclerView recyclerPokemon;
    private CircularProgressIndicator progressIndicator;
    private LinearLayout errorContainer;
    private TextView tvError;
    private MaterialButton btnRetry;
    private PokemonAdapter adapter;
    private PokemonRepository repository;
    private Call<PokemonResponse> currentCall;
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    //Inicialización de la Vista
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerPokemon = view.findViewById(R.id.recyclerPokemon);
        progressIndicator = view.findViewById(R.id.progressIndicator);
        errorContainer = view.findViewById(R.id.errorContainer);
        tvError = view.findViewById(R.id.tvError);
        btnRetry = view.findViewById(R.id.btnRetry);

        // Inicializa el adaptador y conecta la acción del clic usando una expresión lambda
        adapter = new PokemonAdapter(this::mostrarPokemonSeleccionado);
        repository = new PokemonRepository();
        recyclerPokemon.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        recyclerPokemon.setHasFixedSize(true);
        recyclerPokemon.setAdapter(adapter);
        btnRetry.setOnClickListener(v -> cargarPokemon());
        cargarPokemon();
    }


    //Petición de Datos a la API
    private void cargarPokemon() {
        mostrarCargando();
        currentCall = repository.obtenerPokemon(30, 0);
        currentCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call,
                                   @NonNull Response<PokemonResponse> response) {
                if (!isAdded()) {
                    return;
                }
                PokemonResponse body = response.body();
                if (response.isSuccessful()
                        && body != null
                        && body.getResults() != null) {
                    adapter.actualizarDatos(body.getResults());
                    mostrarContenido();
                } else {
                    mostrarError(
                            "No fue posible obtener los Pokémon. "
                                    + "Código HTTP: " + response.code()
                    );
                }
            }

            //Si falla el internet o el servidor no responde, muestra un mensaje amigable de error y habilita el botón de reintentar.
            @Override
            public void onFailure(
                    @NonNull Call<PokemonResponse> call,
                    @NonNull Throwable throwable
            ) {
                if (call.isCanceled() || !isAdded()) {
                    return;
                }
                mostrarError(
                        "Error de conexión. Verifique internet "
                                + "e intente nuevamente."
                );
            }
        });
    }

    //Carga los datos de l pokemon seleccionado
    private void mostrarPokemonSeleccionado(Pokemon pokemon) {
        //Crear el Bundle con el nombre del Pokémon seleccionado
        Bundle bundle = new Bundle();// instancia un objeto de la clase Bundle que sirve para almacenar y pasar datos de un tipo primitivo u objetos serializables en formato clave-valor.
        bundle.putString("pokemon_name", pokemon.getName());

        // Instanciar el InfoFragment y asignarle los argumentos
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(bundle);

        // Realizar la transacción para reemplazar el fragmento actual en el contenedor
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, infoFragment)
                .addToBackStack(null) // Permite volver atrás con el botón del celular
                .commit();
    }

    // Control de Estados Visuales y Cierre de Seguridad
    private void mostrarCargando() {
        progressIndicator.setVisibility(View.VISIBLE);
        recyclerPokemon.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
    }
    private void mostrarContenido() {
        progressIndicator.setVisibility(View.GONE);
        recyclerPokemon.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
    }
    private void mostrarError(String mensaje) {
        progressIndicator.setVisibility(View.GONE);
        recyclerPokemon.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        tvError.setText(mensaje);
    }

    //Ciclo de vida -  limpiar la memoria y cancelar tareas pendientes
    @Override
    public void onDestroyView() {
        if (currentCall != null) {
            currentCall.cancel();
        }
        recyclerPokemon.setAdapter(null);
        super.onDestroyView();
    }
}
