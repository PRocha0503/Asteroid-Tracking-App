package com.example.asteroidradar.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentMainBinding
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Inflate with binding
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Create the adapter end lamda for each item
        val adapter = AsteroidAdapter(AsteroidListener {
                asteroid -> viewModel.navigateToDetails(asteroid)
        })
        //Setup the recycler view
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        //Update the recyclerview with new list
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        //Observe for button clicks
        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer { asteroid -> asteroid.let {
            if(asteroid != null){
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(asteroid))
                viewModel.hasNavegatedToDetails()
            }
        } })

        //Observe for button clicks
        viewModel.error.observe(viewLifecycleOwner, Observer { er ->
            Log.i("Debug","ERROR")
            if(er != null){
                Toast.makeText(context,"Unable to update data",Toast.LENGTH_LONG).show()
            }
         })





//        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
//            asteroids?.apply {
//                binding.recyclerView.adapter?.videos = videos
//            }
//        })


        return  binding.root
    }
}