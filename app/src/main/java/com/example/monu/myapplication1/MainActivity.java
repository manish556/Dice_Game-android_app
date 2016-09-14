package com.example.monu.myapplication1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int userTotalScore;
    private int userTurnScore;
    private int computerTurnScore;
    private int computerTotalScore;

    private Button rollButton,holdButton,resetButton;
    private ImageView diceImage;
    private TextView scoreView;

    Random random;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();

        resetScore();

        scoreView = (TextView) findViewById(R.id.textView2);
        rollButton = (Button) findViewById(R.id.button1);
        holdButton = (Button) findViewById(R.id.button2);
        resetButton = (Button) findViewById(R.id.button3);
        diceImage = (ImageView) findViewById(R.id.imageView1);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int save = rollDice();
                if(save==1)
                    userTurnScore = 0;
                else
                    userTurnScore+=save;
                updateScore();
                if(save==1)
                    computerTurn();
            }

        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTotalScore+=userTurnScore;
                userTurnScore = 0;
                updateScore();
                computerTurn();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetScore();
                updateScore();
            }
        });

    }

    private int rollDice()
    {
        int facevalue = random.nextInt(6)+1;
        int imageID;
        switch (facevalue) {
            case 1:
                imageID = R.drawable.dice1;
                break;
            case 2:
                imageID = R.drawable.dice2;
                break;
            case 3:
                imageID = R.drawable.dice3;
                break;
            case 4:
                imageID = R.drawable.dice4;
                break;
            case 5:
                imageID = R.drawable.dice5;
                break;
            case 6:
                imageID = R.drawable.dice6;
                break;
            default:
                imageID = R.drawable.dice1;
                break;
        }
        diceImage.setImageDrawable(getResources().getDrawable(imageID));
        return facevalue;
    }

    private void updateScore(){
        StringBuffer dispStr = new StringBuffer();
        dispStr.append("Your total score: ");
        dispStr.append(userTotalScore);
        dispStr.append(" Your turn score:");
        dispStr.append(userTurnScore);
        dispStr.append(" Computer total score: ");
        dispStr.append(computerTotalScore);
        dispStr.append(" Computer turn score: ");
        dispStr.append(computerTurnScore);
        scoreView.setText(dispStr);
    }

    private void resetScore(){
        userTurnScore=userTotalScore=computerTurnScore=computerTotalScore=0;
    }

    private void enableAllButtons(){
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    private void disableAllButtons(){
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    private void computerTurn(){
        disableAllButtons();
        computerTurnScore=0;
        timerHandler.postDelayed(computerTurnRunnable,0);

    }

    private void computerSingleTurn() {
        int score=rollDice();
        if (score==1)
            computerTurnScore = 0;
        else
            computerTurnScore+=score;
        updateScore();
    }

  Handler timerHandler = new Handler();
    Runnable computerTurnRunnable = new Runnable() {
        @Override
        public void run() {
                computerSingleTurn();
               if(computerTurnScore<20 && computerTurnScore!=0)
               {
                   timerHandler.postDelayed(this,1000);
               }
               else
               {
                   computerTotalScore+=computerTurnScore;
                   updateScore();
                   enableAllButtons();
               }
        }
    };

}
