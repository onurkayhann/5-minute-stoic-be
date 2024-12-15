package com.onurkayhann.__minute_stoic_be.model
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "StoicJournal")
class StoicJournal(
    @Column(nullable = false)
    val title: String = "",

    @Column(nullable = false)
    val affirmation: String = "",

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    var user: CustomUser? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) {

}