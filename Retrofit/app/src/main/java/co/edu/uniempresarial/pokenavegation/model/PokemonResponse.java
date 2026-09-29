package co.edu.uniempresarial.pokenavegation.model;

import java.util.List;

public class PokemonResponse {

    private  int count;
    private String next;
    private String previous;
    private List<Pokemon> results;

    public int getCount() {
        return count;
    }

    public List<Pokemon> getResults() {
        return results;
    }
}
