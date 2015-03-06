package de.empty2k12.antaresoneupdater;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mattia on 05/03/2015.
 */
public class DonateDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View v = inflater.inflate(R.layout.donate_dialog, null);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.close_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                    }
                });

        Button donate = (Button) v.findViewById(R.id.donate_button);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=mattia.dalleva%40live.it&lc=IT&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return builder.create();
    }
}
