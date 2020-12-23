package course.labs.intentslab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ExplicitlyLoadedActivity extends Activity {

	static private final String TAG = "Lab-Intents";

	private EditText mEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.explicitly_loaded_activity);

		// Get a reference to the EditText field
		mEditText = (EditText) findViewById(R.id.editText);

		// Declare and setup "Enter" button
		Button enterButton = (Button) findViewById(R.id.enter_button);
		enterButton.setOnClickListener(new OnClickListener() {

			// Call enterClicked() when pressed

			@Override
			public void onClick(View v) {

				enterClicked();
			
			}
		});

	}

	// Sets result to send back to calling Activity and finishes
	
	private void enterClicked() {

		Log.i(TAG,"Entered enterClicked()");
		
		// TODO - Save user provided input from the EditText field
		Bundle data = new Bundle();
		data.putString("result",mEditText.getText().toString());
		// TODO - Create a new intent and save the input from the EditText field as an extra
		Intent intent = new Intent();
		intent.putExtras(data);		//putExtras()注意“s”
		// TODO - Set Activity's result with result code RESULT_OK
		setResult(RESULT_OK,intent);
		// TODO - Finish the Activity
		finish();

//		// 以下方法不可行
//		String string = mEditText.getText().toString();
//		Intent intent = new Intent(ExplicitlyLoadedActivity.this, ActivityLoaderActivity.class);
//		intent.putExtra("myInput", string);
//		setResult(RESULT_OK,intent);
//		finish();

//		// 以下方法不可行
//		String givenText = mEditText.getText().toString();
//		Intent editText = new Intent();
//		editText.putExtra("BAG" ,givenText);
//		setResult(RESULT_OK , editText);
//		finish();

	}
}
