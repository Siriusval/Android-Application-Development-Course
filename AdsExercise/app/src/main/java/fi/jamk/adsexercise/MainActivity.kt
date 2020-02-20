package fi.jamk.adsexercise

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize MobileAds SDK
        MobileAds.initialize(this) {}

        val adViewList = mutableListOf<AdView>()

        //Text different layouts
        adViewList.add(adViewBanner)
        adViewList.add(adViewLargeBanner)
        adViewList.add(adViewMediumRectangle)
        adViewList.add(adViewFullBanner)
        adViewList.add(adViewLeaderboard)
        adViewList.add(adViewSmartBanner)

        //val adId = AdvertisingIdClient.getAdvertisingIdInfo(this).id
        val adRequest = AdRequest.Builder().build()

        for (view in adViewList) {
            //view.adUnitId = adId
            view.loadAd(adRequest)
        }

        //rewarded Ad
        val rewardedAd = RewardedAd(
            this,
            "ca-app-pub-3940256099942544/5224354917"
        )

        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.d("Debug", "Ad loaded")
            }

            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                // Ad failed to load.
                Log.d("Debug", "Ad load failed, error code: $errorCode")
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)


        testButton.setOnClickListener {
            if (rewardedAd.isLoaded) {
                val adCallback = object : RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        // Ad opened.
                        Log.d("Debug", "Ad opened")
                    }

                    override fun onRewardedAdClosed() {
                        // Ad closed.
                        Log.d("Debug", "Ad closed")
                    }

                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        // User earned reward.
                        Log.d("Debug", "User earned reward")
                    }

                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        // Ad failed to display.
                        Log.d("Debug", "Ad failed to display")
                    }
                }
                rewardedAd.show(this, adCallback)
            } else {
                Log.d("Debug", "The rewarded ad wasn't loaded yet.")
                Toast.makeText(this,"Ad was not ready, try again.",Toast.LENGTH_SHORT)
            }
        }


    }


}


