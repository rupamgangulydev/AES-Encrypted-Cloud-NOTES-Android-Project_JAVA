package com.rupam.tictactoe;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    int activePlayer=0;
    int[] gameState={2,2,2,2,2,2,2,2,2};// Null -> 2   0-> X    1->0
    int[][] winningPositions={
            {0,1,2},{3,4,5},
            {6,7,8},{0,3,6},
            {1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };
    int count[]={0,0};//{x, o}
    int steps=0;
    boolean gameActive=true;
    String player1;
    String player2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String[] ar=intent.getStringArrayExtra(PlayerActivity.key);
        System.out.println("Intent recieved");
        player1=ar[0];
        player2=ar[1];
        TextView textView3= findViewById(R.id.textView3);
        textView3.setText(player1+"'s Turn ");
    }
    public void gameReset(View view){
        gameActive=true;
        activePlayer=0;
        int [] newgameState={2,2,2,2,2,2,2,2,2};
        gameState=newgameState;
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView9)).setImageResource(0);
        TextView textView3= findViewById(R.id.textView3);
        textView3.setText(player1+"'s Turn ");
    }
    public void playerTap(View view){
        ImageView img= (ImageView) view;
        int tappedCell=Integer.parseInt(img.getTag().toString());
        if(!gameActive || steps>8) {
            steps=0;
            gameReset(view);// reset when board is full or some one won..
        }

        if(gameState[tappedCell]==2){
            gameState[tappedCell]=activePlayer;
            img.setTranslationY(-1000f);
            if(activePlayer==0){
                img.setImageResource(R.drawable.o);
                activePlayer=1;
                TextView textView3= findViewById(R.id.textView3);
                String txt=player2+"'s Turn ";
                textView3.setText(txt);
                steps++;
            }
            else {
                img.setImageResource(R.drawable.x);
                activePlayer=0;
                TextView textView3= findViewById(R.id.textView3);
                String txt=player1+"'s Turn ";
                textView3.setText(txt);
                steps++;
            }
            img.animate().translationYBy(1000f).setDuration(100);
        }
        for(int[] winPos: winningPositions){
            if (gameState[winPos[0]] == gameState[winPos[1]] && gameState[winPos[1]] == gameState[winPos[2]] && gameState[winPos[0]]!=2) {
                String winner="";
                gameActive=false;
                if(gameState[winPos[0]]==0){
                    count[0]=count[0]+1;
                    winner=player1+" has WON"+"  POINTS:-> "+player1+": "+count[0]+"; "+player2+": "+count[1];
                }
                else{
                    count[1]=count[1]+1;
                    winner=player2+" has WON"+"  POINTS:-> "+player1+": "+count[0]+"; "+player2+": "+count[1];
                }
                TextView textView3= findViewById(R.id.textView3);
                textView3.setText(winner);
            }
        }
    }
}