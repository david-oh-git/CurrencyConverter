/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.getapikey

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.databinding.FragmentGetKeyBinding

class GetKeyFragment : Fragment(), GetKeyClickListener {

    private lateinit var binding: FragmentGetKeyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentGetKeyBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                listener = this@GetKeyFragment
            }
        return binding.root
    }

    override fun launchRegisterPage() {
        startActivity( Intent( Intent.ACTION_VIEW, Uri.parse(getString(R.string.get_api_key_url))))
    }

    override fun saveKey(view: View, apiKey: String) {
        hideKeyboard(view)
        if( apiKey.isNotEmpty() && apiKey.isNotBlank() ){
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putString(getString(R.string.preference_api_key), apiKey)
                putBoolean(getString(R.string.preference_has_api_key), true)
            }
            (activity as GetKeyActivity).navigateHome()
        }else{
            Snackbar.make(binding.parentLayout, "Please enter API key", Snackbar.LENGTH_LONG ).show()
        }


    }

    private fun hideKeyboard(view: View){
        (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .apply {
                hideSoftInputFromWindow(view.windowToken, 0)
            }

    }
}