package com.example.weatherappfragment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatMessageAdapter(private var messages: List<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.email ?: ""
        return if (message.senderID == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View
        when (viewType) {
            VIEW_TYPE_SENT -> {
                view = inflater.inflate(R.layout.chat_own_layout, parent, false)
                return SentMessageViewHolder(view)
            }
            VIEW_TYPE_RECEIVED -> {
                view = inflater.inflate(R.layout.chat_others_layout, parent, false)
                return ReceivedMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is SentMessageViewHolder -> {
                holder.usernameTextView.text = "You"
                holder.contentTextView.text = message.content
                holder.timestampTextView.text = formatTimestamp(message.timestamp)
            }
            is ReceivedMessageViewHolder -> {
                holder.usernameTextView.text = message.senderID
                holder.contentTextView.text = message.content
                holder.timestampTextView.text = formatTimestamp(message.timestamp)

                // ... set timestamp
            }
            else -> throw IllegalArgumentException("Invalid holder type")
        }
    }

    override fun getItemCount() = messages.size

    private fun formatTimestamp(timestampInMilliseconds: Long): String {
        val instant = Instant.ofEpochMilli(timestampInMilliseconds)
        val zoneId = ZoneId.systemDefault() // Adjust if needed for specific time zone
        val localDateTime = instant.atZone(zoneId).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return formatter.format(localDateTime)
    }
    fun setData(newMessages: List<ChatMessage>) {
        messages = newMessages
        notifyDataSetChanged()
    }
    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.chat_own_mail)
        val contentTextView: TextView = itemView.findViewById(R.id.chat_own_content)
        val timestampTextView: TextView = itemView.findViewById(R.id.chat_own_time)
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.chat_others_mail)
        val contentTextView: TextView = itemView.findViewById(R.id.chat_others_content)
        val timestampTextView: TextView = itemView.findViewById(R.id.chat_others_time)

    }
}
