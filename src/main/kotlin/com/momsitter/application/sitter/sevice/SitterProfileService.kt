package com.momsitter.application.sitter.sevice

import com.momsitter.application.sitter.dto.UpdateSitterProfileCommand
import com.momsitter.domain.user.UserRepository
import com.momsitter.application.user.validator.UserValidator
import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class SitterProfileService(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) {

    @Transactional
    fun updateSitterProfile(command: UpdateSitterProfileCommand) {
        val user = userValidator.validateUserWithSitterProfile(command.userId)
        val sitterProfile = user.sitterProfile
            ?: throw BusinessException("시터 프로필이 존재하지 않습니다.", ErrorCode.NOT_SITTER)

        sitterProfile.update(command.minCareAge, command.maxCareAge, command.introduction)
        userRepository.save(user)
    }

}
