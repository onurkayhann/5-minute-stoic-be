package com.onurkayhann.__minute_stoic_be.controller

import com.onurkayhann.__minute_stoic_be.model.CustomUser
import com.onurkayhann.__minute_stoic_be.model.StoicJournal
import com.onurkayhann.__minute_stoic_be.repository.CustomUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class CustomUserController(
    @Autowired private val customUserRepository: CustomUserRepository,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
) {

    @GetMapping("/all")
    fun getAllUsers(): MutableList<CustomUser> {

        return customUserRepository.findAll()

    }

    @GetMapping
    fun test(): String {
        return "You are logged in"
    }

    @PostMapping
    fun createUser(@RequestBody customUser: CustomUser): ResponseEntity<String> {

        val hashedPassword = passwordEncoder.encode(customUser.password)
        val userWithHashedPassword = customUser.copy(password = hashedPassword)

        println("Password: ${customUser.password}")
        println("Hashed Password: $hashedPassword")

        customUserRepository.save(userWithHashedPassword)

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