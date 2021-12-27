package id.wanztudio.githubusers.ui.list.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.wanztudio.githubusers.databinding.UserListItemBinding
import id.wanztudio.githubusers.extensions.layoutInflater
import id.wanztudio.githubusers.extensions.load
import id.wanztudio.githubusers.repository.model.UserFavorite

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserFavoritesAdapter(
    private var userList: MutableList<UserFavorite> = arrayListOf()
) : RecyclerView.Adapter<UserFavoritesAdapter.ViewHolder>() {

    var callback: UserFavoritesCallback? = null

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
                        userList[adapterPosition].username ?: "",
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

    fun updateList(countries: List<UserFavorite>) {
        userList.clear()
        userList.addAll(countries)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: UserListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: UserFavorite) {
            binding.textUsername.text = item.username
            binding.imageProfile.load(item.avatarUrl)
        }
    }
}