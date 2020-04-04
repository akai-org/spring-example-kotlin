package com.example.KotlinExample.security

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutSuccessHandler : SimpleUrlLogoutSuccessHandler() {

    override fun onLogoutSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        if (authentication?.name != null) {
            response!!.status = HttpStatus.OK.value()
        } else {
            response!!.status = HttpStatus.UNAUTHORIZED.value()
        }
    }
}