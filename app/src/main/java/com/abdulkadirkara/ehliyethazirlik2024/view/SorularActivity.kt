package com.abdulkadirkara.ehliyethazirlik2024.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulkadirkara.ehliyethazirlik2024.adapter.OnItemClickListener
import com.abdulkadirkara.ehliyethazirlik2024.adapter.TestlerAdapter
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivitySorularBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.TestlerModel
import com.google.firebase.firestore.FirebaseFirestore

class SorularActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivitySorularBinding
    private lateinit var db:FirebaseFirestore
    private lateinit var testler:ArrayList<TestlerModel>
    private lateinit var adapter: TestlerAdapter
    private var doc=""
    private var col=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySorularBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        db= FirebaseFirestore.getInstance()
        testler=ArrayList()

        when(intent.getStringExtra("testkategori").toString()){
            "Trafik Adabı Testleri"->{
                binding.actTexview.text="Trafik Adabı Testleri"
                doc="adabtests"
                col="adabtest"
            }
            "Motor Tekniği Testleri"->{
                binding.actTexview.text="Motor Tekniği Testleri"
                doc="motortests"
                col="motortest"
            }
            "Trafik ve Çevre Testleri"->{
                binding.actTexview.text="Trafik ve Çevre Testleri"
                doc="trafiktests"
                col="trafiktest"
            }
            else->{
                binding.actTexview.text="İlk Yardım Testleri"
                doc="yardimtests"
                col="yardimtest"
            }
        }

        gettests(doc,col)

        binding.testlerRecyclerview.layoutManager=LinearLayoutManager(this@SorularActivity,LinearLayoutManager.VERTICAL,false)
        adapter= TestlerAdapter(testler,this)
        binding.testlerRecyclerview.adapter=adapter
    }

    private fun gettests(doc:String, col:String) {
        db.collection("Ehliyetİçerik").document("Testler").collection("tests")
            .document(doc).collection(col).orderBy("order")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(this@SorularActivity, error.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (value != null && !value.isEmpty) {
                        val docs = value.documents
                        testler.clear()

                        for (doc in docs) {
                            var title = doc.get("title") as String
                            var order = doc.get("order") as Long
                            var test = TestlerModel(title, order)
                            testler.add(test)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onItemClickListener(position: Int) {
        val test=testler[position]
        val intent=Intent(this@SorularActivity,TestSorulariActivity::class.java)
        intent.putExtra("testname",test.title)
        intent.putExtra("doc",doc)
        intent.putExtra("col",col)
        startActivity(intent)
        finish()
        Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
}