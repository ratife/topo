package mg.tife.topo.util;

import android.content.Context;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Parametres;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.RecordItem;

public class Utils {
    public static boolean testNotVide(EditText editText,String fieldName){
        if(editText.getText().toString().trim().equals("")){
            editText.setError(fieldName +" obligatoire");
            return false;
        }
        return true;
    }

    public static void exportLaborde(Record record, DB db, Context ctx){
        List<RecordItem> recordItemList = db.getListRecordItem(record.getId());

        List<String> keyList = new ArrayList<>();
        Map<String,List> mapRecord = new HashMap<>();
        updateData(keyList,mapRecord,recordItemList);

        PrintWriter printWriter = getPrinter(record,db,"Labord");
        double xo = record.getXo();
        double yo = record.getYo();
        double zo = record.getZo();
        double voLastStantion = record.getVo();
        int cpt = 0;
        //printWriter.println("P."+cpt + " S1"  +" "+xo+" "+yo+" "+zo);

        String lastStation = "s1";
        int cptStation = 0;
        for(String station : keyList){
            cptStation++;
            List<RecordItem> listRecord = mapRecord.get(station);
            if(!station.toLowerCase().equals("s1")){
                List<RecordItem> listRecordLastStation = mapRecord.get(lastStation);
                double gm1 = voLastStantion + listRecordLastStation.get(listRecordLastStation.size()-1).getAngleH();
                double gm2 = listRecord.get(0).getAngleH();
                voLastStantion = gm1 + 200 - gm2;
            }

            int max = listRecord.size() - 1;
            if(cptStation == keyList.size())
                max = listRecord.size();

            for(int i=1;i<max;i++){
                cpt++;
                double dh,x,y;
                RecordItem recordItem = listRecord.get(i);
                dh = recordItem.getDistance() * Math.sin(recordItem.getAngelV() * Math.PI/200);
                double gm = voLastStantion + listRecord.get(i).getAngleH();
                gm = gm%400;
                x = xo + dh * Math.sin(gm*Math.PI/200);
                y = yo + dh * Math.cos(gm*Math.PI/200);
                printWriter.println("P."+cpt + " " +recordItem.getStantion() +" "+x+" "+y+" "+zo);
            }
            lastStation = station;
        }
        printWriter.flush();
        printWriter.close();
        Toast.makeText(ctx, "Fichier exporté !!!", Toast.LENGTH_LONG).show();
    }

    public static void exportBrute(Record record, DB db, Context ctx){
        List<RecordItem> recordItemList = db.getListRecordItem(record.getId());
        PrintWriter printWriter = getPrinter(record,db,"Brute");
        int cpt = 0;
        for(RecordItem item : recordItemList){
            printWriter.println(item.getStantion() + " " +cpt+" "+item.getAngleH()+" "+item.getAngelV()+" "+item.getDistance()+" "+item.getObservation());
            cpt++;
        }
        printWriter.flush();
        printWriter.close();
        Toast.makeText(ctx, "Fichier exporté !!!", Toast.LENGTH_LONG).show();
    }

    private static PrintWriter getPrinter(Record record,DB db,String type) {
        try {
            Parametres param = db.getParam();
            File root = Environment.getExternalStoragePublicDirectory
                    (
                            param.getPath()
                    );
            if (!root.exists()) {
                root.mkdirs();
            }
            DateFormat time_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String retDate = time_format.format(new Date());

            File gpxfile = new File(root, "Export_"+type+"_TN" + record.getTn() + "-P" + record.getParcelle() + "-" + retDate + ".txt");

            FileWriter fileWriter = new FileWriter(gpxfile);
            return new PrintWriter(fileWriter);
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

    }

    private static void updateData(List<String> keyList,Map<String,List> mapRecord,List<RecordItem> recordItemList){
        for(RecordItem recordItem:recordItemList){
            String station = recordItem.getStantion().toLowerCase();
            if(!keyList.contains(station)){
                keyList.add(station);
            }
            if(!mapRecord.containsKey(station)){
                List<RecordItem> list = new ArrayList<>();
                list.add(recordItem);
                mapRecord.put(station,list);
            }
            else{
                mapRecord.get(station).add(recordItem);
            }
        }
    }
}
