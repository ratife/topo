package mg.tife.topo.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import mg.tife.topo.R;
import mg.tife.topo.activities.AboutActivity;
import mg.tife.topo.activities.ParametreActivity;
import mg.tife.topo.activities.record.*;
import mg.tife.topo.activities.user.UserActivity;
import mg.tife.topo.activities.user.UserFormActivity;
import mg.tife.topo.data.DB;
import mg.tife.topo.databinding.FragmentFirstBinding;
import mg.tife.topo.ui.login.LoginActivity;


public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    DB db;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = DB.getInstance(this.getContext());
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ParametreActivity.class);
                startActivity(intent);
            }
        });
        binding.btnRec.setOnClickListener((e)->{
            Intent intent = new Intent(getContext(), RecordActivity.class);
            startActivity(intent);
        });
        binding.btnUser.setOnClickListener((e)->{
            Intent intent = new Intent(getContext(), UserActivity.class);
            startActivity(intent);
        });
        binding.btnDeconnect.setOnClickListener((e)->{
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            this.getActivity().finish();
        });
        binding.btnProfil.setOnClickListener((e)->{
            Intent intent = new Intent(getContext(), UserFormActivity.class);
            intent.putExtra(DB.COLUMN_ID, db.userConnected.getId());
            this.startActivity(intent);
        });
        binding.btnAbout.setOnClickListener((e)->{
            Intent intent = new Intent(getContext(), AboutActivity.class);
            db.userConnected = null;
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}