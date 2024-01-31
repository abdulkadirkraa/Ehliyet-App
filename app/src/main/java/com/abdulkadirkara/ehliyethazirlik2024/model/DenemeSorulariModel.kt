package com.abdulkadirkara.ehliyethazirlik2024.model

data class DenemeSorulariModel(
    var description:String,
    var correctoption:String,
    var imgurl:String?="",
    var category:String,
    var opA:String,
    var opB:String,
    var opC:String,
    var opD:String
)
