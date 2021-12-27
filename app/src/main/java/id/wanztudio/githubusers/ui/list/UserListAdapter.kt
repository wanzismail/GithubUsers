package id.wanztudio.githubusers.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.wanztudio.githubusers.databinding.UserListItemBinding
import id.wanztudio.githubusers.extensions.layoutInflater
import id.wanztudio.githubusers.extensions.load
import id.wanztudio.githubusers.repository.model.UserResult

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserListAdapter(
    private var userList: MutableList<UserResult> = arrayListOf()
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var callback: UserListCallback? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val view = UserListItemBinding.inflate(
            parent.context.layoutInflater,
            parent,
            false
        )

        return ViewHolder(view).apply {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback?.onItemTapped(
                        userList[adapterPosition].username ?: ""
                    )
                }
            }
        }

    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        val item = userList[position]
        holder.bindItem(item)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(countries: List<UserResult>) {
        userList.clear()
        userList.addAll(countries)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: UserListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: UserResult) {
            binding.textUsername.text = item.username
            binding.imageProfile.load(item.avatarUrl)
        }
    }
}