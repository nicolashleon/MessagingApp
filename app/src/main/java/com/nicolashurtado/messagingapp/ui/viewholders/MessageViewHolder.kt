package com.nicolashurtado.messagingapp.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicolashurtado.messagingapp.R
import com.nicolashurtado.messagingapp.db.entities.Attachment
import com.nicolashurtado.messagingapp.db.entities.Publication
import com.squareup.picasso.Picasso

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    protected val userNameTextView by lazy { itemView.findViewById<TextView>(R.id.text_view_user_name) }
    protected val contentTextView by lazy { itemView.findViewById<TextView>(R.id.text_view_content) }
    protected val attachmentsLayout by lazy { itemView.findViewById<LinearLayout>(R.id.layout_attachments) }

    open fun bind(publication: Publication) {
        userNameTextView.text = itemView.context.getString(R.string.txt_me)
        contentTextView.text = publication.message.content
        if (publication.attachments.isEmpty()) {
            attachmentsLayout.removeAllViews()
            attachmentsLayout.visibility = View.GONE
        } else {
            attachmentsLayout.visibility = View.VISIBLE
            publication.attachments.forEach {
                bindAttachments(it)
            }
        }
    }

    private fun bindAttachments(attachment: Attachment) {
        val attachmentView = LayoutInflater.from(attachmentsLayout.context).inflate(R.layout.item_attachment, attachmentsLayout, false)
        val attachmentImageView = attachmentView.findViewById<ImageView>(R.id.image_view_attachment_thumbnail)
        val attachmentNameTextView = attachmentView.findViewById<TextView>(R.id.text_view_attachment_name)

        attachmentNameTextView.text = attachment.title
        if (attachment.thumbnailUrl.isNotEmpty()) {
            Picasso.get().load(attachment.thumbnailUrl)
                    .placeholder(R.drawable.ic_attachment)
                    .error(R.drawable.ic_attachment)
                    .into(attachmentImageView)
        } else {
            attachmentImageView.setImageResource(R.drawable.ic_attachment)
        }
        attachmentsLayout.addView(attachmentView)
    }
}