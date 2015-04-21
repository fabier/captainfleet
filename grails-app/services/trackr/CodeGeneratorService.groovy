package trackr

import grails.transaction.Transactional

import java.security.SecureRandom

@Transactional
class CodeGeneratorService {

    private SecureRandom random = new SecureRandom();

    public String newCode() {
        return new BigInteger(130, random).toString(32)
    }

    public String newCode(int length) {
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

    public String newCodeUppercase() {
        return newCode().toUpperCase()
    }

    public String newCodeUppercase(int length) {
        return newCode(length).toUpperCase()
    }

    public String newCodeForDevice() {
        return newCodeUppercase(6)
    }
}
