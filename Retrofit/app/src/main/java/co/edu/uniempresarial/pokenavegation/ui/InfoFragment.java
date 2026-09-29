package co.edu.uniempresarial.pokenavegation.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import co.edu.uniempresarial.pokenavegation.R;

public class InfoFragment extends Fragment {

    // Declaración de variables de vistas
    private TextView textName;
    private TextView textType;
    private TextView textHeight;
    private TextView textWeight;
    private TextView textExperience;
    private ImageView imagePokemon;
    private Button btnFavorite;

    private PokemonDetailViewModel viewModel;
    private String pokemonName;

    // Inflar el layout directamente desde el constructor como lo hace la profesora
    public InfoFragment() {
        super(R.layout.fragment_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);// Inicializar y vincular vistas
        recuperarArgumentos();// Recuperar argumentos por nombre del Pokémon
        initViewModel(); //Inicializar ViewModel y observadores
        configurarAcciones();// Configurar botones o interacciones
        cargarDetallePokemon();// Cargar los datos desde la API
    }
    //Metodo para traer pokemon por nombre
    private void recuperarArgumentos() {
        if (getArguments() != null) {
            pokemonName = getArguments().getString("pokemon_name");
        }
        // Si el usuario ingresa directamente desde el menú inferior
        if (pokemonName == null || pokemonName.isEmpty()) {
            pokemonName = "pikachu";
        }
    }

    //Metodo para modelar los datos en la vista
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PokemonDetailViewModel.class);

        // Observar los datos de la API de forma limpia
        viewModel.getPokemonDetail().observe(getViewLifecycleOwner(), pokemon -> {
            if (pokemon != null) {
                textName.setText("Nombre: " + pokemon.getName());

                if (pokemon.getTypes() != null && !pokemon.getTypes().isEmpty()) {
                    textType.setText("Tipo: " + pokemon.getTypes().get(0).getType().getName());
                } else {
                    textType.setText("Tipo: Desconocido");
                }

                textHeight.setText("Altura: " + pokemon.getHeight());
                textWeight.setText("Peso: " + pokemon.getWeight());
                textExperience.setText("Experiencia Base: " + pokemon.getBaseExperience());

                // Usa la libreria Glide para taraer la Imagen
                if (pokemon.getSprites() != null && pokemon.getSprites().getFrontDefault() != null) {
                    Glide.with(requireContext())
                            .load(pokemon.getSprites().getFrontDefault())
                            .into(imagePokemon);
                }
            }
        });
    }


    //Metodo del Boton que guarda en Favoritos al Pokemon
    private void configurarAcciones() {
        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> guardarEnFavoritos());
        }
    }

    //Metodo se encarga de guardar el nombre del pokemon en la memoria interna
    private void guardarEnFavoritos() {

        //guardar datos simples de forma permanente en el dispositivo, utilizando un sistema de clave-valor
        SharedPreferences prefs = requireActivity().getSharedPreferences("PokemonPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(pokemonName, true);
        editor.apply();

        Toast.makeText(requireContext(), pokemonName + " guardado en Favoritos", Toast.LENGTH_SHORT).show();
    }

    private void cargarDetallePokemon() {
        viewModel.fetchPokemonDetail(pokemonName);
    }

    //Inicializa componentes
    private void initViews(View view) {
        textName = view.findViewById(R.id.textName);
        textType = view.findViewById(R.id.textType);
        textHeight = view.findViewById(R.id.textHeight);
        textWeight = view.findViewById(R.id.textWeight);
        textExperience = view.findViewById(R.id.textExperience);
        imagePokemon = view.findViewById(R.id.imagePokemon);
        btnFavorite = view.findViewById(R.id.btnFavorite);
    }

}