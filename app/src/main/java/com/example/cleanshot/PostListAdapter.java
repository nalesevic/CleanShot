package com.example.cleanshot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PostListAdapter extends BaseAdapter {
    private Context context;
    private List<Post> posts;

    public PostListAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return posts.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_posts, parent, false);

        TextView title = convertView.findViewById(R.id.tv_title);
        TextView location = convertView.findViewById(R.id.tv_location);

        Post post = posts.get(position);

        title.setText(post.getTitle());
        location.setText(post.getLocation());

        return convertView;
    }
}
