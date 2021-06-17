package com.skuad.talent.utils

object AppUtils {

     fun isExperienceInYears(experience: String) = "^[0-9\\s-]+$".toRegex().matches(experience)

}