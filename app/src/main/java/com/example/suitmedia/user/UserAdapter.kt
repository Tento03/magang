package com.example.suitmedia.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmedia.api.User
import com.example.suitmedia.databinding.ItemUserBinding


class UserAdapter(private val dataSet: List<User>,private var setonClickListener: onClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.ivAvatar)
            binding.tvFirstName.text=user.first_name
            binding.tvLastName.text=user.last_name
            binding.tvEmail.text=user.email

            binding.root.setOnClickListener(){
                var position=bindingAdapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    var user=dataSet[position]
                    setonClickListener.onClickListener(user)
                }
            }
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.textView.text = dataSet[position]
        var user=dataSet[position]
        viewHolder.bind(user)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    interface onClickListener{
        fun onClickListener(user: User)
    }

}
