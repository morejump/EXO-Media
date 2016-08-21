package com.example.hp.exo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static String URL="http://s82.stream.nixcdn.com/f5bbb5f9deea7719c5806d7889aeb90d/57b975a9/NhacCuaTui925/ChungTaKhongThuocVeNhau-SonTungMTP-4528181.mp3";
    private static int BUFFER_SEGMENT_SIZE=64*1024;
    private static int BUFFER_SEGMENT_COUNT=256;
    //
    private ExoPlayer exoPlayer;
    private MediaCodecAudioTrackRenderer audioRenderer;
    //
    private TextView title;
    private Button play;

    private Boolean isPlay;

    @Override
    protected void onResume() {
        super.onResume();
        loadStremURL(URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init exo
        exoPlayer= ExoPlayer.Factory.newInstance(BIND_AUTO_CREATE);
        title = (TextView) findViewById(R.id.txt_title);
        play= (Button) findViewById(R.id.btn_Play);
        isPlay=false;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlay){
                exoPlayer.setPlayWhenReady(true);
                play.setText("Pause");
                isPlay=true;}
                else {
                    exoPlayer.setPlayWhenReady(false);
                    play.setText("Play");
                    isPlay=false;
                }
            }

        });
    }
    private void loadStremURL(String url){
        // get title
        String titile = url.substring(url.lastIndexOf("/")+1);
        title.setText(titile);
        Uri uri = Uri.parse(url);
// Settings for exoPlayer
        Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
        String userAgent = Util.getUserAgent(this, "EXO");
        DataSource dataSource = new DefaultUriDataSource(this, null, userAgent);
        ExtractorSampleSource sampleSource = new ExtractorSampleSource(
                uri, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT);
        audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
// Prepare ExoPlayer
        exoPlayer.prepare(audioRenderer);
    }


}
