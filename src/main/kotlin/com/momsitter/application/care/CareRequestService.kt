package com.momsitter.application.care

import com.momsitter.application.care.dto.CreateCareRequestCommand
import com.momsitter.application.care.dto.UpdateCareRequestCommand
import com.momsitter.common.BusinessException
import com.momsitter.common.ErrorCode
import com.momsitter.domain.care.CareRequest
import com.momsitter.domain.care.CareRequestRepository
import com.momsitter.domain.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CareRequestService(
    private val userRepository: UserRepository,
    private val careRequestRepository: CareRequestRepository
) {

    @Transactional
    fun createCareRequest(command: CreateCareRequestCommand): Long {
        val user = userRepository.findById(command.parentUserId)
            .orElseThrow { BusinessException("존재하지 않는 사용자입니다.", ErrorCode.USER_NOT_FOUND) }

        val parentProfile = user.parentProfile
            ?: throw BusinessException("부모 프로필이 존재하지 않습니다.", ErrorCode.PARENT_PROFILE_NOT_FOUND)

        val saved = careRequestRepository.save(CareRequest.of(parentProfile, command.content))
        return saved.id
    }

    @Transactional
    fun updateCareRequest(command: UpdateCareRequestCommand) {
        val request = careRequestRepository.findById(command.requestId)
            .orElseThrow { BusinessException("돌봄 요청을 찾을 수 없습니다.", ErrorCode.CARE_REQUEST_NOT_FOUND) }

        if (request.parentProfile?.user?.id != command.parentUserId) {
            throw BusinessException("본인의 요청만 수정할 수 있습니다.", ErrorCode.CARE_REQUEST_FORBIDDEN)
        }

        request.updateContent(command.content)
    }
}
