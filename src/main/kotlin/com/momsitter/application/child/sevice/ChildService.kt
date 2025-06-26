package com.momsitter.application.child.sevice

import com.momsitter.application.child.dto.UpdateChildCommand
import com.momsitter.domain.user.UserRepository
import com.momsitter.application.user.validator.UserValidator
import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ChildService(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator
) {

    @Transactional
    fun updateChild(command: UpdateChildCommand) {
        val user = userValidator.validateUserWithParentAndChildren(command.userId)
        val parentProfile = user.parentProfile
            ?: throw BusinessException("부모 프로필이 존재하지 않습니다.", ErrorCode.NOT_PARENT)

        val child = parentProfile.findChildById(command.childId)
            ?: throw BusinessException("자녀 정보가 존재하지 않습니다.", ErrorCode.CHILD_NOT_FOUND)

        child.update(command.name, command.birthDate, command.gender)
        userRepository.save(user)
    }



}
