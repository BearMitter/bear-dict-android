package site.notfound.navigation_try;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by lenovo on 2018/3/14.
 */

public class LookUpFragment extends Fragment
        implements View.OnClickListener
{

    public static final String EXTRA_MESSAGE = "com.example.lenovo.MESSAGE";
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
        wordDao=db.getWordDao();
        View v=inflater.inflate(R.layout.framelayout_lookup, container, false);
        Button b=v.findViewById(R.id.lookup);
        Button buttonAdd=v.findViewById(R.id.button_add);
        b.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);

        return v;
    }

    public void addToWordBook(View view){
        if(wordId==null){
            Toast.makeText(mContext, R.string.no_word_to_add, Toast.LENGTH_SHORT).show();
            return;
        }
        if(wordDao.getWord(wordId)!=null){
            Toast.makeText(mContext, R.string.word_already_added, Toast.LENGTH_SHORT).show();
            return;
        }

        if(!symble.equals("")&&!meanings.equals("NULL")){
            Word word=new Word(wordId,symble,meanings,sdf.format(new Date()));
            wordDao.insertWords(word);
            Toast.makeText(mContext, R.string.add_word, Toast.LENGTH_SHORT).show();
            add_flag=true;
        }else{
            Toast.makeText(mContext, R.string.word_can_not_be_added, Toast.LENGTH_SHORT).show();
        }
    }


    public void showMeanings(View view) throws IOException {
        EditText editText =  getActivity().findViewById(R.id.word);
        wordId = editText.getText().toString().trim();
        String[] re = getDefine(wordId);
        symble=re[0].trim();
        meanings=re[1].trim();
        TextView textView= getActivity().findViewById(R.id.meanings);
        textView.setText(symble + "\n\n[" + symble + "]\n" + meanings);
    }


    public static String[] getDefine(String word) throws IOException {

        String url = "http://www.iciba.com/index.php?a=getWordMean&c=search&list=1&word=";
        String charset = "UTF-8";

        HttpURLConnection connection = (HttpURLConnection) new URL(url + word).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next().replaceAll(" ", "");
        scanner.close();

        StringBuffer sb = new StringBuffer("");

        JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();

        JsonObject baesInfo = jsonObject.getAsJsonObject("baesInfo");

        if (baesInfo == null) {
            return new String[] { "", "NULL" };
        }

        JsonArray symbols = baesInfo.getAsJsonArray("symbols");

        if (symbols == null) {
            return new String[] { "", "NULL" };
        }

        String ph_am = "";

        for (JsonElement e : symbols) {

            ph_am = e.getAsJsonObject().get("ph_am").getAsString();

            JsonArray array = e.getAsJsonObject().getAsJsonArray("parts");

            for (JsonElement o : array) {
                String part = o.getAsJsonObject().get("part").getAsString();
                sb.append(part);

                JsonArray means = o.getAsJsonObject().getAsJsonArray("means");

                for (JsonElement s : means) {
                    sb.append(s.getAsString() + ";");
                }
                sb.setCharAt(sb.length() - 1, '\n');

            }
        }

        return new String[] { ph_am, sb.toString().replaceAll("，", ",").replaceAll("（", "(").replaceAll("）", ")")
                .replaceAll("〈", "<").replaceAll("〉", ">").replaceAll("、", ",") };
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_add:
                addToWordBook(view);
            case R.id.lookup:
                try {
                    showMeanings(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
