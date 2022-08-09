package mg.tife.topo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import mg.tife.topo.activities.record.*;


import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Parametres;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.util.Utils;

public class RecordAdapter extends ArrayAdapter<Record> {
    private FusedLocationProviderClient fusedLocationClient;
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
        ltxView.setText("TN:"+record.getTn());
        lotissementView.setText("Parcelle:"+record.getParcelle()+" - Date:"+ record.getDate());

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
            Intent intent = new Intent(getContext(), RecordItem1Activity.class);
            intent.putExtra(DB.COLUMN_ID, record.getId());
            getContext().startActivity(intent);
        });

        convertView.findViewById(R.id.recordBtnItemRemove).setOnClickListener((e)->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Confirmation");
            builder.setMessage("Voulez vous supprimer cette ligne : TN:" + record.getTn() + " - P:"+record.getParcelle()+" - "+record.getAdressTerrain() + " ?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteRecord(record.getId());
                            Activity act = (Activity)getContext();
                            act.finish();
                            Intent intent = new Intent(getContext(), RecordActivity.class);
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


        convertView.findViewById(R.id.recordITemBtnItemExp).setOnClickListener((e)->{
            Utils.exportLaborde(record,db,getContext());
        });
        return convertView;
    }

}
