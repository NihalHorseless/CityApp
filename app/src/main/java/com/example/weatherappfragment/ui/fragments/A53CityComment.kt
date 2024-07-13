package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.model.Comment
import com.example.weatherappfragment.repository.ChatCommentRepository
import com.example.weatherappfragment.ui.adapter.CommentAdapter
import com.example.weatherappfragment.ui.viewModel.CommentViewModel
import com.example.weatherappfragment.ui.viewModel.factory.CommentViewModelFactory
import com.example.weatherappfragment.util.OnCommentDeleteListener
import com.google.firebase.database.FirebaseDatabase


class A53CityComment : Fragment(), OnCommentDeleteListener {
    private lateinit var viewModel: CommentViewModel

    private lateinit var commentList: RecyclerView
    private lateinit var commentInput: EditText
    private lateinit var sendButton: Button

    private lateinit var cityNameLabel: TextView

    val args: A53CityCommentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a5_3_city_comment, container, false)

        commentList = view.findViewById(R.id.city_comment_commentList)
        commentInput = view.findViewById(R.id.city_comment_inputTxt)
        sendButton = view.findViewById(R.id.city_comment_sendBtn)
        cityNameLabel = view.findViewById(R.id.city_comment_cityName)

        // Get city ID from arguments or another source
        val cityId = args.cityName ?: ""
        cityNameLabel.text = cityId + " Reviews"

        // Inject ViewModel with repository and city ID
        val viewModelFactory = CommentViewModelFactory(
            ChatCommentRepository(FirebaseDatabase.getInstance("https://city-chat-9d109-default-rtdb.europe-west1.firebasedatabase.app")),
            cityId
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[CommentViewModel::class.java]
        val layoutManager = LinearLayoutManager(context)
        val adapter = CommentAdapter(viewModel.comments.value ?: emptyList(), this)
        commentList.layoutManager = layoutManager
        commentList.adapter = adapter

        commentList.adapter = CommentAdapter(viewModel.comments.value ?: emptyList(), this)
        // Observe comments LiveData and update UI
        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            val adapter = CommentAdapter(comments, this)
            commentList.layoutManager = layoutManager
            commentList.adapter = adapter
            adapter.setData(comments) // Set initial data
            adapter.notifyDataSetChanged() // Notify adapter of data change
        }

        // Send comment button click listener
        sendButton.setOnClickListener {
            val commentContent = commentInput.text.toString().trim()
            if (commentContent.isNotEmpty()) {
                viewModel.postComment(commentContent)
                commentInput.text.clear() // Clear input field after sending
            }
        }

        return view
    }

    override fun onCommentDelete(comment: Comment) {
        viewModel.deleteComment(comment)
    }
}