package ve.com.cblanco1989.common.others

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.text.DecimalFormat


fun getSubscriberId(context: Context, networkType: Int): String? {
    if (ConnectivityManager.TYPE_MOBILE == networkType) {
        val tm =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return ""
        }


        return tm.subscriberId
    }
    return ""
}

fun getFileSize(size: Long): String? {
    if (size <= 0) return "0"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups =
        (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#")
        .format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
}

fun isPackage(context: Context, s: CharSequence): Boolean {
    val packageManager = context.packageManager
    try {
        packageManager.getPackageInfo(s.toString(), PackageManager.GET_META_DATA)
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
    return true
}

fun getPackageUid(context: Context, packageName: String?): Int {
    val packageManager = context.packageManager
    var uid = -1
    try {
        val packageInfo =
            packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA)
        uid = packageInfo.applicationInfo.uid
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return uid
}