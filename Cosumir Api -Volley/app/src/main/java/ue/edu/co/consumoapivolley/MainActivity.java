package ue.edu.co.consumoapivolley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    private TextView tvTituloMenu;
    private Button btnEjercicio, btnTarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicializarVistas();
        configurarEventos();

    }

    private void configurarEventos() {
        // Botón que dirige al Ejercicio (/todos)
        btnEjercicio.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EjercicioActivity.class);
            startActivity(intent);
        });

        // Botón que dirige a la Tarea (/posts con POO)
        btnTarea.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TareaActivity.class);
            startActivity(intent);
        });
    }



    private void inicializarVistas() {
        tvTituloMenu = findViewById(R.id.tvTituloMenu);
        btnEjercicio = findViewById(R.id.btnEjercicio);
        btnTarea = findViewById(R.id.btnTarea);
    }

   }