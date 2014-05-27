package me.planetguy.robots.renderworld;

import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class ViewWorld extends SurfaceView implements SurfaceHolder.Callback{

	static final Paint paint=new Paint();
	private Thread mDrawingThread;
	SurfaceHolder surfaceHolder;

	private Thread timer;


	GestureDetector detector;
	ScaleGestureDetector scaleDetector;

	float scale=1.0f;

	public ViewWorld(Context context) {
		super(context);
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);
		paint.setAntiAlias(false);
	}

	public void drawTerrain(Canvas canvas){
		Robots.world.tryTick(this);
		canvas.save();
		canvas.scale(scale, scale);
		int tileSize=Robots.iconSize;
		for(int x=0; x<Robots.world.tiles.length; x++){
			for(int y=0; y<Robots.world.tiles[0].length; y++){
//				if(Robots.world.visible[x][y])
					canvas.drawBitmap(Robots.world.tiles[x][y].bitmapIcon, x*tileSize,y*tileSize, paint);
			}
		}
		for(DynamicObject r:Robots.world.robots){
			canvas.drawBitmap(r.getIcon(), r.x*tileSize, r.y*tileSize, paint);
		}
		canvas.restore();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mDrawingThread = new Thread(new Runnable() {
			public void run() {
				Canvas canvas = null;
				while (!Thread.currentThread().isInterrupted()) {
					canvas = surfaceHolder.lockCanvas();
					if (null != canvas) {
						drawTerrain(canvas);
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		});
		mDrawingThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (null != mDrawingThread)
			mDrawingThread.interrupt();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//scaleDetector.onTouchEvent(ev);
		int x=(int) (ev.getX());
		int y=(int) (ev.getY());
		int xIdx=(int) (x/(Robots.iconSize*scale));
		int yIdx=(int) (y/(Robots.iconSize*scale));
		if(0<=xIdx&&xIdx<Robots.world.tiles.length&&0<yIdx&&yIdx<Robots.world.tiles[0].length) //if in bounds
			return Robots.world.handleClick(this, xIdx, yIdx);
		return false;
	}

}
