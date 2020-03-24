package com.example.figures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyView extends View {
    View myView;
    Paint paint = new Paint();
    int[][] tiles = new int[4][4];
    int darkColor = Color.DKGRAY;
    int brightColor = Color.MAGENTA;

    float width ;
    float height;
    float space;
    float cellWidth;
    float cellHeight;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int randomColor;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                randomColor = (int) (Math.random()*2);
                if (randomColor == 0) {
                    tiles[i][j] = darkColor;
                }
                else {
                    tiles[i][j] = brightColor;
                }
            }

        }
    }

     @Override
     protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        space = width/50;
        cellWidth = (width - space)/4;
        cellHeight = ((height - space)/4);

        float x1 = 0;
        float y1 = 0;
        float x2 = cellWidth;
        float y2 = cellHeight;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                paint.setColor(tiles[i][j]);
                canvas.drawRect(x1, y1, x2, y2, paint);
                x1 = x2 + space/3;
                x2 = x1 + cellWidth;
            }
            x1 = 0;
            x2 = cellWidth;
            y1 = y2 + space/3;
            y2 = y1 + cellHeight;
        }
     }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            /*int x = (int) (event.getX());
            int y = (int) (event.getY());*/

            int yTiles = (int) (event.getX() / cellWidth);
            int xTiles = (int) (event.getY() / cellHeight);
            changeColor(xTiles, yTiles);

            for (int i = 0; i < tiles.length; i++) {
                changeColor(i, yTiles);
                changeColor(xTiles, i);
            }

            if (itIsWin() == true){
                myView = findViewById(R.id.myView);
                Toast toast = Toast.makeText(myView.getContext(), "Вы выиграли!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        invalidate();
        return true;
    }

    public boolean itIsWin (){ //проверка выиграли ли мы
        int countOfDark = 0;
        int countOfBright = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length-1; j++) {
                if (tiles[i][j] == darkColor){
                    countOfDark++;
                }
                else {
                    countOfBright++;
                }
            }
        }
        if ((countOfDark == tiles.length*tiles[0].length) || (countOfBright == tiles.length*tiles[0].length)){
            return true;
        }
        return false;
    }

    public  void changeColor (int x, int y){
        if (tiles[x][y] == darkColor) {
            tiles[x][y] = brightColor;
        } else {
            tiles[x][y] = darkColor;
        }
    }


}


