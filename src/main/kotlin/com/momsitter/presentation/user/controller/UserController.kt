package com.momsitter.presentation.user.controller


import com.momsitter.application.user.dto.ChangePasswordCommand
import com.momsitter.application.user.dto.command.ExtendToParentCommand
import com.momsitter.application.user.dto.command.ExtendToSitterCommand
import com.momsitter.application.user.service.UserService
import com.momsitter.application.user.dto.command.SignUpCommand
import com.momsitter.application.user.dto.result.SignUpResult
import com.momsitter.application.user.service.UpdateUserInfoCommand
import com.momsitter.common.CustomApiResponse
import com.momsitter.domain.child.ChildInfo
import com.momsitter.presentation.user.dto.request.*
import com.momsitter.presentation.user.dto.response.ExtendToParentResponse
import com.momsitter.presentation.user.dto.response.ExtendToSitterResponse
import com.momsitter.presentation.user.dto.response.MyInfoResponse
import com.momsitter.presentation.user.dto.response.SignupResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) : UserAPI {

    @PostMapping
    override fun signup(@RequestBody request: SignupRequest): ResponseEntity<CustomApiResponse<SignupResponse>> {
        val command = SignUpCommand.of(request)
        val result: SignUpResult = userService.signup(command)

        return ResponseEntity.ok(
            CustomApiResponse.success(
                SignupResponse(result.userId, result.username, result.role),
                "회원가입 성공"
            )
        )
    }

    @GetMapping("/me")
    override fun getMyInfo(request: HttpServletRequest): ResponseEntity<CustomApiResponse<MyInfoResponse>> {
        val userId = request.getAttribute("userId") as Long
        val user = userService.getMyInfo(userId)
        return ResponseEntity.ok(CustomApiResponse.success(MyInfoResponse.from(user)))
    }

    @PostMapping("/extend-role/sitter")
    override fun extendToSitter(
        request: HttpServletRequest,
        @RequestBody @Valid requestDto: ExtendToSitterRequest
    ): ResponseEntity<CustomApiResponse<ExtendToSitterResponse>> {
        val userId = request.getAttribute("userId") as Long
        val command = ExtendToSitterCommand.of(userId, requestDto.minCareAge, requestDto.maxCareAge, requestDto.introduction)
        val result = userService.extendToSitter(command)

        return ResponseEntity.ok(CustomApiResponse.success(ExtendToSitterResponse.from(result), "시터 역할 확장 성공"))
    }

    @PostMapping("/extend-role/parent")
    override fun extendToParent(
        request: HttpServletRequest,
        @RequestBody @Valid requestDto: ExtendToParentRequest
    ): ResponseEntity<CustomApiResponse<ExtendToParentResponse>> {
        val userId = request.getAttribute("userId") as Long
        val command = ExtendToParentCommand(
            userId,
            requestDto.children?.map { ChildInfo.of(it.name, it.birthDate, it.gender) }
        )
        val result = userService.extendToParent(command)

        return ResponseEntity.ok(CustomApiResponse.success(ExtendToParentResponse.from(result), "부모 역할 확장 성공"))
    }

    @PatchMapping("/me/info")
    override fun updateUserInfo(userId: Long, request: UpdateUserInfoRequest): ResponseEntity<Void> {
        userService.updateUserInfo(
            UpdateUserInfoCommand.of(
                userId = userId,
                name = request.name,
                email = request.email
            )
        )
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/me/password")
    override fun changePassword(userId: Long, request: ChangePasswordRequest): ResponseEntity<Void> {
        userService.changePassword(
            ChangePasswordCommand.of(
                userId = userId,
                oldPassword = request.oldPassword,
                newPassword = request.newPassword
            )
        )
        return ResponseEntity.noContent().build()
    }

}
