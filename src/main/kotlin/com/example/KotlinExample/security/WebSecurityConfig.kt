package com.example.KotlinExample.security

import com.example.KotlinExample.services.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.vote.AuthenticatedVoter
import org.springframework.security.access.vote.RoleVoter
import org.springframework.security.access.vote.UnanimousBased
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.expression.WebExpressionVoter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var service: UsersService

    @Autowired
    lateinit var unauthorizedHandler: AuthenticationEntryPoint

    @Autowired
    lateinit var successHandler: WebSecurityAuthSuccessHandler

    @Autowired
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    override fun configure(http: HttpSecurity?) {
        http
                ?.csrf()?.disable()
                ?.exceptionHandling()
                ?.authenticationEntryPoint(unauthorizedHandler)
                ?.and()
                ?.authorizeRequests()
                ?.antMatchers(HttpMethod.POST,
                        "/hello"
                )
                ?.permitAll()
                ?.antMatchers(
                        HttpMethod.GET, "/user"
                )
                ?.authenticated()
                ?.and()
                ?.formLogin()?.loginProcessingUrl("/login")?.usernameParameter("email")
                ?.successHandler(successHandler)
                ?.failureHandler(WebSecurityAuthFailureHandler())
                ?.and()
                ?.logout()?.logoutUrl("/logout")?.logoutSuccessHandler(LogoutSuccessHandler())
                ?.invalidateHttpSession(true)?.deleteCookies("JSESSIONID")


//        val corsConfiguration = CorsConfiguration()
//        corsConfiguration.allowCredentials = true
//        corsConfiguration.allowedHeaders = arrayListOf("*")
//        corsConfiguration.allowedMethods = arrayListOf("*")
//        corsConfiguration.allowedOrigins = arrayListOf("*")
//        corsConfiguration.maxAge = 3600
//        http?.cors()?.configurationSource { corsConfiguration }
    }


    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(service)
        authProvider.setPasswordEncoder(object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence?): String {
                return rawPassword.toString()
            }

            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
                return encodedPassword?.contentEquals(rawPassword ?: "") ?: false
            }

        })
        return authProvider
    }

//    @Bean
//    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun accessDecisionManager(): AccessDecisionManager {
        val decisionVoter = listOf(
                WebExpressionVoter(),
                RoleVoter(),
                AuthenticatedVoter()
        )
        return UnanimousBased(decisionVoter)
    }


}