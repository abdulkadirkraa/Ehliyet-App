package com.abdulkadirkara.ehliyethazirlik2024.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.adapter.OnItemClickListener
import com.abdulkadirkara.ehliyethazirlik2024.adapter.SoruAdapter
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityTestSorulariBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.SoruModel
import com.google.firebase.firestore.FirebaseFirestore

class TestSorulariActivity : AppCompatActivity(),OnItemClickListener {

    private lateinit var binding: ActivityTestSorulariBinding
    private var doc=""
    private var col=""
    private var testdoc=""
    private var sorularcol=""
    private lateinit var db:FirebaseFirestore
    private lateinit var sorularlist:ArrayList<SoruModel>
    private lateinit var adapter: SoruAdapter
    private var soruID=0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTestSorulariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actTexview.text=intent.getStringExtra("testname")
        db= FirebaseFirestore.getInstance()
        doc= intent.getStringExtra("doc").toString()
        col=intent.getStringExtra("col").toString()
        sorularlist= ArrayList()

        when(intent.getStringExtra("testname")){
            "Motor Tekniği Test1"->{
                testdoc="motortest1"
                sorularcol="motortest1sorular"
            }
            "Trafik Adabı Test1"->{
                testdoc="adabtest1"
                sorularcol="adabtest1sorular"
            }
            "Trafik ve Çevre Test1"->{
                testdoc="tt01"
                sorularcol="tt01sorular"
            }
            "İlk Yardım Test1"->{
                testdoc="yardimtest1"
                sorularcol="yardimtest1sorular"
            }
        }

        getData()

        binding.soruRecyclerview.layoutManager=LinearLayoutManager(this@TestSorulariActivity,LinearLayoutManager.HORIZONTAL,false)
        adapter=SoruAdapter(sorularlist,this)
        binding.soruRecyclerview.adapter=adapter

        binding.soruidTextview.setText("1/ ${sorularlist.size}")

        setSnapHelper()

        setClickListeners()
    }

    private fun getData() {
        db.collection("Ehliyetİçerik").document("Testler").collection("tests")
            .document(doc).collection(col).document(testdoc).collection(sorularcol).addSnapshotListener { value, error ->
                if (error != null){
                    Toast.makeText(this@TestSorulariActivity,error.localizedMessage,Toast.LENGTH_SHORT)
                }else{
                    if (value != null && !value.isEmpty){
                        val documents=value.documents
                        sorularlist.clear()

                        for (dc in documents){
                            var description=dc.get("description") as String
                            var correctoption=dc.get("correctoption") as String
                            var imgurl=dc.get("imgurl") as String?
                            var opA=dc.get("opA") as String
                            var opB=dc.get("opB") as String
                            var opC=dc.get("opC") as String
                            var opD=dc.get("opD") as String

                            var soru=SoruModel(description,correctoption,imgurl,opA,opB,opC,opD)
                            sorularlist.add(soru)
                        }
                    }
                }
            }
    }

    override fun onItemClickListener(position: Int) {
        val soru=sorularlist[position]
    }

    private fun setSnapHelper(){
        var snaphelper=PagerSnapHelper()
        snaphelper.attachToRecyclerView(binding.soruRecyclerview)

        binding.soruRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            @SuppressLint("SetTextI18n")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                var view=snaphelper.findSnapView(binding.soruRecyclerview.layoutManager)
                soruID=binding.soruRecyclerview.layoutManager!!.getPosition(view!!)
                binding.soruidTextview.setText("${soruID + 1}/ ${sorularlist.size}")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setClickListeners(){
        binding.sorubackBtn.setOnClickListener {
            if (soruID > 0){
                binding.soruRecyclerview.smoothScrollToPosition(soruID - 1)
            }
        }

        binding.sorunextBtn.setOnClickListener {
            if (soruID < sorularlist.size-1){
                binding.soruRecyclerview.smoothScrollToPosition(soruID+1)
            }
        }
    }
}