package course.labs.asynctasklab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

public class DownloaderTaskFragment extends Fragment {

	private DownloadFinishedListener mCallback;
	private Context mContext;
	
	@SuppressWarnings ("unused")
	private static final String TAG = "Lab-Threads";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Preserve across reconfigurations
		setRetainInstance(true);
		
		// TODO: Create new DownloaderTask that "downloads" data
		DownloaderTask downloads = new DownloaderTask();

		// TODO: Retrieve arguments from DownloaderTaskFragment
		// Prepare them for use with DownloaderTask. 
		Bundle bundle = this.getArguments();
		ArrayList<Integer> arg = bundle.getIntegerArrayList(MainActivity.TAG_FRIEND_RES_IDS);

		// TODO: Start the DownloaderTask 
		downloads.execute(arg);

	}

	// Assign current hosting Activity to mCallback
	// Store application context for use by downloadTweets()
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mContext = activity.getApplicationContext(); 

		// Make sure that the hosting activity has implemented
		// the correct callback interface.
		try {
			mCallback = (DownloadFinishedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement DownloadFinishedListener");
		}
	}

	// Null out mCallback
	@Override
	public void onDetach() {
		super.onDetach();
		mCallback = null;
	}

	// TODO: Implement an AsyncTask subclass called DownLoaderTask.
	// This class must use the downloadTweets method (currently commented
	// out). Ultimately, it must also pass newly available data back to 
	// the hosting Activity using the DownloadFinishedListener interface.

	public class DownloaderTask extends AsyncTask<ArrayList<Integer>, Void, String[]> {

//		@Override
//		protected Void doInBackground(Integer[]... integers) {
//			String data[] ;
//			Integer[] str = new Integer[3];
//			str = new Integer[]{R.raw.tswift, R.raw.rblack, R.raw.lgaga};
//				data = downloadTweets(str);
//				mCallback.notifyDataRefreshed(data);
//			return null;
//		}

		@Override
		protected String[] doInBackground(ArrayList<Integer>... agrs) {
			ArrayList<Integer> datas = agrs[0];
//			System.out.println(datas);
//			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

			Integer[] resourceIDS = new Integer[datas.size()];
			for (int i = 0; i < datas.size(); i++){
				resourceIDS[i] = datas.get(i);
			}
			return  downloadTweets(resourceIDS);

		}

//		以下也可以
//		@Override
//		protected String[] doInBackground(ArrayList<Integer>... params) {
//
//			return downloadTweets(params[0].toArray(new Integer[params[0].size()]));
//		}

		protected void onPostExecute(String[] results){
//		方法：onPostExecute（）
//      作用：接收线程任务执行结果、将执行结果显示到UI组件
//      注：必须复写，从而自定义UI操作
			mCallback.notifyDataRefreshed(results);
		}
	}
    
        // TODO: Uncomment this helper method
		// Simulates downloading Twitter data from the network

         private String[] downloadTweets(Integer resourceIDS[]) {
			final int simulatedDelay = 2000;
			String[] feeds = new String[resourceIDS.length];
			try {
				for (int idx = 0; idx < resourceIDS.length; idx++) {
					InputStream inputStream;
					BufferedReader in;
					try {
						// Pretend downloading takes a long time
						Thread.sleep(simulatedDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					inputStream = mContext.getResources().openRawResource(
							resourceIDS[idx]);
					in = new BufferedReader(new InputStreamReader(inputStream));

					String readLine;
					StringBuffer buf = new StringBuffer();

					while ((readLine = in.readLine()) != null) {
						buf.append(readLine);
					}

					feeds[idx] = buf.toString();

					if (null != in) {
						in.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return feeds;
		}



    
    
    
    
    
    

}