package mg.tife.topo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;
import mg.tife.topo.R;
import mg.tife.topo.data.model.Record;

public class RecordAdapter extends ArrayAdapter<Record> {

    public RecordAdapter(Context context,  List<Record> users) {
        super(context, 0, users);
       /* this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);*/
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_record, parent, false);
        }
        TextView ltxView = (TextView) convertView.findViewById(R.id.recordItemLTXView);
        TextView lotissementView = (TextView) convertView.findViewById(R.id.recordItemLotissementView);

        ltxView.setText(record.getLtx());
        lotissementView.setText(record.getLotissement());

        return convertView;
    }
}
