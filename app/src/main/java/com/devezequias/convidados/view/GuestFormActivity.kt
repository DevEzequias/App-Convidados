package com.devezequias.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devezequias.convidados.viewmodel.GuestFormViewModel
import com.devezequias.convidados.databinding.ActivityGuestFormBinding
import com.devezequias.convidados.service.constants.GuestConstants
import com.devezequias.convidados.view.listener.GuestListener

class GuestFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener {
                setListeners()
        }

        observe()
        loadData()

        binding.radioPresence.isChecked = true
    }

    private fun setListeners() {
        val name = binding.editName.text.toString()
        val presence = binding.radioPresence.isChecked

        mViewModel.save(mGuestId, name, presence)
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        }
    }

    private fun observe(){

        mViewModel.saveGuest.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresence.isChecked = true
            }else {
                binding.radioAbsent.isChecked = true
            }
        })
    }


}


