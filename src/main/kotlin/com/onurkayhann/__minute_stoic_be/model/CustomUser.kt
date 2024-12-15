package com.onurkayhann.__minute_stoic_be.model

import jakarta.persistence.*

@Entity
@Table(name = "StoicUser")
class CustomUser(
    val username: String = "",
    val password: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) {

}