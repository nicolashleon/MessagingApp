package com.nicolashurtado.messagingapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.nicolashurtado.messagingapp.R
import com.nicolashurtado.messagingapp.db.entities.Publication
import com.nicolashurtado.messagingapp.ui.viewholders.ExternalMessageViewHolder
import com.nicolashurtado.messagingapp.ui.viewholders.MessageViewHolder

class MessageAdapter(diffUtil: PublicationDiffUtil) : PagedListAdapter<Publication, MessageViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_external_message -> ExternalMessageViewHolder(inflater.inflate(viewType, parent, false))
            R.layout.item_message -> MessageViewHolder(inflater.inflate(viewType, parent, false))
            else -> MessageViewHolder(inflater.inflate(R.layout.item_message, parent, false))
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val publication = getItem(position)
        if (publication != null) {
            holder.bind(publication)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.message?.id?.toLong() ?: super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)?.message
        return if (message != null) {
            if (message.isExternal()) R.layout.item_external_message else R.layout.item_message
        } else {
            super.getItemViewType(position)
        }
    }

}