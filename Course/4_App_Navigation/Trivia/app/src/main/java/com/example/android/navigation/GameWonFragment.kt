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
 */

package com.example.android.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false
        )

        binding.nextMatchButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_gameWonFragment_to_gameFragment)
        )

        val args = GameWonFragmentArgs.fromBundle(requireArguments())

        Toast.makeText(
            context,
            "NumCorrect: ${args.numCorrect}\nNumQuestions: ${args.numQuestions}",
            Toast.LENGTH_LONG
        ).show()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            @SuppressLint("QueryPermissionsNeeded")
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.winner_menu, menu)

                if (null === getShareIntent().resolveActivity(activity!!.packageManager)) {
                    menu.findItem(R.id.share)?.isVisible = false
                }
            }

            private fun getShareIntent(): Intent {
                val args = GameWonFragmentArgs.fromBundle(requireArguments())
                val shareIntent = Intent(Intent.ACTION_SEND)

                shareIntent.type = "text/plain"

                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.share_success_text, args.numCorrect, args.numQuestions)
                )

                return shareIntent
            }

            private fun shareSuccess() {
                startActivity(getShareIntent())
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.share -> {
                        shareSuccess()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }
}
