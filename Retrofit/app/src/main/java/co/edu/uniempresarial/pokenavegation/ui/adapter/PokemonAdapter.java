package co.edu.uniempresarial.pokenavegation.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import  co.edu.uniempresarial.pokenavegation.R;
import  co.edu.uniempresarial.pokenavegation.model.Pokemon;

import  java.util.ArrayList;
import  java.util.List;

public class PokemonAdapter
        extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    //Define un contrato o mecanismo de comunicación, indica donde el usuario hizo click
    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }


    //Variables y Constructor del Adaptador
    private final List<Pokemon> pokemonList = new ArrayList<>();
    private final OnPokemonClickListener listener;
    public PokemonAdapter(OnPokemonClickListener listener) {
        this.listener = listener;
    }

   //Metodo para actualizar los datos, limpia la lista vieja e ingresa los nuevos
    public void actualizarDatos(List<Pokemon> nuevosPokemon) {
        pokemonList.clear();
        if (nuevosPokemon != null) {
            pokemonList.addAll(nuevosPokemon);
        }
        notifyDataSetChanged();//Ordenarle al RecyclerView que redibuje la pantalla y muestre la información actualizada.
    }


    @NonNull
    @Override
    //devuelve una tarjeta ya armada con sus vistas adentro
    public PokemonViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())//traducir un archivo de diseño visual escrito en XML a objeto visual real de Java que el celular pueda renderizar.
                .inflate(R.layout.item_pokemon, parent, false);//Convertir el XML en vista rea
        return new PokemonViewHolder(view);
    }


    //Toma un elemento específico de la lista según su posición (position) y llama al metodo bind de la tarjeta
    // para rellenar sus textos con los datos de ese Pokemon en particular.
    @Override
    public void onBindViewHolder(
            @NonNull PokemonViewHolder holder,
            int position
    ) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
    }

    //Le informa al RecyclerView cuántos elementos hay en total en la lista para que calcule el espacio, el tamaño del
    // scroll y sepa cuántas veces debe reciclar las vistas.
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    //Vincula los componentes
    class PokemonViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPokemonName;
        private final TextView tvPokemonUrl;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
            tvPokemonUrl = itemView.findViewById(R.id.tvPokemonUrl);
        }

        //Asigna el nombre y la URL del objeto Pokemon actual a los textos de la tarjeta.
        public void bind(Pokemon pokemon) {
            tvPokemonName.setText(pokemon.getName());
            tvPokemonUrl.setText(pokemon.getUrl());

            //activa el listener que avisa al Fragment qué Pokémon exacto fue seleccionado.
            itemView.setOnClickListener(
                    view -> listener.onPokemonClick(pokemon)
            );
        }
    }
}
