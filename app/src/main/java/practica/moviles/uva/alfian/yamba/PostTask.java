package practica.moviles.uva.alfian.yamba;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
/**
 * Created by alfre on 12/10/2017.
 */

final class PostTask extends AsyncTask<String, Void, String> {
    Twitter twitter;
    public TwitterException e;
    private static final String TAG = "StatusActivity";

    @Override
    protected String doInBackground(String... params) {
        try{
            ConfigurationBuilder builder = new ConfigurationBuilder();

            builder.setDebugEnabled(true)
                .setOAuthConsumerKey("W4oId8Hl2fS7TE1lpbcPjoqC3")
                .setOAuthConsumerSecret("y0mdidyHzDJOlmY5mcVJuFryGk920yLDShbaL9G2vkTYdAWH2w")
                .setOAuthAccessToken("916397559488192512-E1nPmgr6J7MmW1iisA9iXFbFxqQP55q")
                .setOAuthAccessTokenSecret("tZZcEdwRjSHmYdb8S0beXxqoXSaE0lm9Ewp0McdfJ5K9Y");

            TwitterFactory factory = new TwitterFactory(builder.build());
            twitter = factory.getInstance();
            twitter.updateStatus(params[0]);
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
       // Toast.makeText("Tweet enviado satisfactoriamente", StatusActivity.this, Toast.LENGTH_LONG).show();
    }

    public TwitterException getE() {
        return e;
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
