package com.nicolashurtado.messagingapp.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.nicolashurtado.messagingapp.db.entities.Publication

class PublicationDiffUtil : DiffUtil.ItemCallback<Publication>() {
    override fun areItemsTheSame(oldItem: Publication, newItem: Publication): Boolean {
        return oldItem.message.id == newItem.message.id
    }

    override fun areContentsTheSame(oldItem: Publication, newItem: Publication): Boolean {
        return oldItem == newItem
    }
}