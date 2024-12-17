package com.onurkayhann.__minute_stoic_be.controller

import com.onurkayhann.__minute_stoic_be.model.CustomUser
import com.onurkayhann.__minute_stoic_be.model.LoginRequest
import com.onurkayhann.__minute_stoic_be.model.StoicJournal
import com.onurkayhann.__minute_stoic_be.repository.CustomUserRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class CustomUserController(
    @Autowired private val customUserRepository: CustomUserRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
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
    fun saveUser(
        @Validated @RequestBody newUser: CustomUser
    ): ResponseEntity<String> {

        // password = 123
        val bcryptUser = CustomUser(
            newUser.username,
            passwordEncoder.encode(newUser.password) // 123 -> bcrypt
        )

        // Save
        customUserRepository.save(bcryptUser)

        return ResponseEntity.status(201).body("User was successfully created")
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

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        val user = customUserRepository.findByUsername(loginRequest.username)
        if (user == null) {
            return ResponseEntity.status(404).body("User not found")
        }

        if (passwordEncoder.matches(loginRequest.password, user.password)) {
            return ResponseEntity.ok("Login successful")
        } else {
            return ResponseEntity.status(401).body("Invalid credentials")
        }
    }
}