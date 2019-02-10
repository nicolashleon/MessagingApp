package com.nicolashurtado.messagingapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nicolashurtado.messagingapp.BuildConfig
import com.nicolashurtado.messagingapp.R
import com.nicolashurtado.messagingapp.ui.viewmodels.MessageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagingActivity : AppCompatActivity() {

    private val viewModel: MessageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)
        viewModel.loadData(BuildConfig.DATA_SEED_FILE_NAME)
        viewModel.getPublications().observe(this, Observer { publications ->
            print("MESSAGES $publications.size")
        })
    }

}
