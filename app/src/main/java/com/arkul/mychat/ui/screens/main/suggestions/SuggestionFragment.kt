package com.arkul.mychat.ui.screens.main.suggestions

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentSuggestionBinding
import com.arkul.mychat.ui.adapters.carousels.CarouselAdapter
import com.arkul.mychat.ui.navigation.BaseFragment
import com.arkul.mychat.utilities.dialogs.createSuggestionReportDialog
import com.arkul.mychat.utilities.image.ImageUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestionFragment : BaseFragment<SuggestionViewModel>() {
    private var _binding: FragmentSuggestionBinding? = null
    private val binding get() = _binding!!

    private val args: SuggestionFragmentArgs by navArgs()

    private lateinit var carouselAdapter: CarouselAdapter

    override val viewModel: SuggestionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionBinding.inflate(inflater, container, false)
        carouselAdapter = CarouselAdapter()

        setupSuggestion()
        setupBottomAppBarMenuItemsClick()

        return binding.root
    }

    private fun setupBottomAppBarMenuItemsClick() {
        with(binding) {
            bottomAppBar.menu.findItem(R.id.author).setIcon(createCircleDrawable())

            bottomAppBar.menu.findItem(R.id.thumb_up_item).actionView?.findViewById<CheckBox>(
                R.id.checkBox_thumb_up
            )?.apply {
                isChecked = args.suggestion.isThumbedUp
                text = args.suggestion.thumbUpCount.toString()
            }

            bottomAppBar.menu.findItem(R.id.thumb_down_item).actionView?.findViewById<CheckBox>(
                R.id.checkBox_thumb_down
            )?.apply {
                isChecked = args.suggestion.isThumbedDown
                text = args.suggestion.thumbDownCount.toString()
            }

            bottomAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.report -> createSuggestionReportDialog()?.show()
                    R.id.author -> viewModel.openAuthorProfile()
                }
                true
            }

            fabSendSuggestion.setOnClickListener { viewModel.sendSuggestionToChat() }
        }
    }

    private fun createCircleDrawable(): Drawable =
        ImageUtils(requireContext()).createCircleDrawable(
            bitmap = args.suggestion.avatarAuthor ?: BitmapFactory.decodeResource(
                resources, R.drawable.ic_anonymus_mask
            ), cornerRadius = 14f
        )

    private fun setupSuggestion() {

        binding.carouselRecyclerViewSuggestion.adapter = carouselAdapter

        with(args.suggestion) {
            binding.textViewTopic.text = topic
            binding.textViewDescription.text = description
            carouselAdapter.setList(photos)
        }
    }
}