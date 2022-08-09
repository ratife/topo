package mg.tife.topo.activities.user.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import mg.tife.topo.R;
import mg.tife.topo.activities.user.UserActivity;
import mg.tife.topo.activities.user.UserFormActivity;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.data.model.User;

public class UserAdapter extends ArrayAdapter<User> {
    private FusedLocationProviderClient fusedLocationClient;
    DB db;
    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
        db = DB.getInstance(context);
       /* this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);*/
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.userNameItemView);
        TextView roleView = (TextView) convertView.findViewById(R.id.userRoleItemView);

        ImageFilterButton expoImage = (ImageFilterButton) convertView.findViewById(R.id.recordITemBtnItemExp);

        nameView.setText(" "+(position+1) +"- "+user.getPrintName());

        roleView.setText(user.getRole());



        convertView.findViewById(R.id.userITemBtnItemEdit).setOnClickListener((e)->{
            Intent intent = new Intent(getContext(), UserFormActivity.class);
            intent.putExtra(DB.COLUMN_ID, user.getId());
           /* intent.putExtra(DB.USER_COLUMN_NOM,user.getNom());
            intent.putExtra(DB.USER_COLUMN_PRENOM,user.getPrenom());
            intent.putExtra(DB.USER_COLUMN_LOGIN,user.getLogin());
            intent.putExtra(DB.USER_COLUMN_ROLE,user.getRole());*/
            getContext().startActivity(intent);
        });

        convertView.findViewById(R.id.userITemBtnItemDel).setOnClickListener((e)->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Confirmation");
            builder.setMessage("Voulez vous supprimer cette ligne : " +user.getPrintName() + " ?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteUser(user.getId());
                            Activity act = (Activity)getContext();
                            act.finish();
                            Intent intent = new Intent(getContext(), UserActivity.class);
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

        return convertView;
    }
}
