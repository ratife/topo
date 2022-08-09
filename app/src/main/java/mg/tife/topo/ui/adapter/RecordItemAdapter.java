package mg.tife.topo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.activities.record.*;

public class RecordItemAdapter extends ArrayAdapter<RecordItem> {
    DB db;
    public RecordItemAdapter(Context context, List<RecordItem> users) {
        super(context, 0, users);
        db = DB.getInstance(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordItem recordItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_record_item, parent, false);
        }
        TextView recordItemStantionView = (TextView) convertView.findViewById(R.id.recordItemStantionView);
        TextView recordItemNumeroView = (TextView) convertView.findViewById(R.id.recordItemNumeroView);
        TextView recordItemAngleView = (TextView) convertView.findViewById(R.id.recordItemItemAngleView);
        TextView recordItemObsView = (TextView) convertView.findViewById(R.id.recordItemObsView);

        recordItemStantionView.setText(recordItem.getStantion());
        recordItemNumeroView.setText(position+"");
        recordItemAngleView.setText(recordItem.getAngelV() + " - "+recordItem.getAngleH()+" - "+recordItem.getDistance());
        recordItemObsView.setText(recordItem.getObservation()+"");

        View vi = convertView;
        final TextView textView;

        //LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView.findViewById(R.id.recordITemBtnItemExp).setOnClickListener((e)->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Confirmation");
            builder.setMessage("Voulez vous supprimer cette ligne : " + recordItem.getAngelV() + " - "+recordItem.getAngleH()+" - "+recordItem.getDistance() + " ?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteRecordItem(recordItem.getId());
                            Activity act = (Activity)getContext();
                            act.finish();
                            Intent intent = new Intent(getContext(), RecordItem3Activity.class);
                            intent.putExtra(DB.COLUMN_ID, recordItem.getRecordId());
                            getContext().startActivity(intent);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        });
        convertView.findViewById(R.id.recordITemBtnItemEdit).setOnClickListener((View v)->{
            Activity act = (Activity)getContext();
            EditText editTextAngleH = (EditText) act.findViewById(R.id.editTextAngleH);
            EditText editTextAngleV = (EditText) act.findViewById(R.id.editTextAngleV);
            EditText editTextDistance = (EditText) act.findViewById(R.id.editTextDistance);
            TextView textViewStantion = (TextView) act.findViewById(R.id.textViewStantion);
            EditText editTextObservation = (EditText) act.findViewById(R.id.editTextObservation);

            editTextAngleH.setText(recordItem.getAngleH()+"");
            editTextAngleV.setText(recordItem.getAngelV()+"");
            editTextDistance.setText(recordItem.getDistance()+"");
            textViewStantion.setText(recordItem.getStantion());
            editTextObservation.setText(recordItem.getObservation());

            db.setCurrentRecordItemId(recordItem.getId());
        });
        return convertView;
    }
}