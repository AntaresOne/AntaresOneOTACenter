package de.empty2k12.antaresoneupdater;

import android.app.*;
import android.content.*;
import android.os.*;

public class UpdateService extends IntentService
{

	public UpdateService()
	{
		super("UpdateService");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		AntaresUtil util = new AntaresUtil();

		DLFile highestServerFile = util.max(util.handleJson(util.testIt(getApplicationContext(), false)));

		int systemVersion = util.stripVersionNumber(util.getRoDotCmDotDisplayDotVersion());
		int upstreamVersion = util.stripVersionNumber(highestServerFile.getName());

		int NOTIFICATION_ID = 1;
		Notification mNotification;
		NotificationManager mNotificationManager;

		mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

		if (upstreamVersion > systemVersion)
		{
			Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, mainIntent, 0);

			Notification.Builder builder = new Notification.Builder(getApplicationContext())
				.setSmallIcon(android.R.drawable.stat_sys_download_done)
				.setAutoCancel(true)
				.setContentTitle("Rom Update Available!")
				.setContentText("Your Version: " + systemVersion + " Upstream Version: " + upstreamVersion);

			mNotification = builder.getNotification();
			mNotificationManager.notify(NOTIFICATION_ID, mNotification);
		} else {
			registerAlarm(getApplicationContext());
		}
	}
	
	public void registerAlarm(Context context)
	{
		AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(this, UpdateService.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,  SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_HOUR, pi);
	}
}
