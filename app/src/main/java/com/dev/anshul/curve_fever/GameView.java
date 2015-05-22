package com.dev.anshul.curve_fever;

/**
 * Created by anshul on 12/5/15.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView {

    private SurfaceHolder mHolder;
    private GameThread mThread = null;

    private int mScreenWidth;
    private int mScreenHeight;

    private boolean is_game_started;
    private boolean is_game_paused;
    private Head mHead = new Head();
    Path mtrailPath=new Path();

    public GameView(Context context){
        super(context);

        this.mScreenWidth = GameActivity.mScreenSize.x;
        this.mScreenHeight= GameActivity.mScreenSize.y;

        this.mThread = new GameThread(this);
        this.mHolder = this.getHolder();
        this.is_game_paused = false;

        this.mHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder mHolder)
            {
                mThread.setRunning(true);
                mThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder mHolder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder mHolder)
            {
                mThread.setRunning(false);
                boolean retry = true;

                while(retry)
                {
                    try {
                        mThread.join();
                        retry = false;
                    } catch (InterruptedException e) {}
                }
            }
        });

    }

    public void update()
    {
        //Do something here.

    }

    public void draw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle((float)mHead.headX, (float)mHead.headY, (float)mHead.headRadius, mHead.headPaint);
        canvas.drawPath (mtrailPath, mHead.headPaint);

        mtrailPath.lineTo((float) mHead.headX, (float) mHead.headY);
        mtrailPath.setLastPoint((float) mHead.headX, (float) mHead.headY);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        is_game_started = true;

        if(!is_game_paused)
        {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    //Do Something here.
                    mHead.followFinger((int)event.getX(),(int)event.getY());
                    break;

                case MotionEvent.ACTION_UP:
                    //Do Something here.
                    mHead.followFinger((int)event.getX(),(int)event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    //Do Something here.
                    mHead.followFinger((int)event.getX(),(int)event.getY());
                    break;
            }
        }
        return true;
    }
}
