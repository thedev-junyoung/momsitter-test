package com.momsitter.presentation.user.controller


import com.momsitter.application.UserService
import com.momsitter.application.user.dto.SignUpCommand
import com.momsitter.application.user.dto.SignUpResult
import com.momsitter.common.CustomApiResponse
import com.momsitter.presentation.user.dto.SignupRequest
import com.momsitter.presentation.user.dto.SignupResponse
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
                SignupResponse(result.userId, result.username, result.role)
            )
        )
    }
}
