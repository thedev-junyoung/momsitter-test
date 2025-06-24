package com.momsitter.infrastructure

import com.momsitter.domain.PasswordEncoder
import org.mindrot.jbcrypt.BCrypt

class BCryptPasswordEncoderAdapter : com.momsitter.domain.PasswordEncoder {

    override fun encode(raw: String): String {
        return BCrypt.hashpw(raw, BCrypt.gensalt()) // salt 자동 생성
    }

    override fun matches(raw: String, encoded: String): Boolean {
        return BCrypt.checkpw(raw, encoded)
    }
}
