package com.jeonhui.mydaily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.jeonhui.mydaily.databinding.FragmentDiaryBinding
import com.jeonhui.mydaily.structures.Diary
import com.jeonhui.mydaily.utils.DiaryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DairyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DairyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentDiaryBinding
    lateinit var mainActivity: MainActivity

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
        // Inflate the layout for this fragment
        mainActivity = activity as MainActivity
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        binding.btnWrite.setOnClickListener { view: View -> navigate(view) }
        binding.refresh.setOnClickListener { _ -> fetchDiary() }
        binding.diaryListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectItem = parent.getItemAtPosition(position) as Diary
                val bundle = Bundle()
                bundle.putSerializable("item", selectItem)
                view.findNavController().navigate(R.id.action_diaryFragment_to_detailDiaryFragment, bundle)
            }
        fetchDiary()
        return binding.root
    }

    fun navigate(view: View) {
        view.findNavController().navigate(R.id.action_diaryFragment_to_writeDiaryFragment)
    }

    fun fetchDiary() {
        CoroutineScope(Dispatchers.IO).launch {
            val diaryList = mainActivity.db.diaryDao().getAllDiary()
            val diaryAdapter = DiaryAdapter(mainActivity, diaryList = ArrayList(diaryList))
            MainScope().launch {
                binding.diaryListView.adapter = diaryAdapter
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DairyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DairyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}