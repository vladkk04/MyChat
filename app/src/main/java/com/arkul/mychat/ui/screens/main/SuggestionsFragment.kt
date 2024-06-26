package com.arkul.mychat.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.arkul.mychat.R
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.databinding.FragmentSuggestionsBinding
import com.arkul.mychat.ui.adapters.recyclerViews.SuggestionListAdapter
import com.arkul.mychat.ui.screens.bottomSheets.SuggestionBottomSheet
import com.arkul.mychat.utilities.dialogs.createSuggestionReportDialog
import com.arkul.mychat.utilities.extensions.showToast

/**
 * A simple [Fragment] subclass.
 * Use the [SuggestionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuggestionsFragment : Fragment() {
    private var _binding: FragmentSuggestionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SuggestionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuggestionsBinding.inflate(inflater, container, false)

        val linearLayoutManager =
            LinearLayoutManager(requireContext())

        adapter = SuggestionListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = linearLayoutManager

        adapter.setOnReportClickListener {
            createSuggestionReportDialog()?.show()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.create_suggestion -> {
                    val suggestionBottomSheet = SuggestionBottomSheet()
                    suggestionBottomSheet.show(requireActivity().supportFragmentManager, SuggestionBottomSheet.TAG)
                }
            }
            true
        }


        adapter.saveData(
            listOf(
                Suggestion("Add to nigga shit you tu pidor ya duze koxai yulia deze silno yomayo"),
                Suggestion("YA"),
                Suggestion("YA"),
                Suggestion ("LYBLYU"),
                Suggestion("YULIIYCHKU"),
                Suggestion("DYZE"),
                Suggestion("SILNO")
            )
        )



        return binding.root
    }

}