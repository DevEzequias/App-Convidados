package com.devezequias.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devezequias.convidados.databinding.FragmentAllBinding
import com.devezequias.convidados.databinding.FragmentPresentBinding
import com.devezequias.convidados.service.constants.GuestConstants
import com.devezequias.convidados.view.adapter.GuestAdapter
import com.devezequias.convidados.view.listener.GuestListener
import com.devezequias.convidados.viewmodel.GuestViewModel

class PresentFragment : Fragment() {

    private lateinit var mViewModel: GuestViewModel
    private var _binding: FragmentPresentBinding? = null
    private val mAdapter: GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        mViewModel =
            ViewModelProvider(this).get(GuestViewModel::class.java)

        _binding = FragmentPresentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //RecyclerView
        val recycler = binding.recyclerPresents
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.PRESENT)
            }
        }

        mAdapter.attachListener(mListener)
        observer()

        return root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.PRESENT)
    }

    private fun observer() {
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}