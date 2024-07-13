package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.repository.ChatCommentRepository
import com.example.weatherappfragment.ui.adapter.ChatMessageAdapter
import com.example.weatherappfragment.ui.adapter.FavoriteCitiesAdapter
import com.example.weatherappfragment.ui.viewModel.ChatViewModel
import com.example.weatherappfragment.ui.viewModel.UserLoginViewModel
import com.example.weatherappfragment.ui.viewModel.factory.ChatViewModelFactory
import com.example.weatherappfragment.ui.viewModel.factory.UserLoginViewModelFactory
import com.google.firebase.database.FirebaseDatabase


class A52CityChatScreen : Fragment() {
    private lateinit var viewModel: ChatViewModel

    private lateinit var messageList: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button

    private lateinit var cityNameLabel: TextView

    val args: A52CityChatScreenArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a5_2_city_chat_screen, container, false)

        messageList = view.findViewById(R.id.city_chat_messageList)
        messageInput = view.findViewById(R.id.city_chat_messageInput)
        sendButton = view.findViewById(R.id.city_chat_messageBtn)
        cityNameLabel = view.findViewById(R.id.city_chat_cityName)

        // Get city ID from arguments or another source
        val cityId = args.cityName ?: ""
        cityNameLabel.text = cityId


        // Inject ViewModel with repository and city ID
        val viewModelFactory = ChatViewModelFactory(
            ChatCommentRepository(FirebaseDatabase.getInstance("https://city-chat-9d109-default-rtdb.europe-west1.firebasedatabase.app")),
            cityId
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java]
        Log.d("ChatFragment", viewModel.messages.value.toString())

        val layoutManager = LinearLayoutManager(context)
        val adapter = ChatMessageAdapter(viewModel.messages.value ?: emptyList())
        messageList.layoutManager = layoutManager
        messageList.adapter = adapter

        messageList.adapter = ChatMessageAdapter(viewModel.messages.value ?: emptyList())

        // Observe messages LiveData and update UI
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            val adapter = ChatMessageAdapter(messages)
            messageList.layoutManager = layoutManager
            messageList.adapter = adapter
            adapter.setData(messages) // Set initial data
            adapter.notifyDataSetChanged() // Notify adapter of data change
        }

        // Send message button click listener
        sendButton.setOnClickListener {
            val messageContent = messageInput.text.toString().trim()
            if (messageContent.isNotEmpty()) {
                viewModel.postChatMessage(messageContent)
                messageInput.text.clear() // Clear input field after sending
            }
        }

        return view
    }
}