package ve.com.cblanco1989.features

import android.Manifest
import android.annotation.TargetApi
import android.app.AppOpsManager
import android.app.AppOpsManager.OnOpChangedListener
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ve.com.cblanco1989.R
import ve.com.cblanco1989.features.accelerometertest.AcceActivity
import ve.com.cblanco1989.features.datameasure.ui.list.AppListActivity
import ve.com.cblanco1989.features.home.ui.activity.HomeActivity


class SplashActivty : AppCompatActivity() {

    var networkStatsManager: NetworkStatsManager? = null
    private val MY_PERMISSIONS_REQUEST = 101


    var packageUid = "pe.com.interbank.mobilebanking"
    var TAG_SPLASH = "TAG_SPLASH"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activty)

        HiAnalyticsTools.enableLog()
        val instance: HiAnalyticsInstance = HiAnalytics.getInstance(this)
        instance.setUserProfile("userKey", "miKeyCarlos")

        val bundle = Bundle()
        bundle.putString("exam_difficulty", "high")
        bundle.putString("exam_level", "1-1")
        bundle.putString("exam_time", "20190520-08")
        instance.onEvent("begin_examination", bundle)

        /*if(hasPermissionToReadNetworkHistory()){
            requestPermission()
        }
         */
        goAccelerometerTest()
    }

    private fun hasPermissionToReadNetworkHistory(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        val appOps =
            getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true
        }
        appOps.startWatchingMode(AppOpsManager.OPSTR_GET_USAGE_STATS,
            applicationContext.packageName,
            object : OnOpChangedListener {
                @TargetApi(Build.VERSION_CODES.M)
                override fun onOpChanged(op: String, packageName: String) {
                    val mode = appOps.checkOpNoThrow(
                        AppOpsManager.OPSTR_GET_USAGE_STATS,
                        Process.myUid(), getPackageName()
                    )
                    if (mode != AppOpsManager.MODE_ALLOWED) {
                        return
                    }
                    appOps.stopWatchingMode(this)
                    val intent = Intent(
                        this@SplashActivty,
                        SplashActivty::class.java
                    )
                    if (getIntent().extras != null) {
                        intent.putExtras(getIntent().extras!!)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    applicationContext.startActivity(intent)
                }
            })
        requestReadNetworkHistoryAccess()
        return false
    }

    private fun requestReadNetworkHistoryAccess() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }

    private fun goAccelerometerTest() {
        var mIntent = Intent(this, AcceActivity::class.java)
        mIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
    }

    private fun goToHome() {
        var mIntent = Intent(this, HomeActivity::class.java)
        mIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
    }

    private fun goToAppList() {
        var mIntent = Intent(this, AppListActivity::class.java)
        mIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mIntent)
    }

    private fun checkPermissionUsageStats():Boolean{
        val appOps =
            getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true
        }
        return false
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.PACKAGE_USAGE_STATS
            ) !== PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.PACKAGE_USAGE_STATS,
                    Manifest.permission.READ_PHONE_STATE
                ),
                MY_PERMISSIONS_REQUEST
            )
        }else{
            goToAppList()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    goToAppList()
                }
                return
            }
        }
    }

}