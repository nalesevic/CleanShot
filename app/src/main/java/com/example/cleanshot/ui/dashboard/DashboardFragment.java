package com.example.cleanshot.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cleanshot.Post;
import com.example.cleanshot.PostDetailsActivity;
import com.example.cleanshot.PostListAdapter;
import com.example.cleanshot.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listView = (ListView) root.findViewById(R.id.list);
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Deponija", "Ilidza", "Mala deponija blizu terminala"));
        posts.add(new Post("Deponija", "Ilidza", "Mala deponija blizu terminala"));
        posts.add(new Post("Deponija", "Ilidza", "Mala deponija blizu terminala"));
        posts.add(new Post("Deponija", "Ilidza", "Mala deponija blizu terminala"));
        PostListAdapter postListAdapter = new PostListAdapter(getContext(), posts);
        listView.setAdapter(postListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), PostDetailsActivity.class);
                intent.putExtra("title", post.getTitle());
                intent.putExtra("location", post.getLocation());
                intent.putExtra("description", post.getDescription());
                startActivity(intent);
            }
        });

        return root;
    }
}