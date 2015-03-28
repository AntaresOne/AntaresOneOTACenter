package de.empty2k12.antaresoneupdater;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity 
{
    private TextView mResult;
    private TextView mStatus;
    private TextView mLastChecked;
    private Button mDownload;
    private Button mUpdate;
    private AntaresUtil util;
    /** private Button mDevThread;
     * private Button mQAThread;
     */
    private Button mAntaresOne;
    private Button mAlucard24;
    private Button mEmpty2k12;
    private ImageButton mFacebook;
    private ImageButton mGooglePlus;
    private ImageButton mTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		mResult = (TextView) findViewById(R.id.status);
		mStatus = (TextView) findViewById(R.id.status_text);
		mLastChecked = (TextView) findViewById(R.id.last_checked);
		mDownload = (Button) findViewById(R.id.download_button);
		mUpdate = (Button) findViewById(R.id.check_button);
		/** mDevThread = (Button) findViewById(R.id.dev_thread);
		 *   mQAThread = (Button) findViewById(R.id.qa_thread);
	         */
		AntaresOne = (Button) findViewById(R.id.antaresone_xda);
		mAlucard24 = (Button) findViewById(R.id.alucard24_xda);
		mEmpty2k12 = (Button) findViewById(R.id.empty2k12_xda);
		mFacebook = (ImageButton) findViewById(R.id.facebook);
		mGooglePlus = (ImageButton) findViewById(R.id.googleplus);
		mTwitter = (ImageButton) findViewById(R.id.twitter);

        /** mDevThread.setOnClickListener(new View.OnClickListener() {
         *           @Override
         *           public void onClick(View v)
         *           {
         *               String url = "http://forum.xda-developers.com/galaxy-s4/i9505-orig-develop/rom-cyanogenmod-12-t2943934";
         *               Intent intent = new Intent(Intent.ACTION_VIEW);
         *               intent.setData(Uri.parse(url));
	 *     	  startActivity(intent);
         *           }
	 *});
	 *
	 *
         * mQAThread.setOnClickListener(new View.OnClickListener() {
	 * @Override
         *   public void onClick(View v)
         *   {
         *       String url = "http://forum.xda-developers.com/galaxy-s4/help/qa-cyanogenmod-12-0-t2946574";
         *       Intent intent = new Intent(Intent.ACTION_VIEW);
         *       intent.setData(Uri.parse(url));
         *       startActivity(intent);
         *   }
         *});
	 *
         * mDarkUI.setOnClickListener(new View.OnClickListener() {
         *   @Override
         *   public void onClick(View v)
         *   {
         *       String url = "http://forum.xda-developers.com/galaxy-s4/help/qa-franzyroys-dark-ui-t2994011";
         *       Intent intent = new Intent(Intent.ACTION_VIEW);
         *       intent.setData(Uri.parse(url));
         *       startActivity(intent);
         *   }
         *});
	 */
        mAntaresOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "http://forum.xda-developers.com/member.php?u=5464206";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mAlucard24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "http://forum.xda-developers.com/member.php?u=4899086";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mEmpty2k12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "http://forum.xda-developers.com/member.php?u=4337413";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "https://www.facebook.com/AntaresOneSoftware";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "https://plus.google.com/+MattiaDAlleva";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "https://twitter.com/antares__one";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

		mDownload.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
				{
                   	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					String url = preferences.getString("highest_link", "");

					if (!url.isEmpty())
					{
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
					}
					else
					{
						new JSONParse().execute();
					}

                }
            });

		mUpdate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
				{
                	new JSONParse().execute();
				}
            });
		Intent intent = new Intent(getApplicationContext(), UpdateService.class);
		getApplicationContext().startService(intent);
		new JSONParse().execute();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_info:
				DialogFragment newFragment = new InfoDialog();
				newFragment.show(getFragmentManager(), "about");
				break;
            case R.id.action_donate:
                DialogFragment Fragment = new DonateDialog();
                Fragment.show(getFragmentManager(), "donate");
                break;
		}
        return super.onOptionsItemSelected(item);
    }

	public class JSONParse extends AsyncTask<String, Void, String>
	{

		private ProgressDialog dialog;
		private String answer;

		public JSONParse()
		{
			dialog = new ProgressDialog(MainActivity.this);
			util = new AntaresUtil();
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			dialog.setMessage(getResources().getString(R.string.loading));
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			answer = util.testIt(getApplicationContext(), true);
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = preferences.edit();
			Calendar c = Calendar.getInstance(); 
			int minutes = c.get(Calendar.MINUTE);
			int hours = c.get(Calendar.HOUR_OF_DAY);
			editor.putString("last_checked", hours + ":" + minutes); // value to store
			editor.commit();
			return answer;
		}

		@Override
		protected void onPostExecute(final String result)
		{
			dialog.dismiss();
			dialog.cancel();
			runOnUiThread(new Runnable(){
					@Override
					public void run()
					{
						DLFile highestServerFile = util.max(util.handleJson(result));

						int systemVersion = util.stripVersionNumber(util.getRoDotCmDotDisplayDotVersion());
						int upstreamVersion = util.stripVersionNumber(highestServerFile.getName());

						mResult.setText(getResources().getString(R.string.system) + ": " + systemVersion + " " + getResources().getString(R.string.upstream) + ": " + upstreamVersion);
						mStatus.setText((systemVersion < upstreamVersion) ? getResources().getString(R.string.update_available) + "!" : getResources().getString(R.string.your_system_is_up_to_date) + "!");
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						mLastChecked.setText(getResources().getString(R.string.last_checked) + ": " + preferences.getString("last_checked", getResources().getString(R.string.never_checked)));

						SharedPreferences.Editor editor = preferences.edit();
						editor.putString("highest_name", highestServerFile.getName()); // value to store
						editor.putString("highest_link", highestServerFile.getDownloadLink());
						editor.commit();

						mDownload.setEnabled((systemVersion < upstreamVersion));

					}
				});
			super.onPostExecute(result);
		}
	}
}
