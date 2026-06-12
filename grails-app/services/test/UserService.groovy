package test

import dto.UserDto
import dto.UserResponseDto
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService


@Transactional
class UserService {
    TokenGenerator tokenGenerator
    UserDetailsService userDetailsService

    UserResponseDto register(UserDto dto){

        if(User.findByUsername(dto.username)){
            throw new RuntimeException("Cet utilisateur existe déjà")
        }

        User user = new User(
                username: dto.username,
                name: dto.name,
                phone: dto.phone,
                password: dto.password
        )

        user.save(flush: true, failOnError: true)

        Role role = Role.findByAuthority("ROLE_USER")
        if(!role){
            throw RuntimeException("Role introuvable")
        }

        UserRole.create(user, role, true)
        println user.errors

        def userDetails = userDetailsService.loadUserByUsername(user.username)
        def accessToken = tokenGenerator.generateAccessToken(userDetails)

        return new UserResponseDto(
                id: user.id,
                username: user.username,
                name: user.name,
                phone: user.phone,
                role: role.authority,
                token: accessToken.accessToken
        )

    }

    User getUser(){
        def auth = SecurityContextHolder.context.authentication?.name

        def user = User.findByUsername(auth)
        if(!user){
            throw RuntimeException("Utilisateur introuvable")
        }

        return user
    }

    UserResponseDto info(){

        def user = getUser()

        return new UserResponseDto(
                id: user.id,
                name: user.name,
                username: user.username,
                phone: user.phone,
                role: UserRole.findByUser(user)?.role?.authority
        )
    }

    UserResponseDto update(UserDto dto){

        def user = getUser()

        user.name = dto.name ?: user.name
        user.username = dto.username ?: user.username
        user.phone = dto.phone ?: user.phone
        user.save(flush: true, failOnError: true)

        return new UserResponseDto(
                id: user.id,
                name: user.name,
                username: user.username,
                phone: user.phone,
                role: UserRole.findByUser(user)?.role?.authority
        )
    }
}