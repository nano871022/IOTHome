package co.com.japl.iothome.def

import co.com.japl.iothome.dto.BaseDTO

interface IQueryDB<T : BaseDTO> {

    fun insertRow(dto:T):Boolean

    fun updateRow(dto:T):Boolean

    fun selectAll():List<T>

    fun selectById(id : Long):T

    fun close()
}