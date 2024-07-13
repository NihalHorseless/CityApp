package com.example.weatherappfragment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.model.Comment
import com.example.weatherappfragment.util.OnCommentDeleteListener
import com.google.firebase.auth.FirebaseAuth
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class CommentAdapter(
    private var comments: List<Comment>,
    private val onCommentDeleteListener: OnCommentDeleteListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        val comment = comments[position]
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.email ?: ""
        return if (comment.senderID == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View
        when (viewType) {
            CommentAdapter.VIEW_TYPE_SENT -> {
                view = inflater.inflate(R.layout.comment_my_item_layout, parent, false)
                return SentCommentViewHolder(view)
            }

            CommentAdapter.VIEW_TYPE_RECEIVED -> {
                view = inflater.inflate(R.layout.comment_item_layout, parent, false)
                return ReceivedCommentViewHolder(view)
            }

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = comments[position]
        when (holder) {
            is CommentAdapter.SentCommentViewHolder -> {
                holder.usernameTextView.text = "You"
                holder.contentTextView.text = comment.content
                holder.timestampTextView.text = formatTimestamp(comment.timestamp)
            }

            is CommentAdapter.ReceivedCommentViewHolder -> {
                holder.usernameTextView.text = comment.senderID
                holder.contentTextView.text = comment.content
                holder.timestampTextView.text = formatTimestamp(comment.timestamp)

                // ... set timestamp
            }

            else -> throw IllegalArgumentException("Invalid holder type")
        }
    }

    override fun getItemCount() = comments.size

    private fun formatTimestamp(timestampInMilliseconds: Long): String {
        val instant = Instant.ofEpochMilli(timestampInMilliseconds)
        val zoneId = ZoneId.systemDefault() // Adjust if needed for specific time zone
        val localDateTime = instant.atZone(zoneId).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return formatter.format(localDateTime)
    }

    fun setData(newComments: List<Comment>) {
        comments = newComments
        notifyDataSetChanged()
    }

    inner class SentCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.comment_my_name)
        val contentTextView: TextView = itemView.findViewById(R.id.comment_my_content)
        val timestampTextView: TextView = itemView.findViewById(R.id.comment_my_date)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.comment_my_deleteBtn)

        init {
            deleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val comment = comments[position]
                    onCommentDeleteListener.onCommentDelete(comment) // Call the interface method
                    comments.toMutableList().removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    inner class ReceivedCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.comment_item_user)
        val contentTextView: TextView = itemView.findViewById(R.id.comment_item_content)
        val timestampTextView: TextView = itemView.findViewById(R.id.comment_item_time)

    }
}
