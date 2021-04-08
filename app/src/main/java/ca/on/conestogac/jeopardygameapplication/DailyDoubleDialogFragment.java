package ca.on.conestogac.jeopardygameapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class DailyDoubleDialogFragment extends DialogFragment {
    private Button buttonYes;
    private Button buttonNo;
    private TextView textViewWager;
    private TextView textViewDialogScore;
    private final String BUNDLE_SCORE_KEY = "Score";

    private int wager = 0;

    //This interface allows us to implement the onClick behavior of the Dialog buttons in the activity its called from
    //****The activity that shows this dialog needs to implement this interface
    public interface DailyDoubleDialogListener{
        public void onDailyDoubleDialogYesButtonClick(DialogFragment dialog, int wager);
        public void onDailyDoubleDialogNoButtonClick(DialogFragment dialog, int wager);
    }

    DailyDoubleDialogFragment.DailyDoubleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //**Need to style the alert dialog when building it
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        //Parse the xml of the view to create the view
        LayoutInflater inflator = getActivity().getLayoutInflater();

        //Need to create view so we can set the OnClick events for the buttons in the view
        View dialogView = inflator.inflate(R.layout.activity_daily_double_dialog, null);

        Bundle dialogBundle = getArguments();

        textViewWager = dialogView.findViewById(R.id.editTextDailyDoubleWager);
        buttonYes = dialogView.findViewById(R.id.buttonYes);
        buttonNo = dialogView.findViewById(R.id.buttonNo);
        textViewDialogScore = dialogView.findViewById(R.id.textViewDialogScore);

        textViewDialogScore.setText(dialogBundle.getString(BUNDLE_SCORE_KEY));

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWager();
                listener.onDailyDoubleDialogYesButtonClick(DailyDoubleDialogFragment.this, wager);
                //Manually dismiss the dialog box because this is a custom button
                dismiss();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWager();
                listener.onDailyDoubleDialogNoButtonClick(DailyDoubleDialogFragment.this, wager);
                dismiss();
            }
        });
        builder.setView(dialogView);

        return builder.create();

    }

    //This method is called when the dialog attaches to the parent view that called it
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Attempt to cast the activity that called the dialog to the interface we created
        try {
            listener = (DailyDoubleDialogFragment.DailyDoubleDialogListener) context;
        }
        catch(ClassCastException ex)
        {
            throw new ClassCastException(getActivity().toString() + "must implement PointsDialogInterface");
        }
    }

    public void addWager()
    {
        if(textViewWager.getText().toString().equals(""))
        {
            wager = 0;
        }
        else
        {
            wager = Integer.parseInt(textViewWager.getText().toString());
        }

    }

}
