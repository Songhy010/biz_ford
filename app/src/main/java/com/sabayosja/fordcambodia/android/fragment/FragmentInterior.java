package com.sabayosja.fordcambodia.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterGallery;
import com.sabayosja.fordcambodia.android.adapter.AdapterGalleryExterior;

import org.json.JSONArray;


public class FragmentInterior extends Fragment {

    private final String TAG = "Fm NewStore";
    private View root_view;
    private JSONArray arrInterior;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root_view == null) {
            root_view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_exterior, container, false);
        }
        return root_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        final RecyclerView recycler = root_view.findViewById(R.id.recycleGallery);
        final GridLayoutManager manager = new GridLayoutManager(root_view.getContext(),2);
        //LinearLayoutManager manager = new LinearLayoutManager(root_view.getContext(),RecyclerView.VERTICAL,false);
        final AdapterGalleryExterior adapter = new AdapterGalleryExterior(root_view.getContext(),arrInterior);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }

    public static final FragmentInterior newInstance(final JSONArray arrNewStore) {
        FragmentInterior fragmentReferral;
        fragmentReferral = new FragmentInterior();

        fragmentReferral.arrInterior = arrNewStore;
        return fragmentReferral;
    }

    
    
}
