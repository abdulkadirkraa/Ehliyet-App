package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulkadirkara.ehliyethazirlik2024.adapter.OnItemClickListener
import com.abdulkadirkara.ehliyethazirlik2024.adapter.TestAdapter
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityTestlerBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.TestModel
import com.google.firebase.firestore.FirebaseFirestore


class TestlerActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityTestlerBinding
    private lateinit var db:FirebaseFirestore
    private lateinit var testlerlist:ArrayList<TestModel>
    private lateinit var adapter:TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTestlerBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        db= FirebaseFirestore.getInstance()
        testlerlist= ArrayList()

        getData()

        binding.recyclerView2.layoutManager=LinearLayoutManager(this@TestlerActivity,LinearLayoutManager.VERTICAL,false)
        adapter= TestAdapter(testlerlist,this)
        binding.recyclerView2.adapter=adapter
    }

    private fun getData() {
        db.collection("Ehliyetİçerik").document("Testler").collection("tests")
            .orderBy("order").addSnapshotListener { value, error ->
                if (error != null){
                    error.printStackTrace()
                }else{
                    if (value != null && !value.isEmpty){
                        val docs= value.documents
                        testlerlist.clear()

                        for (doc in docs){
                            var title=doc.get("title") as String
                            var subtitle=doc.get("subtitle") as String
                            var order=doc.get("order") as Long

                            val test=TestModel(title,subtitle,order)
                            testlerlist.add(test)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onItemClickListener(position: Int) {
        val test=testlerlist[position]
        val intent= Intent(this@TestlerActivity,SorularActivity::class.java)
        intent.putExtra("testkategori",test.title)
        startActivity(intent)
    }
}