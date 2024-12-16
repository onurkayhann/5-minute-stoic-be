package com.onurkayhann.__minute_stoic_be.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Entity
@Table(name = "StoicUser")
data class CustomUser(

    @NotEmpty
    @Size(min = 2, max = 16)
    val username: String = "",

    @NotEmpty
    @Size(min = 7, max = 30)
    val password: String = "",

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val journals: MutableList<StoicJournal> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) {

    fun addJournal(journal: StoicJournal) {
        journals.add(journal)
        journal.user = this
    }

    fun removeJournal(journal: StoicJournal) {
        journals.remove(journal)
        journal.user = null
    }

}