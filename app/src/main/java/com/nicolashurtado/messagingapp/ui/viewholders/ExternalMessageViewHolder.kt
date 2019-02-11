package com.nicolashurtado.messagingapp.ui.viewholders

import android.view.View
import android.widget.ImageView
import com.nicolashurtado.messagingapp.R
import com.nicolashurtado.messagingapp.db.entities.Publication
import com.nicolashurtado.messagingapp.ui.CircleTransformation
import com.squareup.picasso.Picasso

class ExternalMessageViewHolder(view: View, listener: OnMessageLongClickListener) :
        MessageViewHolder(view, listener) {

    private val userImageView by lazy { itemView.findViewById<ImageView>(R.id.image_view_user) }

    override fun bind(publication: Publication) {
        super.bind(publication)
        userNameTextView.text = publication.user.name

        if(!publication.user.avatarId.isEmpty()) {
            Picasso.get().load(publication.user.avatarId)
                .transform(CircleTransformation())
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(userImageView)
        } else {
            userImageView.setImageResource(R.drawable.ic_person)
        }
    }
}