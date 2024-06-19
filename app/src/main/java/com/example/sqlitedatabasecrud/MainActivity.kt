package com.example.sqlitedatabasecrud

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.core.view.WindowInsetsCompat
import com.example.sqlitedatabasecrud.databinding.ActivityMainBinding
import com.example.sqlitedatabasecrud.model.MyHelper

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: SQLiteDatabase
    lateinit var rs: Cursor
    lateinit var adapter: SimpleCursorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var helper = MyHelper(applicationContext)
        db = helper.readableDatabase
        rs = db.rawQuery("SELECT * FROM User", null)

        binding.btnFisrt.setOnClickListener {
            if (rs.moveToFirst()) {
                binding.edtName.setText(rs.getString(1))
                binding.edtEmail.setText(rs.getString(2))
            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnLast.setOnClickListener {
            if (rs.moveToLast()) {
                binding.edtName.setText(rs.getString(1))
                binding.edtEmail.setText(rs.getString(2))
            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            if (rs.moveToNext()) {
                binding.edtName.setText(rs.getString(1))
                binding.edtEmail.setText(rs.getString(2))
            } else if (rs.moveToFirst()) {
                binding.edtName.setText(rs.getString(1))
                binding.edtEmail.setText(rs.getString(2))
            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnPrev.setOnClickListener {
            if (rs.moveToPrevious()) {
                binding.edtName.setText(rs.getString(1))
                binding.edtEmail.setText(rs.getString(2))
            } else if (rs.moveToLast()) {
                binding.edtName.setText(rs.getString(1))
                binding.edtEmail.setText(rs.getString(2))
            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

        // list all to listview
        adapter = SimpleCursorAdapter(
            applicationContext, android.R.layout.simple_expandable_list_item_2, rs,
            arrayOf("name", "email"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0
        )

        binding.lvFull.adapter = adapter
        binding.btnFull.setOnClickListener {
            binding.searchView.visibility = View.VISIBLE
            binding.lvFull.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
            // click vào bản ghi nào thì hiển thị lên edittext
            binding.lvFull.setOnItemClickListener { parent, view, position, id ->
                rs.moveToPosition(position)
                binding.edtName.setText(rs.getString(1))
                binding.edtEmail.setText(rs.getString(2))
            }
            binding.searchView.queryHint = "Search in ${rs.count} records"
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                rs = db.rawQuery(
                    "SELECT * FROM User WHERE name LIKE '%$newText%' or email LIKE '$newText'",
                    null
                )
                adapter.changeCursor(rs)
                return false
            }
        })
        binding.btnInsert.setOnClickListener {
            var cv = ContentValues()
            cv.put("name", binding.edtName.text.toString())
            cv.put("email", binding.edtEmail.text.toString())
            db.insert("User", null, cv)
           rs.requery()
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Insert successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnUpdate.setOnClickListener {
            var cv = ContentValues()
            cv.put("name", binding.edtName.text.toString())
            cv.put("email", binding.edtEmail.text.toString())
            db.update("User", cv, "_id=?", arrayOf(rs.getString(0)))
            rs.requery()
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnDelete.setOnClickListener {
            db.delete("User", "_id=?", arrayOf(rs.getString(0)))
            rs.requery()
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Delete successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnClear.setOnClickListener {
            binding.edtName.setText("")
            binding.edtEmail.setText("")
        }
    }
}