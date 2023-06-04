package com.example.uts_russellherdianque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_russellherdianque.databinding.ActivityContentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ContentActivity : AppCompatActivity() {

    private lateinit var wisataRecyclerview: RecyclerView
    private lateinit var wisataList: MutableList<Image>
    private lateinit var wisataAdapter:ImageAdapter
    private lateinit var binding: ActivityContentBinding

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**initialized*/
        wisataRecyclerview = findViewById(R.id.imageRecyclerView)
        wisataRecyclerview.setHasFixedSize(true)
        wisataRecyclerview.layoutManager = LinearLayoutManager(this@ContentActivity)

        wisataList = ArrayList()
        wisataAdapter = ImageAdapter(this@ContentActivity,wisataList)
        wisataRecyclerview.adapter = wisataAdapter
        /**getData firebase*/

        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("wisata")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ContentActivity, error.message, Toast.LENGTH_SHORT).show()

            }



            override fun onDataChange(snapshot: DataSnapshot) {
                wisataList.clear()
                if (snapshot.exists()){
                    for (teacherSnapshot in snapshot.children){
                        val upload = teacherSnapshot.getValue(Image::class.java)
                        upload!!.key = teacherSnapshot.key
                        wisataList.add(upload)
                    }
                    wisataAdapter.notifyDataSetChanged()
                }
            }
        })
    }
    override fun onCreateOptionsMenu (menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when (item.itemId){
            R.id.Logout ->
            {
                FirebaseAuth.getInstance().signOut()
                Intent(this, MainActivity::class.java).also{
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
        return true
    }

}