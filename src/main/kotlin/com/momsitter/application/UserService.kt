package com.momsitter.application

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService (

){
    // 회원가입
    // 사용자는 회원가입을 통해서 시터회원 또는 부모회원으로 가입할 수 있어야 합니다.
    @Transactional
    fun signup(): Long {
        return 0;
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