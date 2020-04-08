package com.examplefg.topquiz2.Controller;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.examplefg.topquiz2.Model.*;
        import com.examplefg.topquiz2.R;


public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mUsersName;
    private Button mPlayBtn;
    private User mUser;

    public static final int GAME_ACTIVITY_REQUEST_CODE = 1;
    public static final String PREFERENCE_FIRSTNAME = "firstname";
    public static final String PREFERENCE_SCORE = "score";
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences("preferences",MODE_PRIVATE);

        mGreetingText = (TextView)findViewById(R.id.activity_main_greeting_text);
        mUsersName = (EditText)findViewById(R.id.activity_main_name_input);
        mPlayBtn = (Button)findViewById(R.id.activity_main_play_btn);

        //getting the preferences from the file
        String firstName = mPreferences.getString(PREFERENCE_FIRSTNAME,null);

        mUser = new User();

        if(firstName!=null)
        {
            int score = mPreferences.getInt(PREFERENCE_SCORE,0);
            mGreetingText.setText("Hello! "+firstName+"Nice to see you again\nLast time you hit a score of " +
                    score+" Do you want to try again ?");
            mUsersName.setText(firstName);
            mUsersName.setSelection(mUsersName.getText().length());
        }
        else
        {
            //desactivation du boutton
            mPlayBtn.setEnabled(false);
        }

        //verifie est controle les modification sur le EditText
        mUsersName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //s'active a chaque modification du texte
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //activation du boutton play si au mois un char est saisi
                mPlayBtn.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        //pour verifier si l'user a cliqué sur le bouton
        mPlayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View V)
            { //se lance quand l'utilisateur clique sur le boutton
                //enregistrer le prenom du joueur
                mUser.setFirstName(mUsersName.getText().toString());

                //inisialisation de l'intent et lancement de la prochaine activité
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity,GAME_ACTIVITY_REQUEST_CODE);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int score=0;
        if(requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
            score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);

        mPreferences = getSharedPreferences("preferences",MODE_PRIVATE);
        mPreferences.edit().putString(PREFERENCE_FIRSTNAME,mUser.getFirstName()).apply();
        mPreferences.edit().putInt(PREFERENCE_SCORE,score).apply();

        finish();
        startActivity(getIntent());
    }
}
