package com.example.KotlinExample.restControllers

import com.example.KotlinExample.data.User
import com.example.KotlinExample.exceptions.UserNotFoundException
import com.example.KotlinExample.services.UsersService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class UsersRestController {

    @Autowired
    private lateinit var service: UsersService

    @Throws(UserNotFoundException::class)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUser(): ResponseEntity<User> {
        val email = SecurityContextHolder.getContext().authentication.name
        val user: User = service.getUserByEmail(email) ?: throw UserNotFoundException(email)
        return ResponseEntity.ok<User>(user)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody user: User): String {
        logger.info(user.toString())
        return "Ok"
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UsersRestController::class.java)
    }
}
