package practica.moviles.uva.alfian.yamba;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class StatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //verificamos si la actividad ha sido creada

        if (savedInstanceState == null) {
            //creamos el fragment
            StatusFragment fragment = new StatusFragment();
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment, fragment.getClass().getSimpleName()).commit();
        }
    }
}
