package de.empty2k12.antaresoneupdater;

import android.app.*;
import android.content.*;
import android.media.Image;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class InfoDialog extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

		View v = inflater.inflate(R.layout.info_dialog, null);
		builder.setView(v)
			// Add action buttons
			.setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id)
				{

				}
			});
		ImageButton xda = (ImageButton) v.findViewById(R.id.xda_button);
		xda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
				{
                    String url = "http://forum.xda-developers.com/member.php?u=4337413";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
                }
            });
			
		ImageButton rooboo = (ImageButton) v.findViewById(R.id.rooboo_button);
		rooboo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
				{
                    String url = "http://www.roobooreviews.de";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
                }
            });


		return builder.create();
	}
}
