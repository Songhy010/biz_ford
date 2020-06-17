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
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterGallery;
import com.sabayosja.fordcambodia.android.adapter.AdapterGalleryExterior;


import org.json.JSONArray;


public class FragmentExterior extends Fragment {

    private final String TAG = "Fm NewStore";
    private View root_view;
    private JSONArray arrExterior;

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
        final AdapterGalleryExterior adapter = new AdapterGalleryExterior(root_view.getContext(),arrExterior);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }

    public static final FragmentExterior newInstance(final JSONArray arrNewStore) {
        FragmentExterior fragmentReferral;
        fragmentReferral = new FragmentExterior();

        fragmentReferral.arrExterior = arrNewStore;
        return fragmentReferral;
    }
}
