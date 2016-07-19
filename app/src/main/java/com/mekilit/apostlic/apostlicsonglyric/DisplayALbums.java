package com.mekilit.apostlic.apostlicsonglyric;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DisplayALbums extends AppCompatActivity {
    MyDbHandler helper = new MyDbHandler(this,null,null,1);
    Toolbar toolbar;
    boolean all=false;//to know whether this activity is called to show all albums or not
    String ArtistName;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String rawData = intent.getStringExtra(Intent.EXTRA_TEXT);//raw data name+Selcet type
        if (rawData.charAt(0)=='1')
            all=true;//called to display all albums
        ArtistName= minusFirst(rawData);

        setContentView(R.layout.activity_display_albums);
        toolbar= (Toolbar) findViewById(R.id.appBar);
        recyclerView = (RecyclerView) findViewById(R.id.RC);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        AlbumAdaptor adaptor;
        if(!all)
        {
        toolbar.setTitle(ArtistName);
        toolbar.setSubtitle(helper.CountAlbumF(ArtistName));

            adaptor =new AlbumAdaptor(this,helper.getAlbum(ArtistName));
        }
        else {
            toolbar.setTitle("ሁሉም አልበሞች");
            toolbar.setSubtitle(helper.CountAlL('a') + " አልበሞች");
            adaptor = new AlbumAdaptor(this,helper.SelectAll());
        }
        recyclerView.setAdapter(adaptor);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(DisplayALbums.this, OneAlbum.class);
                        if (!all)
                            intent.putExtra(Intent.EXTRA_TEXT, helper.getAlbum(ArtistName).get(position));
                        else
                            intent.putExtra(Intent.EXTRA_TEXT, helper.SelectAll().get(position));
                        startActivity(intent);
                    }
                })
        );
        
    }



    public String minusFirst (String data)
    {   String ret="";
        char [] arr = new char[data.length()-1];
        for (int j=0;j<arr.length;j++)
        {
            arr[j]=data.charAt(j+1);
        }
        for (int i=0;i<arr.length;i++)
        {
            ret+=arr[i];
        }
        return ret;
    }
}
