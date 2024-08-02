package com.arkul.mychat.ui.screens.main.chat

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkul.mychat.R
import com.arkul.mychat.data.models.uiEvents.ChatEvents
import com.arkul.mychat.data.models.Message
import com.arkul.mychat.data.models.PhotoGallery
import com.arkul.mychat.databinding.FragmentChatBinding
import com.arkul.mychat.ui.adapters.carousels.CarouselAdapter
import com.arkul.mychat.ui.adapters.recyclerViews.MessageListAdapter
import com.arkul.mychat.ui.adapters.recyclerViews.SelectedPhotosGalleryListAdapter
import com.arkul.mychat.utilities.extensions.getStateListDrawable
import com.arkul.mychat.utilities.extensions.toDp
import com.arkul.mychat.utilities.keyboard.hideKeyboardFrom
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.emojis
import com.vanniktech.emoji.ios.IosEmojiProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    private lateinit var selectedPhotosAdapter: SelectedPhotosGalleryListAdapter

    private lateinit var messageListAdapter: MessageListAdapter

    private lateinit var emojiPopup: EmojiPopup

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private lateinit var adapter: CarouselAdapter


    private val multiplyPhotoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uriList ->
            if (uriList.isNotEmpty()) {
                binding.recyclerViewAddedPhotos.visibility = View.VISIBLE
                selectedPhotosAdapter.setList(uriList.map { PhotoGallery(it) })
            } else {
                binding.recyclerViewAddedPhotos.visibility = View.GONE
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        setupEmojiPopup()
        setupChatRecyclerView()
        setupSelectedPhotosRecyclerView()
        setupMenuAppBarLayout()
        setupButtonOnClickListeners()
        setupCarousel()
        setupBottomSheet()



        viewLifecycleOwner.lifecycleScope.launch {
            observeChatEvents()
        }

        return binding.root
    }

    private fun setupCarousel() {
        adapter = CarouselAdapter()

        binding.bottomSheetStandardLayout.carouselRecyclerView.adapter = adapter
    }

    private suspend fun observeChatEvents() = viewModel.chatEvents.collectLatest {
        when (it) {
            ChatEvents.OpenEmojiBottomPanel -> {
                emojiPopup.toggle()
            }

            ChatEvents.OpenGallery -> {
                multiplyPhotoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }

            is ChatEvents.SendMessage -> {
                binding.recyclerViewAddedPhotos.visibility = View.GONE
                selectedPhotosAdapter.clearList()
                messageListAdapter.addItem(it.message)
                binding.recyclerView.smoothScrollToPosition(messageListAdapter.itemCount)
                binding.textInputEditTextMessage.text?.clear()
            }
        }
    }

    private fun setupChatRecyclerView() {
        messageListAdapter = MessageListAdapter()
        binding.recyclerView.adapter = messageListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
        }
    }

    private fun setupSelectedPhotosRecyclerView() {
        selectedPhotosAdapter = SelectedPhotosGalleryListAdapter()
        selectedPhotosAdapter.setSizeItem(80, 80)
        binding.recyclerViewAddedPhotos.setHasFixedSize(true)
        binding.recyclerViewAddedPhotos.adapter = selectedPhotosAdapter
        binding.recyclerViewAddedPhotos.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetStandardLayout.layoutStandardInterlocutorProfile)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setupEmojiPopup() {
        EmojiManager.install(IosEmojiProvider())
        emojiPopup = EmojiPopup(
            binding.root,
            binding.textInputEditTextMessage,
            onEmojiClickListener = {
                binding.textInputEditTextMessage.text.emojis()
            },
            onEmojiPopupDismissListener = {
                binding.buttonEmoji.isChecked = false
            }
        )

        binding.buttonEmoji.buttonDrawable = getStateListDrawable(
            requireContext(),
            R.drawable.ic_keyboard,
            R.drawable.ic_emoji_outline
        )
    }

    private fun setupButtonOnClickListeners() {
        binding.buttonEmoji.setOnClickListener {
            viewModel.setChatEvents(ChatEvents.OpenEmojiBottomPanel)
        }
        binding.checkBoxAddPhoto.setOnClickListener {
            viewModel.setChatEvents(ChatEvents.OpenGallery)
        }
        binding.buttonSend.setOnClickListener {
            viewModel.setChatEvents(
                ChatEvents.SendMessage(
                Message(
                    text = binding.textInputEditTextMessage.text.toString(),
                    photos = selectedPhotosAdapter.asyncListDiffer.currentList.map { it.uri }
                )
            ))
        }
    }

    private fun setupMenuAppBarLayout() {
        binding.toolbar.menu.getItem(0).icon = ContextCompat.getDrawable(requireContext(), R.drawable.us)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profile_interlocutor -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    hideKeyboardFrom(binding.textInputEditTextMessage)
                    true
                }

                else -> false
            }
        }
    }


}