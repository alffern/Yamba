package practica.moviles.uva.alfian.yamba;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class StatusActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = "StatusActivity";
    EditText editStatus;
    Button tweet;
    private Context Contexto;
    TextView textCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Contexto = this;

        //enlazando views
        editStatus = (EditText) findViewById(R.id.editStatus);
        tweet = (Button) findViewById(R.id.buttonTweet);
        tweet.setOnClickListener(this);
        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GREEN);
        editStatus.addTextChangedListener(this);

    }


    //funcion llamada cuando se pulsa el boton
    public void onClick(View v){
        String status = editStatus.getText().toString();
        Log.d(TAG, "onClicked");

        PostTask a = (PostTask) new PostTask().execute(status);

            Toast.makeText(this, "Tweet enviado satisfactoriamente", Toast.LENGTH_LONG).show();

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
}
