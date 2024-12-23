package com.example.ucproomdatabase.ui.viewmodel

data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val SKS: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null,
    val nidn: String? = null,
    val jenisKelamin: String? = null
) {
    fun isValid(): Boolean {
        return kode == null && nama == null && SKS == null &&
                semester == null && jenis == null && dosenPengampu == null &&
                nidn == null && jenisKelamin == null
    }
}
