package com.example.KotlinExample.data

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User
(
        @Id
        @Column(name = "id", columnDefinition = "int")
        var id: Int,
        @Column(name = "login", columnDefinition = "varchar(45)")
        var login: String,
        @Column(name = "email", columnDefinition = "varchar(45)")
        var email: String,
        @JsonIgnore
        @Column(name = "password", columnDefinition = "varchar(45)")
        var psw: String,
        @Column(name = "role", columnDefinition = "varchar(45)")
        var role: String
) : UserDetails {

    override fun getUsername(): String = email

    override fun isEnabled(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf<GrantedAuthority>().apply {
            add(SimpleGrantedAuthority(role))
        }
    }

    override fun getPassword(): String = psw
}

