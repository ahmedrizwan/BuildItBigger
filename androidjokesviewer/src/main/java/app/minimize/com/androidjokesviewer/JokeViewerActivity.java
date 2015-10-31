package app.minimize.com.androidjokesviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeViewerActivity extends AppCompatActivity {

    public static final String JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_viewer);
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String joke = extras.getString(JOKE, "No Joke Found!");
            ((TextView) findViewById(R.id.textViewJoke)).setText(joke);
        } else {
            ((TextView) findViewById(R.id.textViewJoke)).setText("No Joke Found!");
        }
    }
}
