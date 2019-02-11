package com.nicolashurtado.messagingapp.ui.activities

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicolashurtado.messagingapp.BuildConfig.DATA_SEED_FILE_NAME
import com.nicolashurtado.messagingapp.R
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Message
import com.nicolashurtado.messagingapp.ui.adapters.MessageAdapter
import com.nicolashurtado.messagingapp.ui.adapters.PublicationDiffUtil
import com.nicolashurtado.messagingapp.ui.viewmodels.MessageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagingActivity : AppCompatActivity(), MessageAdapter.OnMessageLongClickListener {

    private val viewModel: MessageViewModel by viewModel()
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        title = getString(R.string.activity_messages_label)

        //TODO Move data loading outside the activity to avoid issues when the screen gets recreated.
        viewModel.loadData(DATA_SEED_FILE_NAME)
        val adapter = MessageAdapter(PublicationDiffUtil(), this).apply {
            setHasStableIds(true)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getPublications().observe(this, Observer { publications ->
            adapter.submitList(publications)
        })
    }

    override fun onAttachmentClicked(attachment: Attachment) {
        AlertDialog.Builder(this)
                .setTitle(R.string.txt_title_delete_attachment_confirmation)
                .setCancelable(true)
                .setPositiveButton(R.string.txt_delete_button) { _, _ ->
                    viewModel.deleteAttachment(attachment)
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }

    override fun onMessageClicked(message: Message) {
        AlertDialog.Builder(this)
                .setTitle(R.string.txt_title_delete_message_confirmation)
                .setCancelable(true)
                .setPositiveButton(R.string.txt_delete_button) { _, _ ->
                    viewModel.deleteMessage(message)
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }
}
