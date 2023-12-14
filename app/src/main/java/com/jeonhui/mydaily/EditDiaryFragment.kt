package com.jeonhui.mydaily

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jeonhui.mydaily.databinding.FragmentEditDiaryBinding
import com.jeonhui.mydaily.structures.Diary
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
 * Use the [EditDiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditDiaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentEditDiaryBinding
    lateinit var mainActivity: MainActivity
    lateinit var diary: Diary
    private var date: Date? = null

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
        binding = FragmentEditDiaryBinding.inflate(inflater, container, false)

        val diary = arguments?.getSerializable("item", Diary::class.java)
        diary?.let {
            this.diary = diary
            layoutDiary(it)
        }

        binding.selectDateButton.setOnClickListener { showDatePickerDialog() }
        binding.editButton.setOnClickListener { update() }
        binding.deleteButton.setOnClickListener { delete() }

        return binding.root
    }

    fun layoutDiary(diary: Diary) {
        binding.diaryEditEmotion.setText(diary.emotion)
        binding.diaryEditTitle.setText(diary.title)
        binding.diaryEditDate.text = Date(diary.createAt).toString().replace("39", "20")
        binding.diaryEditContent.setText(diary.content)
    }

    fun update() {
        val new = this.diary.updateDiary(
            null,
            binding.diaryEditTitle.text.toString(),
            binding.diaryEditEmotion.text.toString(),
            date?.time,
            binding.diaryEditContent.text.toString()
        )
        CoroutineScope(Dispatchers.IO).launch {
            mainActivity.db.diaryDao().update(new)
            MainScope().launch {
                view?.findNavController()?.navigate(R.id.action_editDiaryFragment_to_diaryFragment)
            }
        }
    }

    fun delete() {
        val diary = this.diary
        CoroutineScope(Dispatchers.IO).launch {
            mainActivity.db.diaryDao().delete(diary)
            MainScope().launch {
                view?.findNavController()?.navigate(R.id.action_editDiaryFragment_to_diaryFragment)
            }
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            date = Date(year, month, day)
            binding.diaryEditDate.text = "$year-$month-$day"
        }
        DatePickerDialog(
            this.findNavController().context,
            data,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditDiaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditDiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}