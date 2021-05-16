package com.skuad.talent.data.mapper

interface ResponseMapper<IN, OUT> {
    fun map(input: IN): OUT
}