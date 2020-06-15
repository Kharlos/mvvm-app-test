package ve.com.cblanco1989.features

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ve.com.cblanco1989.R
import ve.com.cblanco1989.features.home.ui.activity.HomeActivity

class SplashActivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activty)

        HiAnalyticsTools.enableLog();
        val instance: HiAnalyticsInstance = HiAnalytics.getInstance(this)
        instance.setUserProfile("userKey","miKeyCarlos")

        val bundle = Bundle()
        bundle.putString("exam_difficulty", "high")
        bundle.putString("exam_level", "1-1")
        bundle.putString("exam_time", "20190520-08")
        instance.onEvent("begin_examination", bundle)

        GlobalScope.launch {
            delay(3000)
            goToHome()
        }

    }

    private fun goToHome() {
        var mIntent = Intent(this, HomeActivity::class.java)
        mIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
    }
}