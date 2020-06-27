package ve.com.cblanco1989.features.datameasure.ui.adapter

import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ve.com.cblanco1989.R
import ve.com.cblanco1989.features.datameasure.data.AppPackage

class PackageAdapter(
    var mPackageList: List<AppPackage?>,
    var mListener: OnPackageClickListener? = null) :
    RecyclerView.Adapter<PackageAdapter.PackageViewHolder>() {

    interface OnPackageClickListener {
        fun onClick(packageItem: AppPackage?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)


        return PackageViewHolder(view)
    }

    override fun getItemCount(): Int = mPackageList.size

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {

        mPackageList[position]?.let {packageItem->
            holder.name.text = packageItem.name
            holder.packageName.text = packageItem.name
            holder.version.text = packageItem.version
            try {
                holder.icon.setImageDrawable(
                    holder.context.packageManager.getApplicationIcon(packageItem.packageName)
                )
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            holder.card.setOnClickListener {
                mListener?.onClick(packageItem)
            }
        }

    }


    class PackageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var context: Context
        var card: View
        var name: TextView
        var packageName: TextView
        var version: TextView
        var icon: AppCompatImageView

        init {
            context = itemView.context
            card = itemView.findViewById<View>(R.id.card) as CardView
            name = itemView.findViewById<View>(R.id.name) as TextView
            packageName = itemView.findViewById<View>(R.id.package_name) as TextView
            version = itemView.findViewById<View>(R.id.version) as TextView
            icon = itemView.findViewById<View>(R.id.icon) as AppCompatImageView
        }
    }

}