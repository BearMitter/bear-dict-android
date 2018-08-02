package site.notfound.navigation_try;

import android.app.DialogFragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/3/14.
 */

public class WordBookFragment extends Fragment
        implements View.OnClickListener
{
    private Context mContext;
    private WordDao wordDao;
    private String wordId;
    private String symble;
    private String meanings;
    private boolean add_flag=false;
    static SimpleDateFormat sdf=new SimpleDateFormat("mm/dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity().getApplicationContext();
        AppDatabase db = Room.databaseBuilder(mContext,
                AppDatabase.class, "bear").allowMainThreadQueries().build();
        View v=inflater.inflate(R.layout.framelayout_wordbook, container, false);
        Button b=v.findViewById(R.id.button_deleteAllWords);
        b.setOnClickListener(this);

        wordDao=db.getWordDao();
        List<Word> wordList = wordDao.getAllWords();
        final List<String> wordStrList = new ArrayList<>();
        for (Word w : wordList) {
            wordStrList.add(w.toString());
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, wordStrList);
        SwipeMenuListView listView = v.findViewById(R.id.WordsList);
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
                        Word word = wordDao.getWord(wordStrList.get(position).split(" ")[0]);
                        wordStrList.remove(position);
                        wordDao.deleteWords(word);
                        break;
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_deleteAllWords:
                clearWords();
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    public void clearWords() {
        DialogFragment newFragment = new DeleteAllWordsDialogFragment();
        newFragment.show(getActivity().getFragmentManager(), "deleteAllWords");
    }



}
