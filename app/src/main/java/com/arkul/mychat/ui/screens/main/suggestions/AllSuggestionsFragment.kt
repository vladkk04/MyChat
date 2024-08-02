package com.arkul.mychat.ui.screens.main.suggestions

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkul.mychat.MainActivity
import com.arkul.mychat.R
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.databinding.FragmentAllSuggestionsBinding
import com.arkul.mychat.ui.adapters.recyclerViews.OnItemClickListener
import com.arkul.mychat.ui.adapters.recyclerViews.SuggestionListAdapter
import com.arkul.mychat.ui.navigation.BaseFragment
import com.arkul.mychat.ui.screens.bottomSheets.SuggestionBottomSheet
import com.arkul.mychat.utilities.dialogs.createSuggestionReportDialog
import com.vanniktech.ui.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllSuggestionsFragment : BaseFragment<AllSuggestionsViewModel>() {

    private var _binding: FragmentAllSuggestionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SuggestionListAdapter

    override val viewModel: AllSuggestionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllSuggestionsBinding.inflate(inflater, container, false)

        setupRecycleViewSuggestion()
        setupOnMenuItemClickListener()

        return binding.root
    }

    private fun setupRecycleViewSuggestion() {

        adapter = SuggestionListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        adapter.setOnItemClickListener { item, _ ->
            (requireActivity() as MainActivity).hideBottomNavigationBar()
            viewModel.navigateToSuggestion(item)
        }

        adapter.setOnReportClickListener {
            createSuggestionReportDialog()?.show()
        }

       /* adapter.setOnForwardSuggestionClickListener {
            (activity as MainActivity).bottomNavigationSwitchTo(R.id.inboxChatsFragment)
        }

        adapter.setOnAuthorClickListener {
            (activity as MainActivity).bottomNavigationSwitchTo(R.id.profileFragment)
        }*/

        adapter.addItems(
            listOf(
                Suggestion(
                    topic = "Add to nigga shit you tu pidor ya duze koxai yulia deze silno yomayo",
                    avatarAuthor = BitmapFactory.decodeResource(resources, R.drawable.us)),

                Suggestion(
                    topic = "Add to nigga shit you tu pidor ya duze koxai yulia deze silno yomayo",
                    isAnonymous = true
                )
            )
        )
    }

    private fun setupOnMenuItemClickListener() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.create_suggestion -> {
                    setupCreateNewSuggestionBottomSheet()
                }
            }
            true
        }
    }

    private fun setupCreateNewSuggestionBottomSheet() {
        val suggestionBottomSheet = SuggestionBottomSheet()

        suggestionBottomSheet.show(
            requireActivity().supportFragmentManager,
            SuggestionBottomSheet.TAG
        )

        suggestionBottomSheet.setOnCreateSuggestionListener {
            adapter.addItem(it)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavigationBar()
    }

}