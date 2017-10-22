package UiComponanets.youTubePlayer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.networkmodule.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by ddopik on 1/24/2018.
 */

public class YouTubeFragment extends YouTubePlayerSupportFragment {
    // API キー
    private  final String API_KEY = "AIzaSyB3uJ-dzR0RBzDfq6bxBwRYyJihjBxYjAw";

    // YouTubeのビデオID
    private  String VIDEO_ID = "TxNRQfUnDJA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.you_tube_news_fragment, container, false);

        // YouTubeフラグメントインスタンスを取得
//        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        // レイアウトにYouTubeフラグメントを追加
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.youtube_layout, this).commit();

        // YouTubeフラグメントのプレーヤーを初期化する
        this.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            // YouTubeプレーヤーの初期化成功
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(getVedioID());
                    player.play();
                }
            }

            // YouTubeプレーヤーの初期化失敗
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();

                Log.d("errorMessage:", errorMessage);
            }
        });

        return rootView;
    }


    public void setVedioID(String vedioID){
        this.VIDEO_ID=VIDEO_ID;
    }
    public String getVedioID(){
        return this.VIDEO_ID;
    }
}