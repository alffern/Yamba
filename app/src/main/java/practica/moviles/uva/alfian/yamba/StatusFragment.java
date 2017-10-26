package practica.moviles.uva.alfian.yamba;



import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static android.support.design.widget.Snackbar.*;




public class StatusFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private static final String TAG = "StatusActivity";
    EditText editStatus;
    Button tweet;
    TextView textCount;
    ProgressBar PB;
    int progressStatus = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        //enlazando views
        editStatus = view.findViewById(R.id.editStatus);
        tweet = view.findViewById(R.id.buttonTweet);
        tweet.setOnClickListener(this);
        textCount = view.findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GREEN);
        editStatus.addTextChangedListener(this);
        PB = view.findViewById(R.id.progressBar);
        return view;
    }


    //funcion llamada cuando se pulsa el boton
    public void onClick(View v){
        String status = editStatus.getText().toString();
        Log.d(TAG, "onClicked");
        PB.setVisibility(View.VISIBLE);
        new PostTask().execute(status);
        //reinicia el campo de texto
        editStatus.setText("");

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable statusText) {
        int count = 140 - statusText.length();
        textCount.setText(Integer.toString(count));
        textCount.setTextColor(Color.GREEN);
        if (count < 10)
            textCount.setTextColor(Color.YELLOW);
        if (count < 0)
            textCount.setTextColor(Color.RED);
    }
    //subclase que realizará la tarea asincrona
    private final class PostTask extends AsyncTask<String, Integer, String> {
        Twitter twitter;
        public TwitterException e;
        private static final String TAG = "StatusActivity";


        @Override
        protected String doInBackground(String... params) {


            try{
                try {
                    // espera 200ms para ver la animacion y publica en el metodo onProgressUpdate la variable progressStatus
                    while (progressStatus < 100) {
                        Thread.sleep(200);
                        publishProgress(progressStatus);
                        progressStatus += 10;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ConfigurationBuilder builder = new ConfigurationBuilder();

                builder.setDebugEnabled(true)
                        .setOAuthConsumerKey("W4oId8Hl2fS7TE1lpbcPjoqC3")
                        .setOAuthConsumerSecret("y0mdidyHzDJOlmY5mcVJuFryGk920yLDShbaL9G2vkTYdAWH2w")
                        .setOAuthAccessToken("916397559488192512-E1nPmgr6J7MmW1iisA9iXFbFxqQP55q")
                        .setOAuthAccessTokenSecret("tZZcEdwRjSHmYdb8S0beXxqoXSaE0lm9Ewp0McdfJ5K9Y");

                TwitterFactory factory = new TwitterFactory(builder.build());
                twitter = factory.getInstance();
                twitter.updateStatus(params[0]);
                progressStatus=0;
                return "Tweet enviado correctamente";
            }catch (TwitterException d){
                this.e=d;
                Log.e(TAG, "Fallo en el envio");
                e.printStackTrace();
                return "Fallo en el envío del tweet";
            }
        }


        //llamada cuando la actividad en background ha terminado

        @Override
        protected void onPostExecute(String result) {
            //accion al completar la actualización de estado
            super.onPostExecute(result);
            PB.setVisibility(View.GONE);
            if(result.equals("Fallo en el envío del tweet"))
                //Toast.makeText(StatusFragment.this.getActivity(), "Tweet fallido", Toast.LENGTH_LONG).show();
                Snackbar.make(StatusFragment.this.getView(),"Error en el tweet: "+e, LENGTH_LONG).show();
            else
                //Con toast el primer argumento del makeText pide contexto!! con Snackbar make pide una vista!!
              //  Toast.makeText(StatusFragment.this.getActivity(), "Tweet enviado correctamente", Toast.LENGTH_LONG).show();
                Snackbar.make(StatusFragment.this.getView(),"Tweet enviado correctamente", LENGTH_LONG).show();
        }

        // metodo para actualizar la barra de progreso a medida que se realiza la tarea asincrona
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            PB.setProgress(values[0]);
        }

    }
}
