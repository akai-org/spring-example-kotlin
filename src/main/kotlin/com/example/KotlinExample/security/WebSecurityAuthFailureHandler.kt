package com.example.KotlinExample.security

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class WebSecurityAuthFailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        when (exception) {
            is BadCredentialsException -> {
                response?.status = HttpStatus.BAD_REQUEST.value()
            }
            else -> {
                response?.status = HttpStatus.UNAUTHORIZED.value()
            }
        }
    }
}