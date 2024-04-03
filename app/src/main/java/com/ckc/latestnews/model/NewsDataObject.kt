package com.ckc.latestnews.model

import com.google.gson.annotations.SerializedName


data class NewsDataObject (

  @SerializedName("Business"      ) var Business      : ArrayList<Business>      = arrayListOf(),
  @SerializedName("Entertainment" ) var Entertainment : ArrayList<Entertainment> = arrayListOf(),
  @SerializedName("Health"        ) var Health        : ArrayList<Health>        = arrayListOf(),
  @SerializedName("Science"       ) var Science       : ArrayList<Science>       = arrayListOf(),
  @SerializedName("Sports"        ) var Sports        : ArrayList<Sports>        = arrayListOf(),
  @SerializedName("Technology"    ) var Technology    : ArrayList<Technology>    = arrayListOf(),
  @SerializedName("US"            ) var US            : ArrayList<US>            = arrayListOf(),
  @SerializedName("World"         ) var World         : ArrayList<World>         = arrayListOf()

)