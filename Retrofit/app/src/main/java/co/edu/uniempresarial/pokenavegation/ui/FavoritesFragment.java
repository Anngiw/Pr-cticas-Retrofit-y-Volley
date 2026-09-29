package co.edu.uniempresarial.pokenavegation.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.edu.uniempresarial.pokenavegation.R;
import co.edu.uniempresarial.pokenavegation.model.Pokemon;
import co.edu.uniempresarial.pokenavegation.ui.adapter.PokemonAdapter;

public class FavoritesFragment extends Fragment {


    //Variables globales
    private RecyclerView recyclerFavorites;
    private TextView textEmptyFavorites;
    private PokemonAdapter adapter;

    // Infla el layout directamente desde el constructor
    public FavoritesFragment() {
        super(R.layout.fragment_favorites);
    }


    //Inicialización de la Vista
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vincular vistas del XML
        initViews(view);

        // Configurar el RecyclerView y el Adaptador
        setupRecyclerView();

        // Cargar los favoritos desde SharedPreferences
        cargarFavoritos();
    }

    //Configuración del Adaptador
    private void setupRecyclerView() {
        adapter = new PokemonAdapter(this::mostrarPokemonSeleccionado);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerFavorites.setHasFixedSize(true);
        recyclerFavorites.setAdapter(adapter);
    }

    //Metodo para traer empaquetar la informacion de info a favoritos
    private void cargarFavoritos() {
        // Leer de SharedPreferences de PokemonPrefs
        SharedPreferences prefs = requireActivity().getSharedPreferences("PokemonPrefs", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        List<Pokemon> favoriteList = new ArrayList<>();

        // Recorrer las claves guardadas
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String pokemonName = entry.getKey();

            // Verificar que sea un favorito válido (booleano en true)
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                Pokemon pokemon = new Pokemon();
                pokemon.setName(pokemonName);
                favoriteList.add(pokemon);
            }
        }

        // Validar si la lista está vacía para mostrar el mensaje o los datos
        if (favoriteList.isEmpty()) {
            recyclerFavorites.setVisibility(View.GONE);
            textEmptyFavorites.setVisibility(View.VISIBLE);
        } else {
            recyclerFavorites.setVisibility(View.VISIBLE);
            textEmptyFavorites.setVisibility(View.GONE);
            adapter.actualizarDatos(favoriteList);
        }
    }

    //empaca el nombre del Pokémon seleccionado en un Bundle, lo envía al InfoFragment y realiza la transacción visual en el contenedor principal para abrir su pantalla de detalles.
    private void mostrarPokemonSeleccionado(Pokemon pokemon) {
        // Al hacer clic en un favorito, navegar al detalle pasando el Bundle
        Bundle bundle = new Bundle();
        bundle.putString("pokemon_name", pokemon.getName());

        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, infoFragment)
                .addToBackStack(null)
                .commit();
    }

   //Inicializa objetos de la vista
    private void initViews(View view) {
        recyclerFavorites = view.findViewById(R.id.recyclerFavorites);
        textEmptyFavorites = view.findViewById(R.id.textEmptyFavorites);
    }
}