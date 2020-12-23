package course.labs.graphicslab;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class BubbleActivity extends Activity {

	// These variables are for testing purposes, do not modify
	private final static int RANDOM = 0;
	private final static int SINGLE = 1;
	private final static int STILL = 2;
	private static int speedMode = RANDOM;

	private static final String TAG = "Lab-Graphics";
    private static final String DEBUG = "DEBUG";

	// The Main view
	private RelativeLayout mFrame;

	// Bubble image's bitmap
	private Bitmap mBitmap;

	// Display dimensions
	private int mDisplayWidth, mDisplayHeight;

	// Sound variables

	// AudioManager
	private AudioManager mAudioManager;
	// SoundPool
	private SoundPool mSoundPool;
	// ID for the bubble popping sound
	private int mSoundID;
	// Audio volume
	private float mStreamVolume;

	// Gesture Detector
	private GestureDetector mGestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		// Set up user interface
		mFrame = (RelativeLayout) findViewById(R.id.frame);

		// Load basic bubble Bitmap
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b64);

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Manage bubble popping sound
		// Use AudioManager.STREAM_MUSIC as stream type

		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

		mStreamVolume = (float) mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)/mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		// TODO - make a new SoundPool, allowing up to 10 streams
//		mSoundPool = null;
//		mSoundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,0);

        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        Log.i(DEBUG, "mSoundPool");

		// TODO - set a SoundPool OnLoadCompletedListener that calls setupGestureDetector()
//		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//			@Override
//			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//				if (0==status){
//					setupGestureDetector();
//				}
//			}
//		});

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.i(DEBUG,"Pre setupGestureDetector()");
                setupGestureDetector();
            }
        });

		// TODO - load the sound from res/raw/bubble_pop.wav
//		mSoundID = 0;
		mSoundID = mSoundPool.load(this,R.raw.bubble_pop,1);
        Log.i(DEBUG,"mSoundPool loaded");
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {

			// Get the size of the display so this View knows where borders are
			mDisplayWidth = mFrame.getWidth();
			mDisplayHeight = mFrame.getHeight();

		}
	}

	// Set up GestureDetector
	private void setupGestureDetector() {

		mGestureDetector = new GestureDetector(this,

				new GestureDetector.SimpleOnGestureListener() {

					// If a fling gesture starts on a BubbleView then change the
					// BubbleView's velocity

					@Override
					public boolean onFling(MotionEvent event1, MotionEvent event2,
										   float velocityX, float velocityY) {

						// TODO - Implement onFling actions.
						// You can get all Views in mFrame using the
						// ViewGroup.getChildCount() method

//						for (int i=0;i<mFrame.getChildCount();i++){
//							if (mFrame.getChildAt(i).getX()<event1.getRawX()
//									&& event1.getRawX()<mFrame.getChildAt(i).getWidth()
//									&& mFrame.getChildAt(i).getY() < event1.getRawY()
//									&& event1.getRawY()<mFrame.getChildAt(i).getWidth()	){
//								if(velocityX < -10.0f){
//									speedMode=1;
//								}
//							}
//						}

                        Log.i(DEBUG, "In onFling()");
                        for(int i=0; i < mFrame.getChildCount(); i++) {
                            BubbleView bubble = (BubbleView) mFrame.getChildAt(i);
                            if(bubble.intersects(event1.getRawX(), event1.getRawY())){
                                bubble.deflect(velocityX,velocityY);
                                Log.i(DEBUG, "Touch intersects with a Bubble");
                            }
                        }
						return false;
					}

					// If a single tap intersects a BubbleView, then pop the BubbleView
					// Otherwise, create a new BubbleView at the tap's location and add
					// it to mFrame. You can get all views from mFrame with ViewGroup.getChildAt()

					@Override
					public boolean onSingleTapConfirmed(MotionEvent event) {

						// TODO - Implement onSingleTapConfirmed actions.
						// You can get all Views in mFrame using the
						// ViewGroup.getChildCount() method

//						for (int i=0;i<mFrame.getChildCount();i++){
//							if (mFrame.getChildAt(i).getX()<event.getRawX()
//									&& event.getRawX()<mFrame.getChildAt(i).getWidth()+event.getRawX()
//									&& mFrame.getChildAt(i).getY() < event.getRawY()
//									&& event.getRawY()<mFrame.getChildAt(i).getWidth()+event.getRawY()	){
//								mFrame.removeView(mFrame.getChildAt(i));
//							}else{
//								BubbleView bubbleView = new BubbleView(getApplicationContext(),event.getX(),event.getY());
//								mFrame.addView(bubbleView);
//								bubbleView.start();
//							}
//						}

                        Log.i(DEBUG, "In onSingleTapConfirmed");
                        for(int i=0; i < mFrame.getChildCount(); i++) {
                            BubbleView bubble = (BubbleView) mFrame.getChildAt(i);
                            if(bubble.intersects(event.getX(), event.getY())){
                                Log.i(DEBUG, "Touch intersects with a bubble - will be popped");
                                //mFrame.removeViewAt(i);
                                bubble.stop(true);
                                return true;
                            }
                        }
                        BubbleView newBubble = new BubbleView(getApplicationContext(), event.getX(), event.getY());
                        mFrame.addView(newBubble);
                        newBubble.start();
                        Log.i(DEBUG, "Added bubble");

						return false;
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// TODO - Delegate the touch to the gestureDetector
		return  mGestureDetector.onTouchEvent(event);

//		return false;

	}

	@Override
	protected void onPause() {

		// TODO - Release all SoundPool resources
//		mSoundPool.release();

        if (null != mSoundPool) {
            mSoundPool.release();
            mSoundPool.unload(mSoundID);
            mSoundPool = null;
        }
        mAudioManager.setSpeakerphoneOn(false);
        mAudioManager.unloadSoundEffects();

		super.onPause();
	}

	// BubbleView is a View that displays a bubble.
	// This class handles animating, drawing, and popping amongst other actions.
	// A new BubbleView is created for each bubble on the display

	public class BubbleView extends View {

		private static final int BITMAP_SIZE = 64;
		private static final int REFRESH_RATE = 40;
		private final Paint mPainter = new Paint();
		private ScheduledFuture<?> mMoverFuture;
		private int mScaledBitmapWidth;
		private Bitmap mScaledBitmap;

		// location, speed and direction of the bubble
		private float mXPos, mYPos, mDx, mDy, mRadius, mRadiusSquared;
		private long mRotate, mDRotate;

		BubbleView(Context context, float x, float y) {
			super(context);

			// Create a new random number generator to
			// randomize size, rotation, speed and direction
			Random r = new Random();

			// Creates the bubble bitmap for this BubbleView
			createScaledBitmap(r);

			// Radius of the Bitmap
			mRadius = mScaledBitmapWidth / 2;
			mRadiusSquared = mRadius * mRadius;

			// Adjust position to center the bubble under user's finger
			mXPos = x - mRadius;
			mYPos = y - mRadius;

			// Set the BubbleView's speed and direction
			setSpeedAndDirection(r);

			// Set the BubbleView's rotation
			setRotation(r);

			mPainter.setAntiAlias(true);

		}

		private void setRotation(Random r) {

			if (speedMode == RANDOM) {

				// TODO - set rotation in range [1..3]
//				mDRotate = 0;
				mDRotate = r.nextInt(3)+1;

			} else {
				mDRotate = 0;

			}
		}

		private void setSpeedAndDirection(Random r) {

			// Used by test cases
			switch (speedMode) {

				case SINGLE:

					mDx = 20;
					mDy = 20;
					break;

				case STILL:

					// No speed
					mDx = 0;
					mDy = 0;
					break;

				default:

					// TODO - Set movement direction and speed
					// Limit movement speed in the x and y
					// direction to [-3..3] pixels per movement.
					mDx = r.nextInt(7)-3;
					mDy = r.nextInt(7)-3;
					break;

			}
		}

		private void createScaledBitmap(Random r) {

			if (speedMode != RANDOM) {
				mScaledBitmapWidth = BITMAP_SIZE * 3;

			} else {
				//TODO - set scaled bitmap size in range [1..3] * BITMAP_SIZE
//				mScaledBitmapWidth = 0;
				mScaledBitmapWidth =  BITMAP_SIZE * (r.nextInt(3)+1);
			}

			// TODO - create the scaled bitmap using size set above
//			mScaledBitmap = null;
//			mScaledBitmap.createBitmap(mBitmap,(int)mXPos,(int)mYPos,mScaledBitmapWidth,mScaledBitmapWidth);
            mScaledBitmap = Bitmap.createScaledBitmap(mBitmap, mScaledBitmapWidth, mScaledBitmapWidth, true);
		}

		// Start moving the BubbleView & updating the display
		private void start() {

			// Creates a WorkerThread
			ScheduledExecutorService executor = Executors
					.newScheduledThreadPool(1);

			// Execute the run() in Worker Thread every REFRESH_RATE
			// milliseconds
			// Save reference to this job in mMoverFuture
			mMoverFuture = executor.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {

					// TODO - implement movement logic.
					// Each time this method is run the BubbleView should
					// move one step. If the BubbleView exits the display,
					// stop the BubbleView's Worker Thread.
					// Otherwise, request that the BubbleView be redrawn.

//					if (isOutOfView()){
//						stop(true);
//					}
//					moveWhileOnScreen();//移动
//					invalidate();
//					intersects(0,0);//判断是否与某点相交
//					deflect(1,1);//变速、变方向

                    if (!moveWhileOnScreen()) {
                        //Log.d(DEBUG,"BubbleView start: Bolla da ridisegnare");
                        //BubbleView.this.postInvalidate();
                        postInvalidate();
                    } else {
                        Log.d(DEBUG,"BubbleView left screen");
                        BubbleView.this.stop(false);
                    }

				}
			}, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
		}

		// Returns true if the BubbleView intersects position (x,y)
		private synchronized boolean intersects(float x, float y) {

			// TODO - Return true if the BubbleView intersects position (x,y)
//			if (x>mXPos
//					&&x<mScaledBitmapWidth+mXPos
//					&&y>mYPos
//					&&y<mScaledBitmapWidth+mYPos){
//				return true;
//			}
//			return false;

            //Math.abs(n)：对int、long、float、double类型的数取绝对值
            //这个泡泡本身+左上+上+左，皆可破
//            if(Math.abs(BubbleView.this.mXPos - x) <= mScaledBitmapWidth && Math.abs(BubbleView.this.mYPos - y) <= mScaledBitmapWidth) {
//                Log.i(DEBUG, "BubbleView x: " + BubbleView.this.mXPos);
//                Log.i(DEBUG, "Touch x: " + x);
//                Log.i(DEBUG, "BubbleView x - x: " + (BubbleView.this.mXPos - x));
//                return true;
//            } else {
//                return false;
//            }

            //这个只能，泡泡本身破
            if((x - BubbleView.this.mXPos) <= mScaledBitmapWidth
                    && (y - BubbleView.this.mYPos) <= mScaledBitmapWidth
                    && (BubbleView.this.mXPos + mScaledBitmapWidth - x) <= mScaledBitmapWidth
                    && (BubbleView.this.mYPos + mScaledBitmapWidth - y) <= mScaledBitmapWidth
            ) {
                                Log.i(DEBUG, "BubbleView x: " + BubbleView.this.mXPos);
                Log.i(DEBUG, "Touch x: " + x);
                Log.i(DEBUG, "BubbleView x - x: " + (x - BubbleView.this.mXPos));
                return true;
            } else {
                return false;
            }

		}

		// Cancel the Bubble's movement
		// Remove Bubble from mFrame
		// Play pop sound if the BubbleView was popped

		private void stop(final boolean wasPopped) {

			if (null != mMoverFuture && !mMoverFuture.isDone()) {
				mMoverFuture.cancel(true);
			}

			// This work will be performed on the UI Thread
			mFrame.post(new Runnable() {
				@Override
				public void run() {

					// TODO - Remove the BubbleView from mFrame
//					mFrame.removeView(getCurrentFocus());
                    mFrame.removeView(BubbleView.this);

					// TODO - If the bubble was popped by user,
					// play the popping sound
					if (wasPopped) {
                        Log.i(DEBUG,"Pop!");
                        mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
					}

                    Log.i(DEBUG,"Bubble removed from view!");
				}
			});
		}

		// Change the Bubble's speed and direction
		private synchronized void deflect(float velocityX, float velocityY) {

			//TODO - set mDx and mDy to be the new velocities divided by the REFRESH_RATE

//			mDx = velocityX;
//			mDy = velocityY;

            Log.i(DEBUG,"velocity X:" + velocityX + " velocity Y:" + velocityY);

            mDx = velocityX / REFRESH_RATE;
            mDy = velocityY / REFRESH_RATE;

		}

		// Draw the Bubble at its current location
		@Override
		protected synchronized void onDraw(Canvas canvas) {

//			super.onDraw(canvas);

//			mPainter.setColor(0xFFFF6600);
//			mPainter.setStyle(Paint.Style.FILL);
//			canvas.drawRect(10,10,15,280,mPainter);


			// TODO - save the canvas
			canvas.save();

			// TODO - increase the rotation of the original image by mDRotate
			mRotate += mDRotate;

			// TODO Rotate the canvas by current rotation
			// Hint - Rotate around the bubble's center, not its position
//			canvas.rotate(mRotate);
            canvas.rotate(mRotate, mXPos + mScaledBitmapWidth / 2, mYPos+ mScaledBitmapWidth / 2);

			// TODO - draw the bitmap at its new location
			canvas.drawBitmap(mScaledBitmap,mXPos,mYPos,mPainter);

			// TODO - restore the canvas
			canvas.restore();


		}

		// Returns true if the BubbleView is still on the screen after the move
		// operation
		private synchronized boolean moveWhileOnScreen() {

			// TODO - Move the BubbleView
//			mXPos += mDx;
//			mYPos += mDy;
//			if (mXPos+mScaledBitmapWidth>mDisplayWidth
//					||mXPos<mDisplayWidth
//					||mYPos+mScaledBitmapWidth>mDisplayHeight
//					||mYPos<mDisplayWidth){
//				return true;
//			}
//			return false;

            mXPos += mDx;
            mYPos += mDy;
            return isOutOfView();
		}

		// Return true if the BubbleView is off the screen after the move
		// operation
		private boolean isOutOfView() {

			// TODO - Return true if the BubbleView is off the screen after
			// the move operation
//			if (mXPos+mScaledBitmapWidth<mDisplayWidth
//					||mXPos>mDisplayWidth
//					||mYPos+mScaledBitmapWidth<mDisplayHeight
//					||mYPos>mDisplayWidth){
//				return true;
//			}
//			return false;

            if(this.mXPos + mScaledBitmapWidth/2 < 0
                    || this.mXPos > mDisplayWidth
                    || this.mYPos + mScaledBitmapWidth/2 < 0
                    || this.mYPos > mDisplayHeight) {
                return true;
            } else {
                return false;
            }
		}
	}

	// Do not modify below here

	@Override
	public void onBackPressed() {
		openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		getMenuInflater().inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_still_mode:
				speedMode = STILL;
				return true;
			case R.id.menu_single_speed:
				speedMode = SINGLE;
				return true;
			case R.id.menu_random_mode:
				speedMode = RANDOM;
				return true;
			case R.id.quit:
				exitRequested();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void exitRequested() {
		super.onBackPressed();
	}
}