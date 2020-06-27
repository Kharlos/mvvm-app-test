package ve.com.cblanco1989.features.datameasure.ui.list

import android.Manifest
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hms.utils.PackageManagerHelper
import kotlinx.android.synthetic.main.content_app_list.*
import ve.com.cblanco1989.R
import ve.com.cblanco1989.common.others.NetworkStatsHelper
import ve.com.cblanco1989.common.others.NetworkStatsHelper.Companion.GET_RX
import ve.com.cblanco1989.common.others.NetworkStatsHelper.Companion.GET_TX
import ve.com.cblanco1989.common.others.NetworkStatsHelper.Companion.SOURCE_TYPE_MOBILE
import ve.com.cblanco1989.common.others.NetworkStatsHelper.Companion.SOURCE_TYPE_WIFI
import ve.com.cblanco1989.common.others.getFileSize
import ve.com.cblanco1989.common.others.getPackageUid
import ve.com.cblanco1989.features.datameasure.data.AppPackage
import ve.com.cblanco1989.features.datameasure.ui.adapter.PackageAdapter
import java.util.*

class AppListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        setupUI()

    }

    var networkStatsHelper: NetworkStatsHelper? = null

    fun emitLog(msg:String = ""){
        Log.d("DATADEBUG", "MSG = $msg")
    }

    private fun setupUI() {
        rv?.apply {
            layoutManager = LinearLayoutManager(this@AppListActivity)
            adapter =
                PackageAdapter(getPackagesData(), object : PackageAdapter.OnPackageClickListener {
                    override fun onClick(packageItem: AppPackage?) {
                        val uid: Int = getPackageUid(this@AppListActivity, packageItem?.packageName)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            val networkStatsManager =
                                applicationContext.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
                            val networkStatsHelper = NetworkStatsHelper(networkStatsManager, uid, this@AppListActivity)
                            emitLog("ALL ${packageItem?.name}")

                            emitLog("SOURCE_TYPE_MOBILE GET_TX : ${getFileSize(networkStatsHelper.getAllDataBytes(GET_TX, SOURCE_TYPE_MOBILE))}")
                            emitLog("SOURCE_TYPE_MOBILE GET_RX : ${getFileSize(networkStatsHelper.getAllDataBytes(GET_RX, SOURCE_TYPE_MOBILE))}")
                            emitLog("*******************************************************")
                            emitLog("SOURCE_TYPE_WIFI GET_RX : ${getFileSize(networkStatsHelper.getAllDataBytes(GET_RX, SOURCE_TYPE_WIFI))}")
                            emitLog("SOURCE_TYPE_WIFI GET_TX : ${getFileSize(networkStatsHelper.getAllDataBytes(GET_TX, SOURCE_TYPE_WIFI))}")
                            emitLog("BY PACKAGE")

                            emitLog("SOURCE_TYPE_MOBILE GET_TX : ${getFileSize(networkStatsHelper.getPackageDataBytes(GET_TX, SOURCE_TYPE_MOBILE))}")
                            emitLog("SOURCE_TYPE_MOBILE GET_RX : ${getFileSize(networkStatsHelper.getPackageDataBytes(GET_RX, SOURCE_TYPE_MOBILE))}")
                            emitLog("*******************************************************")
                            emitLog("SOURCE_TYPE_WIFI GET_TX : ${getFileSize(networkStatsHelper.getPackageDataBytes(GET_TX, SOURCE_TYPE_WIFI))}")
                            emitLog("SOURCE_TYPE_WIFI GET_RX : ${getFileSize(networkStatsHelper.getPackageDataBytes(GET_RX, SOURCE_TYPE_WIFI))}")

                            emitLog("*******************************************************")
                        }
                    }
                })
        }

    }

    private fun getPackagesData(): MutableList<AppPackage> {
        val packageManager = packageManager
        val packageInfoList =
            packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        packageInfoList.sortWith(Comparator { o1, o2 -> ((o2.lastUpdateTime - o1.lastUpdateTime) / 10).toInt() })
        val packageList: MutableList<AppPackage> =
            ArrayList(packageInfoList.size)
        for (packageInfo in packageInfoList) {
            if (packageManager.checkPermission(
                    Manifest.permission.INTERNET,
                    packageInfo.packageName
                ) == PackageManager.PERMISSION_DENIED
            ) {
                continue
            }
            val appPackage = AppPackage()
            appPackage.version = packageInfo.versionName
            appPackage.packageName = packageInfo.packageName
            packageList.add(appPackage)
            var ai: ApplicationInfo? = null
            try {
                ai = packageManager.getApplicationInfo(
                    packageInfo.packageName,
                    PackageManager.GET_META_DATA
                )
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            if (ai == null) {
                continue
            }
            val appName = packageManager.getApplicationLabel(ai)
            if (appName != null) {
                appPackage.name = (appName ?: "") as String?
            }
        }
        return packageList
    }
}