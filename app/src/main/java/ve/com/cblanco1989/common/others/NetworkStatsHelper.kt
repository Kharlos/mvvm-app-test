package ve.com.cblanco1989.common.others

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.RemoteException

class NetworkStatsHelper(var networkStatsManager: NetworkStatsManager, var packageUid:Int, var context: Context){

    companion object{
        const val GET_TX = "TX"
        const val GET_RX = "RX"

        const val SOURCE_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE
        const val SOURCE_TYPE_WIFI = ConnectivityManager.TYPE_WIFI
    }


    fun getAllDataBytes(dataType:String, sourceType:Int): Long {
        val bucket: NetworkStats.Bucket = try {
            networkStatsManager.querySummaryForDevice(
                sourceType,
                getSubscriberId(context, sourceType),
                0,
                System.currentTimeMillis()
            )
        } catch (e: RemoteException) {
            return -1
        }
        return when(dataType){
            GET_TX->{
                bucket.txBytes
            }
            else->{
                bucket.rxBytes
            }
        }
    }


    fun getPackageDataBytes(dataType:String, sourceType:Int): Long {
        var networkStats: NetworkStats? = null
        networkStats = try {
            networkStatsManager.queryDetailsForUid(
                sourceType,
                getSubscriberId(context, sourceType),
                0,
                System.currentTimeMillis(),
                packageUid
            )
        } catch (e: RemoteException) {
            return -1
        }
        var rxBytes = 0L
        val bucket = NetworkStats.Bucket()

        while (networkStats.hasNextBucket()) {
            networkStats.getNextBucket(bucket)
            rxBytes += when(dataType){
                GET_TX->{
                    bucket.txBytes
                }
                else->{
                    bucket.rxBytes
                }
            }

        }
        networkStats.close()


        return rxBytes
    }


}