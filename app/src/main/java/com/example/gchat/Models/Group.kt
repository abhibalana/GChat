package com.example.gchat.Models

data class Group (var gPin:String = "",
                   var gName:String = "",
                  val users:ArrayList<String> = ArrayList()
)