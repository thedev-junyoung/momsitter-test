package com.momsitter.application.user.service

import com.momsitter.application.user.dto.ChangePasswordCommand
import com.momsitter.application.user.dto.command.ExtendToParentCommand
import com.momsitter.application.user.dto.command.ExtendToSitterCommand
import com.momsitter.application.user.dto.result.MyInfoResult
import com.momsitter.application.user.dto.command.SignUpCommand
import com.momsitter.application.user.dto.result.ExtendToParentResult
import com.momsitter.application.user.dto.result.ExtendToSitterResult
import com.momsitter.application.user.dto.result.SignUpResult
import com.momsitter.application.user.factory.UserSignUpStrategy
import com.momsitter.application.user.validator.SignUpValidator
import com.momsitter.application.user.validator.UserValidator
import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import com.momsitter.domain.PasswordEncoder
import com.momsitter.domain.user.UserRepository
import com.momsitter.domain.user.UserRoleType

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val signUpValidator: SignUpValidator,
    private val userValidator: UserValidator,
    private val strategies: List<UserSignUpStrategy>, // Spring이 자동 주입
    private val passwordEncoder: PasswordEncoder
){
    // 회원가입
    // 사용자는 회원가입을 통해서 시터회원 또는 부모회원으로 가입할 수 있어야 합니다.
    @Transactional
    fun signup(command: SignUpCommand): SignUpResult {
        // 1. 유효성 검사
        signUpValidator.validate(command.username, command.email)

        // 2. 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(command.rawPassword)

        // 3. 역할에 따른 사용자 생성
        val strategy = strategies.find { it.supports(command.roles) }
            ?: throw IllegalArgumentException("Unsupported role: ${command.roles}")


        val user = strategy.create(command, encodedPassword)
        userRepository.save(user)
        return SignUpResult.from(user)
    }

    @Transactional
    fun getMyInfo(userId: Long): MyInfoResult {
        val role = userRepository.getUserRole(userId)

        val user = when (role) {
            UserRoleType.SITTER -> userRepository.findSitterUser(userId)
            UserRoleType.PARENT -> userRepository.findParentUser(userId)
            else -> userValidator.validateUserId(userId)
        } ?: throw BusinessException("존재하지 않는 사용자입니다.", ErrorCode.USER_NOT_FOUND)

        return MyInfoResult(user) // 여전히 생성자 활용
    }

    // 유저 정보 수정(Token 기반)
    // 사용자는 회원가입시 제출한 정보를 수정할 수 있어야 합니다.
    // 이 API 는 인증토큰을 이용한 사용자 인증 과정이 있어야 합니다.

    // 시터로 확장하기
    @Transactional
    fun extendToSitter(command: ExtendToSitterCommand): ExtendToSitterResult {
        val user = userValidator.validateUserId(command.userId)
        user.extendToSitter(
            minAge = command.minCareAge,
            maxAge = command.maxCareAge,
            introduction = command.introduction
        )

        return ExtendToSitterResult.from(user)
    }

    // 부모로 확장하기
    @Transactional
    fun extendToParent(command: ExtendToParentCommand): ExtendToParentResult {
        val user = userValidator.validateUserId(command.userId)
        val children = command.children ?: emptyList()
        user.extendToParent(children)

        return ExtendToParentResult.from(user)
    }

    @Transactional
    fun changePassword(command: ChangePasswordCommand) {
        val user = userValidator.validateUserId(command.userId)
        if (!passwordEncoder.matches(command.oldPassword, user.password)) {
            throw BusinessException("비밀번호가 일치하지 않습니다.", ErrorCode.INVALID_PASSWORD)
        }

        user.changePassword(passwordEncoder.encode(command.newPassword))
        userRepository.save(user)
    }

    @Transactional
    fun updateUserInfo(command: UpdateUserInfoCommand) {
        val user = userValidator.validateUserId(command.userId)
        user.updateInfo(command.name, command.email)
        userRepository.save(user)
    }

}