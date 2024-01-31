package com.abdulkadirkara.ehliyethazirlik2024.model

data class SoruModel(
    var description:String,
    var correctoption:String,
    var imgurl:String?="",
    var opA:String,
    var opB:String,
    var opC:String,
    var opD:String
) {
}