package co.edu.uniempresarial.pokenavegation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.edu.uniempresarial.pokenavegation.ui.FavoritesFragment;
import co.edu.uniempresarial.pokenavegation.ui.HomeFragment;
import co.edu.uniempresarial.pokenavegation.ui.InfoFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

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

        // Inicializamos los objetos (encontrar vistas por ID)
        initObjetc();

        // Configuramos losclics del menú inferior
        configurarBottonNavegacion();

        // Cargamos el HomeFragment por defecto al abrir la aplicación por primera vez
        if (savedInstanceState == null) {
            cargarFragment(new HomeFragment());
        }
    }

    // Metodo de configuración del Bottom Navigation
    private void configurarBottonNavegacion(){
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = obtenerFragment(item.getItemId());
            if (fragment == null) {
                return false;
            }
            cargarFragment(fragment);
            return true;
        });
    }

    // Metodo de carga del Fragment
    private void cargarFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    // Metodo que recibe el ID del ítem seleccionado en el menú
    private Fragment obtenerFragment(int itemId){
        if(itemId == R.id.navigation_home){
            return new HomeFragment();
        }
        if (itemId == R.id.navigation_favorites){
            return new FavoritesFragment();
        }
        if (itemId == R.id.navegation_info){
            return new InfoFragment();
        }
        return null;
    }

    // Inicialización de componentes gráficos
    private void initObjetc(){
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }
}