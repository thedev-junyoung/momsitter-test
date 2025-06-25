package com.momsitter.application.user.service

import com.momsitter.application.user.dto.SignUpCommand
import com.momsitter.application.user.dto.SignUpResult
import com.momsitter.application.user.factory.UserFactoryResolver
import com.momsitter.application.user.validator.SignUpValidator
import com.momsitter.domain.PasswordEncoder
import com.momsitter.domain.user.UserRepository
import com.momsitter.domain.user.UserRoleType
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val signUpValidator: SignUpValidator,
    private val userFactoryResolver: UserFactoryResolver,
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

        // 3. 역할 파싱 (단일 역할 가정)
        val roleType = UserRoleType.valueOf(command.roles.uppercase())

        // 4. 역할별 팩토리 호출
        val userFactory = userFactoryResolver.resolve(roleType)
        val user = userFactory.create(command, encodedPassword, roleType)

        // 5. 저장 및 반환
        val savedUser = userRepository.save(user)

        return SignUpResult.from(savedUser)
    }




    // 유저 정보 조회(Token 기반)
    // 사용자는 작성한 내 정보를 조회할 수 있어야 합니다.
    // 다만 회원 정보 중에서 비밀번호는 노출되지 않아야 합니다.
    // 예를 들어 시터회원의 경우는
    // 회원번호/이름/생년월일/성별/아이디/이메일/케어 가능한 최소 연령/자기소개 정보가 노출될 것입니다.
    // 단, 추가적으로 부모로도 활동하기를 선택한 시터회원이라면
    // 아이나이/신청 내용 정보가 추가적으로 노출되어야 합니다.



    // 유저 정보 수정(Token 기반)
    // 사용자는 회원가입시 제출한 정보를 수정할 수 있어야 합니다.
    // 이 API 는 인증토큰을 이용한 사용자 인증 과정이 있어야 합니다.


}