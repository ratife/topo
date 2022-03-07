package mg.tife.topo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.ui.RecordItemActivity;

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
        TextView angleView = (TextView) convertView.findViewById(R.id.recordItemAngleView);
        TextView distanceView = (TextView) convertView.findViewById(R.id.recordItemDistanceView);

        angleView.setText(recordItem.getAngle()+"");
        distanceView.setText(recordItem.getDistance()+"");

        View vi = convertView;
        final TextView textView;

        //LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView.findViewById(R.id.recordITemBtnItemExp).setOnClickListener((e)->{
            db.deleteRecordItem(recordItem.getId());
            Activity act = (Activity)getContext();
            act.finish();
            Intent intent = new Intent(getContext(), RecordItemActivity.class);
            intent.putExtra(DB.COLUMN_ID, recordItem.getRecordId());
            getContext().startActivity(intent);
        });
        convertView.findViewById(R.id.recordITemBtnItemEdit).setOnClickListener((View v)->{
            Activity act = (Activity)getContext();
            EditText angle = (EditText) act.findViewById(R.id.editTextAngle);
            angle.setText(recordItem.getAngle()+"");
            EditText dist = (EditText) act.findViewById(R.id.editTextDist);
            dist.setText(recordItem.getDistance()+"");
            EditText obs = (EditText) act.findViewById(R.id.editTextObs);
            obs.setText(recordItem.getObservation()+"");
            db.setCurrentRecordItemId(recordItem.getId());
        });
        return convertView;
    }
}