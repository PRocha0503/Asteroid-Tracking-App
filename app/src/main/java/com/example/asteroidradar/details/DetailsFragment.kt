package com.example.asteroidradar.details

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentDetailsBinding
import com.example.asteroidradar.databinding.FragmentMainBinding


class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate with binding
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        val asteroid = DetailsFragmentArgs.fromBundle(arguments!!).asteroid
        binding.asteroid = asteroid
        binding.helpBtn.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
        return  binding.root
    }
    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

}