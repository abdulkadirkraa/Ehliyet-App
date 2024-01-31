package com.abdulkadirkara.ehliyethazirlik2024.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdulkadirkara.ehliyethazirlik2024.adapter.DenemeSorulariAdapter
import com.abdulkadirkara.ehliyethazirlik2024.adapter.OnItemClickListener
import com.abdulkadirkara.ehliyethazirlik2024.databinding.ActivityDenemeSorulariBinding
import com.abdulkadirkara.ehliyethazirlik2024.model.DenemeSorulariModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.concurrent.TimeUnit

class DenemeSorulariActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding:ActivityDenemeSorulariBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var denemesorularilist:ArrayList<DenemeSorulariModel>
    private lateinit var adapter: DenemeSorulariAdapter
    private var soruID=0
    lateinit var time:String
    var dogrusayisi=0
    var yanlissayisi=0
    var bossayisi=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDenemeSorulariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=Firebase.firestore
        denemesorularilist= ArrayList()

        getData()

        binding.soruRecyclerview.layoutManager=LinearLayoutManager(this@DenemeSorulariActivity, LinearLayoutManager.HORIZONTAL,false)
        adapter=DenemeSorulariAdapter(denemesorularilist,this)
        binding.soruRecyclerview.adapter=adapter

        binding.bitirBtn.setOnClickListener {
            //boşları saydır
            dogrusayisi=adapter.dogrular
            yanlissayisi=adapter.yanlislar
            bossayisi=50-dogrusayisi-yanlissayisi
            var mesaj:String
            if (bossayisi == 0)
                mesaj="Sınavı bitirmek üzeresin. Emin misin?"
            else
                mesaj="Daha $bossayisi boş sorun var. Sınavı bitirmeye emin misin?"
            MaterialAlertDialogBuilder(this)
                .setTitle("Sınavı Bitir")
                .setMessage(mesaj)
                .setPositiveButton("Evet"){dialog,which->
                    intent= Intent(this,SonucActivity::class.java)
                    intent.putExtra("dogrusayisi",dogrusayisi.toString())
                    intent.putExtra("yanlissayisi",yanlissayisi.toString())
                    intent.putExtra("bossayisi",bossayisi.toString())
                    intent.putExtra("kalansure",calculateTime(time))
                    startActivity(intent)
                    finish()
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                .setNegativeButton("Hayır") { dialog, which ->
                    // Respond to negative button press
                }
                .show()
        }

        startTimer()

        setSnapHelper()

        setClickListeners()
    }

    private fun calculateTime(time: String): String {
        val timeParts = time.split(":")
        val minutes = timeParts[0].toInt()
        val seconds = timeParts[1].toInt()
        val elapsedSeconds = (45 * 60) - (minutes * 60 + seconds)

        val elapsedMinutes = elapsedSeconds / 60
        val remainingSeconds = elapsedSeconds % 60

        return String.format("%02d:%02d", elapsedMinutes, remainingSeconds)
    }

    private fun getData() {
        db.collection("Ehliyetİçerik").document("Denemeler").collection("denemes")
            .document("deneme1").collection("deneme1sorular")
            .addSnapshotListener { value, error ->
                if (error != null){
                    error.printStackTrace()
                    Toast.makeText(this@DenemeSorulariActivity,error.localizedMessage,Toast.LENGTH_SHORT).show()
                }else{
                    if (value != null && !value.isEmpty){
                        val docs=value.documents
                        denemesorularilist.clear()

                        for (doc in docs){
                            var description=doc.get("description") as String
                            var correctoption=doc.get("correctoption") as String
                            var imgurl=doc.get("imgurl") as String?
                            var category=doc.get("category") as String
                            var opA=doc.get("opA") as String
                            var opB=doc.get("opB") as String
                            var opC=doc.get("opC") as String
                            var opD=doc.get("opD") as String

                            var denemesorusu=DenemeSorulariModel(description,correctoption,imgurl,category,
                                opA,opB,opC,opD)

                            denemesorularilist.add(denemesorusu)
                            println(denemesorusu)
                        }
                    }
                }
            }
    }

    override fun onItemClickListener(position: Int) {
        val soru=denemesorularilist[position]
    }

    private fun setSnapHelper(){
        var snaphelper= PagerSnapHelper()
        snaphelper.attachToRecyclerView(binding.soruRecyclerview)

        binding.soruRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            @SuppressLint("SetTextI18n")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                var view=snaphelper.findSnapView(binding.soruRecyclerview.layoutManager)
                soruID=binding.soruRecyclerview.layoutManager!!.getPosition(view!!)
                binding.soruidTextview.setText("${soruID + 1}/ ${denemesorularilist.size}")
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
            if (soruID < denemesorularilist.size-1){
                binding.soruRecyclerview.smoothScrollToPosition(soruID+1)
            }
        }
    }

    private fun startTimer() {
        val totalTime: Long = 45 * 60 * 1000
        val timer = object : CountDownTimer(totalTime + 1000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(remainingTime: Long) {
                time = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))
                )

                binding.sure.text= "Süre: $time dakika"
            }

            override fun onFinish() {
                val alertDialogBuilder=MaterialAlertDialogBuilder(this@DenemeSorulariActivity)
                    .setTitle("SÜRE BİTTİ")
                    .setMessage("Sonuç ekranına yönlendiriliyorsunuz")
                    .show()

                Handler().postDelayed({
                    alertDialogBuilder.dismiss()
                    intent= Intent(this@DenemeSorulariActivity,SonucActivity::class.java)
                    intent.putExtra("dogrusayisi",dogrusayisi)
                    intent.putExtra("yanlissayisi",yanlissayisi)
                    intent.putExtra("bossayisi",bossayisi)
                    intent.putExtra("kalansure",calculateTime(time))
                    startActivity(intent)
                    finish()
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                    finish()
                }, 4200)
            }
        }
        timer.start()
    }
}