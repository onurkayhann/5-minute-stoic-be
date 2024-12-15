package com.onurkayhann.__minute_stoic_be.controller

import com.onurkayhann.__minute_stoic_be.model.CustomUser
import com.onurkayhann.__minute_stoic_be.model.StoicJournal
import com.onurkayhann.__minute_stoic_be.repository.CustomUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class CustomUserController(
    @Autowired private val customUserRepository: CustomUserRepository
) {

    @GetMapping("/all")
    fun getAllUsers(): MutableList<CustomUser> {

        return customUserRepository.findAll()

    }

    @PostMapping
    fun createUser(@RequestBody customUser: CustomUser): ResponseEntity<String> {

        customUserRepository.save(customUser)

        return ResponseEntity.status(201).body("User Created")
    }

    @PostMapping("/{userId}/journals")
    fun addJournal(
        @PathVariable userId: Long,
        @RequestBody journal: StoicJournal
    ): ResponseEntity<String> {
        val user = customUserRepository.findById(userId).orElseThrow {
            RuntimeException("User not found")
        }
        user.addJournal(journal)
        customUserRepository.save(user)
        return ResponseEntity.ok("Journal added successfully")
    }
}