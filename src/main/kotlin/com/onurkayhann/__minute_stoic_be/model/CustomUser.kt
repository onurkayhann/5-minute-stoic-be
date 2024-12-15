package com.onurkayhann.__minute_stoic_be.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "StoicUser")
class CustomUser(
    val username: String = "",
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