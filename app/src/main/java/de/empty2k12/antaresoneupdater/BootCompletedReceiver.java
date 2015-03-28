package de.empty2k12.antaresoneupdater;

import android.content.*;

public class BootCompletedReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent arg1)
	{
		Intent intent = new Intent(context, UpdateService.class);
		context.startService(intent);
	}
}
