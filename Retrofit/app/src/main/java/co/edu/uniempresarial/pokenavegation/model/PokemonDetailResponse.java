package co.edu.uniempresarial.pokenavegation.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// Clase principal que mapea la respuesta detallada de un Pokémon desde la API
public class PokemonDetailResponse {

    private String name;
    private int height;
    private int weight;

    // Vincula la propiedad "base_experience" del JSON con esta variable en Java
    @SerializedName("base_experience")
    private int baseExperience;

    private Sprites sprites;       // Objeto que contiene las imágenes del Pokémon
    private List<TypeSlot> types;  // Lista con los tipos a los que pertenece el Pokémon

    // Métodos Getters para obtener los valores desde otras clases
    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<TypeSlot> getTypes() {
        return types;
    }

    // Clase interna Para objetos anidados del JSON

    // Clase interna para manejar las imágenes (sprites)
    public static class Sprites {

        // Mapea la URL de la imagen frontal por defecto del JSON
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }

    // Clase interna que representa el contenedor de cada tipo en la lista
    public static class TypeSlot {
        private TypeInfo type;

        public TypeInfo getType() {
            return type;
        }
    }

    // Clase interna que guarda la información detallada del tipo de elemento del pokemon
    public static class TypeInfo {
        private String name;

        public String getName() {
            return name;
        }
    }
}