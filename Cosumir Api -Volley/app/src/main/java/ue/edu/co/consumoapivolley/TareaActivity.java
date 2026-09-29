package ue.edu.co.consumoapivolley;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ue.edu.co.consumoapivolley.poo.Post;

public class TareaActivity extends AppCompatActivity {

    private static final String URL_API = "https://jsonplaceholder.typicode.com/posts";
    private static final String REQUEST_TAG = "GET_POSTS";

    private ListView lvPosts;
    private TextView tvEstadoTarea;
    private ProgressBar progressBarTarea;
    private RequestQueue requestQueue;

    // Listas Una para los objetos y otra para pintar los textos en el ListView
    private final List<Post> listaPosts = new ArrayList<>();
    private final List<String> listaTextos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarea);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicializar();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        consumirApiPosts();

    }

    private void consumirApiPosts() {
        mostrarCargando(true);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL_API,
                null,
                response -> {
                    listaPosts.clear();
                    listaTextos.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject item = response.getJSONObject(i);

                            int userId = item.getInt("userId");
                            int id = item.getInt("id");
                            String title = item.getString("title");
                            String body = item.getString("body");

                            // Aplicando POO: Instanciamos el objeto Post con los datos obtenidos
                            Post post = new Post(userId, id, title, body);
                            listaPosts.add(post);

                            // Mostramos los 4 campos requeridos: userId, id, title y body
                            String texto = "User ID: " + post.getUserId()
                                    + "\nID: " + post.getId()
                                    + "\nTítulo: " + post.getTitle()
                                    + "\nContenido: " + post.getBody();
                            listaTextos.add(texto);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_list_item_1,
                                listaTextos
                        );
                        lvPosts.setAdapter(adapter);
                        mostrarCargando(false);

                        // Mostrar el contador con el total de registros recibidos
                        int totalRegistros = listaPosts.size();
                        tvEstadoTarea.setText("Registros recibidos: " + totalRegistros);
                        tvEstadoTarea.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        mostrarError("No fue posible procesar la respuesta.");
                    }
                },
                this::procesarError
        );

        request.setTag(REQUEST_TAG);
        request.setShouldCache(false); // Evitamos caché para validar errores de red
        requestQueue.add(request);
    }

    private void mostrarError(String mensaje) {
        mostrarCargando(false);
        tvEstadoTarea.setText(mensaje);
        tvEstadoTarea.setVisibility(View.VISIBLE);
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void procesarError(VolleyError error) {
        String mensaje = error.getMessage();
        if (mensaje == null || mensaje.isBlank()) {
            mensaje = "Verifique la conexión a Internet.";
        }
        mostrarError("Error en la solicitud: " + mensaje);
    }

    private void mostrarCargando(boolean cargando) {
        progressBarTarea.setVisibility(cargando ? View.VISIBLE : View.GONE);
        lvPosts.setVisibility(cargando ? View.GONE : View.VISIBLE);
    }

    private void inicializar() {
        lvPosts = findViewById(R.id.lvPost);
        progressBarTarea = findViewById(R.id.pbPost);
        tvEstadoTarea = findViewById(R.id.tvEstadoPost);
    }
}
