package de.empty2k12.antaresoneupdater;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.widget.*;

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
	protected void onHandleIntent(Intent intent) {
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

			Notification.Builder mBuilder = new Notification.Builder(getApplicationContext())
				.setSmallIcon(android.R.drawable.stat_sys_download_done)
				.setAutoCancel(true)
				.setContentTitle(getResources().getString(R.string.rom_update_available))
				.setContentText(getResources().getString(R.string.new_update) + ": " + systemVersion)
				.setCategory("AntaresOneUpdater")
				.setColor(Color.parseColor("#2196F3"))
				.setContentIntent(pIntent);
				
			Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
			inboxStyle.setBigContentTitle(getResources().getString(R.string.version_details));
			inboxStyle.addLine(getResources().getString(R.string.your_version) + ": " + systemVersion);
			inboxStyle.addLine(getResources().getString(R.string.upstream_version) + ": " + upstreamVersion);
			mBuilder.setStyle(inboxStyle);

			mNotification = mBuilder.getNotification();
			mNotificationManager.notify(NOTIFICATION_ID, mNotification);
		} 
		registerAlarm(getApplicationContext());
	}
	
	public void registerAlarm(Context context)
	{
		AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(this, UpdateService.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,  SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR, pi);
	}
}
