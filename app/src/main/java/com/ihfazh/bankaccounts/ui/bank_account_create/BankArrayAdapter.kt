package com.ihfazh.bankaccounts.ui.bank_account_create

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.ihfazh.bankaccounts.core.domain.data.Bank

class BankArrayAdapter(
    context: Context,
    @LayoutRes val resource: Int,
    private var objects: List<Bank>
) : ArrayAdapter<Bank>(context, resource, objects) {
    private var originalObjects: List<Bank?> = objects
    private val mFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            println("BankArrayAdapter.performFiltering")
            val filterResult = FilterResults()
            val tempList: List<Bank?>

            if (constraint != null && !objects.isNullOrEmpty()) {
                tempList = originalObjects.filter {
                    it?.name?.contains(constraint.toString(), true) == true
                }
                filterResult.values = tempList
                filterResult.count = tempList.size
            }
            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values != null) {
                objects = results.values as List<Bank>
            } else {
                objects = originalObjects as List<Bank>
            }
            if (results != null && results.count > 0) notifyDataSetChanged() else notifyDataSetInvalidated()
        }
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    override fun getCount(): Int {
        return objects?.size ?: 0
    }

    override fun getItem(position: Int): Bank? {
        return objects.getOrNull(position)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = createViewFromResource(convertView, parent, resource) as TextView
        return bindView(getItem(position), view)
    }

    private fun bindView(item: Bank?, view: TextView): View {
        view.text = item?.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = createViewFromResource(convertView, parent, resource) as TextView
        return bindView(getItem(position), view)
    }

    private fun createViewFromResource(
        convertView: View?, parent: ViewGroup, resource: Int
    ): View {
        return convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
    }


}