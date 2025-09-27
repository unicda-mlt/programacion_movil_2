/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.network.MarsApiFilter

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this)[OverviewViewModel::class.java]
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater, container, false)

        // Use viewLifecycleOwner (recommended)
        binding.lifecycleOwner = viewLifecycleOwner

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                viewModel.updateFilter(
                    when (menuItem.itemId) {
                        R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                        R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                        else -> MarsApiFilter.SHOW_ALL
                    }
                )
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

}
