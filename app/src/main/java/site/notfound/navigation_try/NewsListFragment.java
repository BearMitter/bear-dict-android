package site.notfound.navigation_try;


import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/3/14.
 */

public class NewsListFragment extends Fragment
{
    private Context mContext;
    static Gson gson = new Gson();
    static String mongoUrl ="mongodb://bear:testonly@ds012578.mlab.com:12578/bear-remote";
    ArrayList<News> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity().getApplicationContext();

        new ConnectMongoTask();

        MongoClientURI uri = new MongoClientURI(mongoUrl);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("bear-remote");
        System.out.println("Connect to database successfully");
        MongoCollection<Document> collection = mongoDatabase.getCollection("news_article");

        ArrayList<News> list=new ArrayList<>();
        FindIterable<Document> findIterable  = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document d=mongoCursor.next();
            list.add(gson.fromJson(d.toJson(), News.class));
        }
        mongoClient.close();

        View v=inflater.inflate(R.layout.framelayout_newslist, container, false);

        final List<String> wordStrList = new ArrayList<>();
        for (News n : list) {
            wordStrList.add(n.getTitle());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, wordStrList);
        SwipeMenuListView listView = v.findViewById(R.id.NewsList);
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                SwipeMenuItem goodItem = new SwipeMenuItem(getContext());
                // set item background
                goodItem.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1, 0xF5)));
                // set item width
                goodItem.setWidth(dp2px(90));
                // set a icon
                goodItem.setIcon(R.mipmap.ic_action_good);
                // add to menu
                menu.addMenuItem(goodItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_action_discard);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        Toast.makeText(getContext(), R.string.press_like_button, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), R.string.delete_single_word, Toast.LENGTH_SHORT).show();
                        wordStrList.remove(position);
                        break;
                }
                return true;
            }
        });

        return v;
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    class ConnectMongoTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {


            return null;
        }
    }



}
