package mg.tife.topo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.ui.RecordItemActivity;

public class RecordAdapter extends ArrayAdapter<Record> {
    DB db;
    public RecordAdapter(Context context,  List<Record> users) {
        super(context, 0, users);
        db = DB.getInstance(context);
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
        TextView ltxView = (TextView) convertView.findViewById(R.id.recordItemAngleView);
        TextView lotissementView = (TextView) convertView.findViewById(R.id.recordItemDistanceView);
        ltxView.setText(record.getLtx());
        lotissementView.setText(record.getLotissement());

        ImageFilterView waitImage = (ImageFilterView) convertView.findViewById(R.id.imageFilterViewWait);
        ImageFilterView readyImage = (ImageFilterView) convertView.findViewById(R.id.imageFilterViewReady);
        ImageFilterButton expoImage = (ImageFilterButton) convertView.findViewById(R.id.recordITemBtnItemExp);
        if(db.getNbrRecordItem(record.getId())>0){
            readyImage.setVisibility(View.VISIBLE);
            waitImage.setVisibility(View.GONE);
        }
        else{
            readyImage.setVisibility(View.GONE);
            waitImage.setVisibility(View.VISIBLE);
            expoImage.setVisibility(View.GONE);
        }



        convertView.findViewById(R.id.recordITemBtnItemEdit).setOnClickListener((e)->{
            Intent intent = new Intent(getContext(), RecordItemActivity.class);
            intent.putExtra(DB.COLUMN_ID, record.getId());
            intent.putExtra(DB.RECORD_COLUMN_LTX, record.getLtx());
            intent.putExtra(DB.RECORD_COLUMN_LOTISSEMENT, record.getLotissement());
            getContext().startActivity(intent);
        });

        convertView.findViewById(R.id.recordITemBtnItemExp).setOnClickListener((e)->{
            List<RecordItem> recordItemList = db.getListRecordItem(record.getId());
            try {

                File root = Environment.getExternalStoragePublicDirectory
                        (
                                Environment.DIRECTORY_DOWNLOADS + "/topo/"
                        );
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root,"Export_" + record.getLtx() + ".txt");

                FileWriter fileWriter = new FileWriter(gpxfile);
                PrintWriter printWriter = new PrintWriter(fileWriter);

                //FileWriter writer = new FileWriter(gpxfile);
                int cpt = 1;
                for(RecordItem recordItem:recordItemList){
                    double x = recordItem.getDistance() * Math.sin(recordItem.getAngle());
                    double y = recordItem.getDistance() * Math.cos(recordItem.getAngle());
                    printWriter.println(cpt +","+x+","+y+","+recordItem.getObservation());
                    cpt++;
                }
                printWriter.flush();
                printWriter.close();
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });



        return convertView;
    }
}
