package com.jeonhui.mydaily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.jeonhui.mydaily.databinding.FragmentDetailDiaryBinding
import com.jeonhui.mydaily.structures.Diary
import java.sql.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailDiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailDiaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentDetailDiaryBinding
    lateinit var mainActivity: MainActivity
    lateinit var diary: Diary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = activity as MainActivity
        binding = FragmentDetailDiaryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val diary = arguments?.getSerializable("item", Diary::class.java)
        diary?.let {
            this.diary = diary
            layoutDiary(it)
        }
        binding.editButton.setOnClickListener { view -> navigateToDiaryEditFragment(view) }
        binding.closeButton.setOnClickListener { view -> navigateToDiaryFragment(view) }
        return binding.root
    }

    fun layoutDiary(diary: Diary) {
        binding.diaryDetailEmotion.text = diary.emotion
        binding.diaryDetailTitle.text = diary.title
        binding.diaryDetailDate.text = Date(diary.createAt).toString().replace("39", "20")
        binding.diaryDetailContent.text = diary.content
    }

    fun navigateToDiaryFragment(view: View) {
        view.findNavController().navigate(R.id.action_detailDiaryFragment_to_diaryFragment)
    }

    fun navigateToDiaryEditFragment(view: View) {
        val bundle = Bundle()
        bundle.putSerializable("item", diary)
        view.findNavController().navigate(R.id.action_detailDiaryFragment_to_diaryEditFragment, bundle)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailDiaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailDiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}