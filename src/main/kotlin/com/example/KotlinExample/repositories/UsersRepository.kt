package com.example.KotlinExample.repositories

import com.example.KotlinExample.data.User
import org.springframework.data.repository.CrudRepository

interface UsersRepository : CrudRepository<User?, Int?> {
    fun findOneByEmail(email: String?): User?
}
