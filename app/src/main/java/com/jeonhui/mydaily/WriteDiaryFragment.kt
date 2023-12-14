package com.jeonhui.mydaily

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jeonhui.mydaily.databinding.FragmentWriteDiaryBinding
import com.jeonhui.mydaily.structures.Diary
import com.jeonhui.mydaily.utils.EmojiFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WriteDiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteDiaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var date: Date? = null

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentWriteDiaryBinding

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
        binding = FragmentWriteDiaryBinding.inflate(inflater, container, false)
        configure()
        return binding.root
    }

    private fun configure() {
        emotionFiltering()
        binding.selectDateButton.setOnClickListener { showDatePickerDialog() }
        binding.writeButton.setOnClickListener { saveDiary() }
    }

    private fun emotionFiltering() {
        binding.diaryEmotion.filters = arrayOf(EmojiFilter(), InputFilter.LengthFilter(2))
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            date = Date(year, month, day)
            binding.diaryWriteDate.text = "$year-$month-$day"
        }
        DatePickerDialog(
            this.findNavController().context,
            data,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveDiary() {
        val date = date?.time ?: return
        val title = binding.diaryTitle.text.toString()
        val emotion = binding.diaryEmotion.text.toString()
        val content = binding.diaryContent.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            mainActivity.db.diaryDao().insert(Diary(0, title, emotion, date, content))
            MainScope().launch {
                view?.findNavController()?.navigate(R.id.action_writeDiaryFragment_to_diaryFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WriteDiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
