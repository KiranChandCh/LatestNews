package com.ckc.latestnews.model

import com.google.gson.annotations.SerializedName


data class World (

  @SerializedName("link"        ) var link       : String? = null,
  @SerializedName("og"          ) var og         : String? = null,
  @SerializedName("source"      ) var source     : String? = null,
  @SerializedName("source_icon" ) var sourceIcon : String? = null,
  @SerializedName("title"       ) var title      : String? = null

)