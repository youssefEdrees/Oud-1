package com.example.oud.artist.fragments.home;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oud.R;

public class ArtistHomeFragment extends Fragment implements OnStartDragListener{

    private ItemTouchHelper mItemTouchHelper;

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_artist, container, false);

        PopularReleasesAdapter adapter = new PopularReleasesAdapter(getContext(),this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_popular_release);


        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.addItem("track1","track1");
        adapter.addItem("track2","track2");
        adapter.addItem("track3","track3");
        adapter.addItem("track4","track4");
        adapter.addItem("track5","track5");
        adapter.addItem("track6","track6");
        adapter.addItem("track7","track7");
        adapter.addItem("track8","track8");
        adapter.addItem("track9","track9");


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}
