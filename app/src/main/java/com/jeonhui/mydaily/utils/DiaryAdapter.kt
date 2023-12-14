package com.jeonhui.mydaily.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jeonhui.mydaily.R
import com.jeonhui.mydaily.structures.Diary
import java.sql.Date

class DiaryAdapter(val context: Context, val diaryList: ArrayList<Diary>) : BaseAdapter() {
    override fun getCount(): Int {
        return diaryList.size
    }

    override fun getItem(position: Int): Any {
        return diaryList[position]
    }

    override fun getItemId(position: Int): Long {
        return diaryList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_item, null)
        val emotionTextView = view.findViewById<TextView>(R.id.item_emotion)
        val titleTextView = view.findViewById<TextView>(R.id.item_title)
        val dateTextView = view.findViewById<TextView>(R.id.item_date)

        val diary = diaryList[position]
        emotionTextView.text = diary.emotion
        titleTextView.text = diary.title
        dateTextView.text = Date(diary.createAt).toString().replace("39","20")
        return view
    }

}