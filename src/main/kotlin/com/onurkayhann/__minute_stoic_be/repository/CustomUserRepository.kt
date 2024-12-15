package com.onurkayhann.__minute_stoic_be.repository

import com.onurkayhann.__minute_stoic_be.model.CustomUser
import org.springframework.data.jpa.repository.JpaRepository

interface CustomUserRepository: JpaRepository<CustomUser, Long> {
}