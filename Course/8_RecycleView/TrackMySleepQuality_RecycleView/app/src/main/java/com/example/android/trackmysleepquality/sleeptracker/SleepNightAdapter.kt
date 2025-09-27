package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import com.example.android.trackmysleepquality.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class SleepNightAdapter(val clickListener: SleepNightListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback())  {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map  { DataItem.SleepNightItem(it) }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}

sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
        override val id = sleepNight.nightId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}

/** Refactor: 6 **/

//class SleepNightAdapter(val clickListener: SleepNightListener): ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback())  {
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position)!!, clickListener)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder.from(parent)
//    }
//
//    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
//
//                return ViewHolder(binding)
//            }
//        }
//
//        fun bind(item: SleepNight, clickListener: SleepNightListener) {
//            binding.sleep = item
//            binding.clickListener = clickListener
//            binding.executePendingBindings()
//        }
//    }
//}
//
//class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
//    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean =
//        oldItem.nightId == newItem.nightId
//
//    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean =
//        oldItem == newItem
//}

/** Refactor: 5 **/

//class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback())  {
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder.from(parent)
//    }
//
//    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
//
//                return ViewHolder(binding)
//            }
//        }
//
//        fun bind(item: SleepNight) {
//            binding.sleep = item
//            binding.executePendingBindings()
//        }
//    }
//}

/** Refactor: 4 **/

//class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback())  {
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder.from(parent)
//    }
//
//    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {
//        val sleepLength: TextView = binding.sleepLength
//        val quality: TextView = binding.qualityString
//        val qualityImage: ImageView = binding.qualityImage
//
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
//
//                return ViewHolder(binding)
//            }
//        }
//
//        fun bind(item: SleepNight) {
//            val res = itemView.context.resources
//            binding.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
//
//            binding.qualityImage.setImageResource(when (item.sleepQuality) {
//                0 -> R.drawable.ic_sleep_0
//                1 -> R.drawable.ic_sleep_1
//                2 -> R.drawable.ic_sleep_2
//                3 -> R.drawable.ic_sleep_3
//                4 -> R.drawable.ic_sleep_4
//                5 -> R.drawable.ic_sleep_5
//                else -> R.drawable.ic_sleep_active
//            })
//        }
//    }
//}

/** Refactor: 3 **/

//class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback())  {
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(holder, item)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder.from(parent)
//    }
//
//    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val sleepQuality: TextView = itemView.findViewById(R.id.sleep_length)
//        val quality: TextView = itemView.findViewById(R.id.quality_string)
//        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
//
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater
//                    .inflate(R.layout.list_item_sleep_night, parent, false)
//
//                return ViewHolder(view)
//            }
//        }
//
//        fun bind(
//            holder: ViewHolder,
//            item: SleepNight
//        ) {
//            val res = itemView.context.resources
//            holder.sleepQuality.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//            holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
//
//            holder.qualityImage.setImageResource(
//                when (item.sleepQuality) {
//                    0 -> R.drawable.ic_sleep_0
//                    1 -> R.drawable.ic_sleep_1
//                    2 -> R.drawable.ic_sleep_2
//                    3 -> R.drawable.ic_sleep_3
//                    4 -> R.drawable.ic_sleep_4
//                    5 -> R.drawable.ic_sleep_5
//                    else -> R.drawable.ic_sleep_active
//                }
//            )
//        }
//    }
//}

/** Refactor: 2 **/

//class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>()  {
//    private val differ = AsyncListDiffer(this, SleepNightDiffCallback())
//    var data: List<SleepNight>
//        get() = differ.currentList
//        set(value) = differ.submitList(value)
//
//    override fun getItemCount() = data.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]
//        holder.bind(holder, item)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder.from(parent)
//    }
//
//    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val sleepQuality: TextView = itemView.findViewById(R.id.sleep_length)
//        val quality: TextView = itemView.findViewById(R.id.quality_string)
//        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
//
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater
//                    .inflate(R.layout.list_item_sleep_night, parent, false)
//
//                return ViewHolder(view)
//            }
//        }
//
//        fun bind(
//            holder: ViewHolder,
//            item: SleepNight
//        ) {
//            val res = itemView.context.resources
//            holder.sleepQuality.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//            holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
//
//            holder.qualityImage.setImageResource(
//                when (item.sleepQuality) {
//                    0 -> R.drawable.ic_sleep_0
//                    1 -> R.drawable.ic_sleep_1
//                    2 -> R.drawable.ic_sleep_2
//                    3 -> R.drawable.ic_sleep_3
//                    4 -> R.drawable.ic_sleep_4
//                    5 -> R.drawable.ic_sleep_5
//                    else -> R.drawable.ic_sleep_active
//                }
//            )
//        }
//    }
//}

/** Refactor: 1 **/

//class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>()  {
//    private val differ = AsyncListDiffer(this, SleepNightDiffCallback())
//    var data: List<SleepNight>
//        get() = differ.currentList
//        set(value) = differ.submitList(value)
//
//    override fun getItemCount() = data.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]
//        val res = holder.itemView.context.resources
//        holder.sleepQuality.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//        holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
//
//        holder.qualityImage.setImageResource(when (item.sleepQuality) {
//            0 -> R.drawable.ic_sleep_0
//            1 -> R.drawable.ic_sleep_1
//            2 -> R.drawable.ic_sleep_2
//            3 -> R.drawable.ic_sleep_3
//            4 -> R.drawable.ic_sleep_4
//            5 -> R.drawable.ic_sleep_5
//            else -> R.drawable.ic_sleep_active
//        })
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
//        return ViewHolder(view)
//    }
//
//    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val sleepQuality: TextView = itemView.findViewById(R.id.sleep_length)
//        val quality: TextView = itemView.findViewById(R.id.quality_string)
//        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
//    }
//}

/** Refactor: 0 **/

//class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>()  {
//    private val differ = AsyncListDiffer(this, SleepNightDiffCallback())
//    var data: List<SleepNight>
//        get() = differ.currentList
//        set(value) = differ.submitList(value)
//
//    override fun getItemCount() = data.size
//
//    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
//        val item = data[position]
//
//        if (item.sleepQuality <= 1) {
//            holder.textView.setTextColor(Color.RED)
//        }
//        else {
//            holder.textView.setTextColor(Color.BLACK)
//        }
//
//        holder.textView.text = item.sleepQuality.toString()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
//        return TextItemViewHolder(view)
//    }
//}