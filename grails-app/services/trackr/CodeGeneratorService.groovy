package trackr

import grails.transaction.Transactional

import java.security.SecureRandom

@Transactional
class CodeGeneratorService {

    SecureRandom random = new SecureRandom();

    String newCode() {
        return new BigInteger(130, random).toString(32)
    }

    String newCode(int length) {
        if (length > 0) {
            String code = newCode()
            if (code.length() > length) {
                code = code.substring(code.length() - length)
            }
            return code
        } else {
            return null
        }
    }

    String newCodeUppercase() {
        return newCode().toUpperCase()
    }

    String newCodeUppercase(int length) {
        return newCode(length).toUpperCase()
    }

    String newCodeForDevice() {
        return newCodeUppercase(6)
    }
}
