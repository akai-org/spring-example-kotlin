package com.example.KotlinExample.services

import com.example.KotlinExample.data.User
import com.example.KotlinExample.repositories.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service("Users Service")
class UsersService : UserDetailsService {

    @Autowired
    private lateinit var repository: UsersRepository

    fun getUserByEmail(email: String?): User? {
        return repository.findOneByEmail(email)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return getUserByEmail(username) ?: throw UsernameNotFoundException("No user with email $username")
    }

}
