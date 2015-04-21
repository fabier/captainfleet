package trackr

import grails.transaction.Transactional

import java.text.ParseException
import java.text.SimpleDateFormat

@Transactional
class ParserService {

    Double tryParseDouble(String s) {
        Double val = null
        if (s) {
            try {
                val = Double.parseDouble(s)
            } catch (NumberFormatException e) {
                // Quiet fail
            }
        }
        return val
    }

    Float tryParseFloat(String s) {
        Float val = null
        if (s) {
            try {
                val = Float.parseFloat(s)
            } catch (NumberFormatException e) {
                // Quiet fail
            }
        }
        return val
    }

    Boolean tryParseBoolean(String s) {
        return Boolean.parseBoolean(s)
    }

    Long tryParseLong(String s) {
        Long val = null
        if (s) {
            try {
                val = Long.parseLong(s)
            } catch (NumberFormatException e) {
                // Quiet fail
            }
        }
        return val
    }

    Date tryParseDate(String s) {
        Date date = null
        if (s) {
            try {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(s)
            } catch (ParseException pe) {
                // Silent
            }
        }
        return date
    }
}
