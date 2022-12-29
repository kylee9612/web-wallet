//package com.xrp.util;
//
//import com.upts.crypto.rest.UPTSCrypto;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Random;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Component
//public class SecurityUtil {
//
//    static String ip;
//    static int port = 0;
//    static String signtype;
//
//    final static String key = "aliwidnvkwirkladw";
//    final static IvParameterSpec iv = new IvParameterSpec(key.substring(0, 16).getBytes());
//
//
//    @Value("#{prop['crypto.ip']}")
//    public void setip(String ksign_ip) {
//        ip = ksign_ip;
//    }
//
//    @Value("#{prop['crypto.port']}")
//    public void setport(int ksign_port) {
//        port = ksign_port;
//    }
//
//    @Value("#{prop['security.option']}")
//    public void checkSeed(String signconfig) {
//        signtype = signconfig;
//    }
//
//
//    /**
//     * Sha-256 암호화
//     *
//     * @param str
//     * @return
//     */
//    public static String encryptSHA256(String str) {
//        String sha = "";
//
//        try {
//            MessageDigest sh = MessageDigest.getInstance("SHA-256");
//            sh.update(str.getBytes());
//            byte byteData[] = sh.digest();
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < byteData.length; i++) {
//                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            sha = sb.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            //e.printStackTrace();
////			log.debug("Encrypt Error - NoSuchAlgorithmException");
//            sha = null;
//        }
//
//        return sha;
//    }
//
//
//    /**
//     * 비밀번호 최소 8자리 25자리 이하 숫자, 대문자, 소문자, 특수문자 각각 1개 이상 포함 Validation 체크
//     *
//     * @param pw
//     * @return
//     */
//    public static boolean validationPasswd(String pw) {
////		log.debug("pw : "+pw);
//        //TODO : 다른 특수 기호 예시 `, ~ 확인필요
//        //http://highcode.tistory.com/6
//        /**
//         ^ 으로 우선 패턴의 시작을 알립니다.
//         [0-9] 괄호사이에 두 숫자를 넣어 범위를 지정해줄 수 있습니다.
//         * 를 넣으면 글자 수를 상관하지 않고 검사합니다.
//         $ 으로 패턴의 종료를 알립니다.
//         1) 숫자만 : ^[0-9]*$
//         2) 영문자만 : ^[a-zA-Z]*$
//         3) 한글만 : ^[가-힣]*$
//         4) 영어 & 숫자만 : ^[a-zA-Z0-9]*$
//         5) E-Mail : ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$
//         6) 휴대폰 : ^01(?:0|1|[6-9]) - (?:\d{3}|\d{4}) - \d{4}$
//         7) 일반전화 : ^\d{2,3} - \d{3,4} - \d{4}$
//         8) 주민등록번호 : \d{6} \- [1-4]\d{6}
//         9) IP 주소 : ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3})
//         * */
//        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\d)(?=.*[#?!@$%\\^&*-~])[A-Za-z\\d#?!@$%\\^&*-~]{8,25}$");
//
//        Matcher m = p.matcher(pw);
//        if (m.matches()) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 문자열에 공백여부 체크 있을경우 true 없을경우 false
//     *
//     * @param
//     * @return
//     */
//    public static boolean validationSpace(String spaceCheck) {
//        for (int i = 0; i < spaceCheck.length(); i++) {
//            if (spaceCheck.charAt(i) == ' ')
//                return true;
//        }
//        return false;
//    }
//
//    /**
//     * 문자열이 null이거나 비어있는 경우 true 아닌경우 false
//     *
//     * @param emptyCheck
//     * @return
//     */
//    public static boolean validationEmpty(String emptyCheck) {
//        if (StringUtils.isEmpty(emptyCheck) == true) {
//            return true;
//
//        } else {
//            return false;
//
//        }
//    }
//
//    /**
//     * 아이디 (특수기호는 $ ! % * # _ - 만 사용) 외 포함되어있으면 false 없을경우 true
//     *
//     * @param id
//     * @return
//     */
//    public static boolean validationId(String id) {
//        Pattern p = Pattern.compile("(?=.*[\\\\{\\\\}\\\\[\\\\]\\\\,;:&|\\\\)~`^+<>\\\\\\\\\\\\\\\\=\\\\(\\\\'\\\\\\\"\\\\])");
//        Matcher m = p.matcher(id);
//
//        if (m.find()) {
//            return false;
//        }
//        return true;
//    }
//
//    public static Key getAESKey() throws Exception {
//
//        Key keySpec;
//
//        byte[] keyBytes = new byte[16];
//        byte[] b = key.getBytes("UTF-8");
//
//        int len = b.length;
//        if (len > keyBytes.length) {
//            len = keyBytes.length;
//        }
//
//        System.arraycopy(b, 0, keyBytes, 0, len);
//        keySpec = new SecretKeySpec(keyBytes, "AES");
//
//        return keySpec;
//    }
//
//    // 복호화
//    public static String decryptKSignBefore(String table, String column, String enStr) throws Exception {
//        String decStr = "-";
//        if (enStr != null) {
//            Key keySpec = getAESKey();
//            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            c.init(Cipher.DECRYPT_MODE, keySpec, iv);
//            byte[] byteStr = Base64.decodeBase64(enStr.getBytes("UTF-8"));
//            decStr = new String(c.doFinal(byteStr), "UTF-8");
//        }
//        return decStr;
//    }
//
//    public static UPTSCrypto crypto = null;
//
//    // 암호화
//    public static String encryptKSign(String table, String column, String value) throws Exception {
//        String result = "-";
//        if ("seed".equals(signtype)) {
//            Key keySpec = getAESKey();
//            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            c.init(Cipher.ENCRYPT_MODE, keySpec, iv);
//            byte[] encrypted = c.doFinal(value.getBytes("UTF-8"));
//            String enStr = new String(Base64.encodeBase64(encrypted));
//            return enStr;
//        } else if ("upsign".equals(signtype)) {
//            if (crypto == null) {
//                crypto = new UPTSCrypto();
//                crypto = UPTSCrypto.getInstanceDomain(ip, port);
//            }
//            result = crypto.encryptCEV(table, column, value);
//
//            if (result == null) {
//                throw new Exception();
//            }
//        }
//        return result;
//    }
//
//    // 복호화
//    public static String decryptKSign(String table, String column, String value) throws Exception {
//        String result = "";
//        if ("seed".equals(signtype)) {
//            if (value != null) {
//                String decStr = "-";
//                Key keySpec = getAESKey();
//                Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                c.init(Cipher.DECRYPT_MODE, keySpec, iv);
//                byte[] byteStr = Base64.decodeBase64(value.getBytes("UTF-8"));
//                decStr = new String(c.doFinal(byteStr), "UTF-8");
//                return decStr;
//            }
//        } else if ("upsign".equals(signtype)) {
//            if (value != null) {
//                if (crypto == null) {
//                    crypto = new UPTSCrypto();
//                    crypto = UPTSCrypto.getInstanceDomain(ip, port);
//                }
//                result = crypto.decryptCEV(table, column, value);
//                if (result == null) {
//                    throw new Exception();
//                }
//            }
//        }
//        return result;
//    }
//
//    // plainTex 복호화
//    public static String decryptSeedCEV(String plainTex, String cryptoIp, int cryptoPort) throws Exception {
//        String result = "";
//        if (crypto == null) {
//            crypto = new UPTSCrypto();
//            crypto = UPTSCrypto.getInstanceDomain(cryptoIp, cryptoPort);
//        }
//        result = crypto.decryptSeedCEV(plainTex);
//
//        if (result == null) {
//            throw new Exception();
//        }
//        return result;
//    }
//
//    // 암호화 Test
//    public static String encryptSeedCEV(String plainTex) throws Exception {
//        String result = "";
//        if (crypto == null) {
//            crypto = new UPTSCrypto();
//            crypto = UPTSCrypto.getInstanceDomain(ip, port);
//        }
//        result = crypto.encryptSeedCEV(plainTex);
//        if (result == null) {
//            throw new Exception();
//        }
//        return result;
//    }
//
//    public static String decryptSeedCEV(String plainTex) throws Exception {
//        String result = "";
//        if (crypto == null) {
//            crypto = new UPTSCrypto();
//            crypto = UPTSCrypto.getInstanceDomain(ip, port);
//        }
//        result = crypto.decryptSeedCEV(plainTex);
//        if (result == null) {
//            throw new Exception();
//        }
//        return result;
//    }
//
//    public static String makePasswordHash(String password, String salt) throws Exception {
//        String passwordStr = String.format("%s%s", salt, password);
//
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        md.update(passwordStr.getBytes(StandardCharsets.UTF_8));
//
//        String encodeStr = new String(Base64.encodeBase64(md.digest()));
//
//        return encodeStr;
//    }
//
//    public static String generateSalt() throws Exception {
//        Random rnd = new Random();
//
//        byte[] bt = new byte[0x10];
//        rnd.nextBytes(bt);
//
//        String encodeStr = new String(Base64.encodeBase64(bt));
//
//        return encodeStr;
//    }
//
//    public static void main(String[] args) throws Exception {
//        System.out.println("==============START==============");
//
//        System.out.println("decryptSeedCEV ::: " + customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+7XyB99o+fRyBFczOE8paxBE3rpn53E4xUhVae36quxxTuT6MX6W9TNqpl2Df7dWTW4G2WQ4PsXEx1DkZdTA7RO4rXpIplDFWR/iQgeZTS29nAMAcXm9MBtP0BWmv5EGbXJ5wDb43wi4o5TTtg6mQB0iHU0yPEy0AFeCu5bc+kGjvDygjM1B2o8ndk0bH2/OYg=="));
//        System.out.println("decryptSeedCEV ::: " + customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+7XyB99o+fRyBFczOE8paxBAhO6NZ+P+hPdPQxHjOtFaeZxVbcwwLpCVidqKitLniSaYoUPKA1BcCvM1XGxz52cbpX51FD4g/hLNfPDUMnsP5KkxQ2O5LfwFZPBOff9bW6nNes2P3Yhn3uVBT5p46XGRb8qR2zW6Bndmb7wyqNF3i+H27NC/S8W1G8wtrxa92A=="));
//        System.out.println("decryptSeedCEV ::: " + customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+7XyB99o+fRyBFczOE8paxCviq9gFFoun8dZzkj91sxJFYDJScxUwyreLAcHKhHqdw4j2I0Ak/Sj9W4wSFdV2EJruZ1OS8ycDumlf8zJC1qZGeEnh9aMyAGxgDpgSnfggS7QCod/pSmNVR1G66N06rXTu1t259XztAauznoo7kocJbCX/oVvtjPP5lo3qQwGhQ=="));
//
////		System.out.println(customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+7XyB99o+fRyBFczOE8paxDOl4L3H9EalktI7Up0W2oXeZxVbcwwLpCVidqKitLniSaYoUPKA1BcCvM1XGxz52cbpX51FD4g/hLNfPDUMnsP5KkxQ2O5LfwFZPBOff9bW6nNes2P3Yhn3uVBT5p46XGRb8qR2zW6Bndmb7wyqNF3i+H27NC/S8W1G8wtrxa92A=="));
////		System.out.println(customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+0HGJMKPeoO2oYswVGNcr4+viq9gFFoun8dZzkj91sxJFYDJScxUwyreLAcHKhHqdw4j2I0Ak/Sj9W4wSFdV2EJruZ1OS8ycDumlf8zJC1qZGeEnh9aMyAGxgDpgSnfggS7QCod/pSmNVR1G66N06rXTu1t259XztAauznoo7kocJbCX/oVvtjPP5lo3qQwGhQ=="));
////		System.out.println(customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+3IFY59QB3RI9AL0vPoO/gMVwTqWIjcQiNib0AgBbqUm0kFyF7WSznftcxn+mD9mjz8RrtfeR2bM1JjluKN/pzivj4MQ3z9MUEVqI91dyJRHbWOzOSd1E1onw/2J/BGl5kCUs8xKNh+3u84bWpc/1rLyRkUH3IcnKxO/gp1JIu8EHUF+Uh8rbj6BuWNosSj6tEL0WbXRF0ieR6jaxgdyCxwe0oxzJId7IsEhjy7VFxQk"));
////		System.out.println(customDecryptSeedCEV("dfuzIlAObxx6UI2r6CZo8T5oFDkO3rn0fwzCCFVIsfBknuvl2q/pT2efQRAtx7D2HCq0k8MHIn37mGEaCC9/3I3o5ICjneZaN3DJ32nWVzcEJsrtkhC0nQylcI8OS0XIiw3tVqP336srhUV9bAA/vGN0B9XnHWXvENwlqPlN6OHOCeyAFWa7IlZrUkjfW9yIvJvm+BOemTmTCt2B2neBhdhz7Aw9S4+Nmh22dhWW7oMdpK406uLXrUOe7zE4lk9ze347srxinLYQbN1ETxENmAORytFrm5l6bxi70QaFiddDQLx9UXLYF1IBZmVdQtYY"));
////		System.out.println(customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+3IASVEqgQ4WXpm/8vT4X6mviq9gFFoun8dZzkj91sxJFYDJScxUwyreLAcHKhHqdw4j2I0Ak/Sj9W4wSFdV2EJruZ1OS8ycDumlf8zJC1qZGeEnh9aMyAGxgDpgSnfggS7QCod/pSmNVR1G66N06rXTu1t259XztAauznoo7kocJbCX/oVvtjPP5lo3qQwGhQ=="));
////		System.out.println(customDecryptSeedCEV("mpvJbJ33wamd6NbLe6J9+ykgTeN/f/7tpG+vKXAYcPUVwTqWIjcQiNib0AgBbqUm0kFyF7WSznftcxn+mD9mjz8RrtfeR2bM1JjluKN/pzivj4MQ3z9MUEVqI91dyJRHbWOzOSd1E1onw/2J/BGl5kCUs8xKNh+3u84bWpc/1rLyRkUH3IcnKxO/gp1JIu8EHUF+Uh8rbj6BuWNosSj6tEL0WbXRF0ieR6jaxgdyCxwe0oxzJId7IsEhjy7VFxQk"));
////		System.out.println(customDecryptSeedCEV("xW1s412eTxpvoWVmVFg8zQ=="));
////		System.out.println(customDecryptSeedCEV("PvZrkTNZ3j3Nx2YyF1kL/A=="));
//        System.out.println(customDecryptSeedCEV("dfuzIlAObxx6UI2r6CZo8T5oFDkO3rn0fwzCCFVIsfBknuvl2q/pT2efQRAtx7D2HCq0k8MHIn37mGEaCC9/3I3o5ICjneZaN3DJ32nWVzcEJsrtkhC0nQylcI8OS0XIiw3tVqP336srhUV9bAA/vGN0B9XnHWXvENwlqPlN6OHOCeyAFWa7IlZrUkjfW9yIvJvm+BOemTmTCt2B2neBhdhz7Aw9S4+Nmh22dhWW7oMdpK406uLXrUOe7zE4lk9ze347srxinLYQbN1ETxENmAORytFrm5l6bxi70QaFiddDQLx9UXLYF1IBZmVdQtYY"));
//        System.out.println(customDecryptSeedCEV(""));
//        System.out.println(customDecryptSeedCEV("dfuzIlAObxx6UI2r6CZo8U7birF8q9q/dUqsRKa3KyBknuvl2q/pT2efQRAtx7D2HCq0k8MHIn37mGEaCC9/3I3o5ICjneZaN3DJ32nWVzcEJsrtkhC0nQylcI8OS0XIiw3tVqP336srhUV9bAA/vGN0B9XnHWXvENwlqPlN6OHOCeyAFWa7IlZrUkjfW9yIvJvm+BOemTmTCt2B2neBhdhz7Aw9S4+Nmh22dhWW7oMdpK406uLXrUOe7zE4lk9ze347srxinLYQbN1ETxENmAORytFrm5l6bxi70QaFiddDQLx9UXLYF1IBZmVdQtYY"));
//        System.out.println(customDecryptSeedCEV(""));
//        System.out.println(customDecryptSeedCEV("dfuzIlAObxx6UI2r6CZo8T5oFDkO3rn0fwzCCFVIsfBknuvl2q/pT2efQRAtx7D2HCq0k8MHIn37mGEaCC9/3JFAXLKZqV/LljUeBweiNDWnnjV388CidetbwF5mAy8kTo/af/wiumH3JFyQ8XCkjOL5iGXR9e1L8APaBl8iRUMJlgXj3foxRLlzVZN8j71m/0xW1LpvJsqxLHN7Wx+T6TMggzcj3ewBoVaY3YME2H9HneEdsMZvxTn8G1L2Qf4pVSX0nb+UBXtlIKPfPqqrBy25PIGJDgVztwkJEXB+su9f/CrJsgqrhBXvs4a42Z7r"));
//        System.out.println(customDecryptSeedCEV(""));
//        System.out.println(customDecryptSeedCEV("dfuzIlAObxx6UI2r6CZo8T5oFDkO3rn0fwzCCFVIsfBknuvl2q/pT2efQRAtx7D2HCq0k8MHIn37mGEaCC9/3PDn/BpXoiEWhSUhmrZpaU/g7l4b2Pj5+rplm0premDgBdw0osHoRxoggkSTm0j317srSy4HZiCpbPrHq7LY7vpnZregaC5tOZpgqKJBbfXz51sXC0CEUl3BL0FVTGDcy5Dmhkr3H3wyuuR5nD24plKQDebZD6KCo++rrnJcZ1/9//ImR7a7GrM4pzrUVf1Rv0Ymk76vpP7wk2vWHbXHY/0DDemIxhFD0P50DbBMmHKI"));
//        System.out.println(customDecryptSeedCEV(""));
//        System.out.println(customDecryptSeedCEV("bc1zVZ8Ux9PWaqbnqQqGAg=="));
//        System.out.println(customDecryptSeedCEV(""));
//        System.out.println(customDecryptSeedCEV("VX78ROMfkFqM3f6+Pq9MnyMnOLBl5HgPuPCmpSB8R9A="));
//        System.out.println(customDecryptSeedCEV("") + "11111123213");
//        System.out.println(customDecryptSeedCEV("wLXkzC4J7QTnBhu0HiGWGA=="));
//        System.out.println(customDecryptSeedCEV("r8E+Oet6KgbTctE9wdzitIcUz8nbHU4SgsdFg1ZYg2U="));
//        System.out.println(customDecryptSeedCEV(""));
//        System.out.println(customDecryptSeedCEV(""));
//        System.out.println(customDecryptSeedCEV("YWu9ABVtT7dLzuwcdA0urA=="));
//        System.out.println(customDecryptSeedCEV("YTDZpOYzLJG7GXp0D/r7e/DJTTJhRFzlZSLD7k+QL78="));
//
//        System.out.println(customDecryptSeedCEV("nA6yXAhxBtD/JLo+eMb8U6IxTAOvIcEQcxnTJRO6MkxmBtKrN1fCXy8vDnm8ooyg1T7YMAjEUlttu4vzp+98k9+Z1DPN2Mb5QWFGFFr08EpOkhobjEW6P+jat0lxlV22eZxVbcwwLpCVidqKitLniSaYoUPKA1BcCvM1XGxz52cbpX51FD4g/hLNfPDUMnsP5KkxQ2O5LfwFZPBOff9bW6nNes2P3Yhn3uVBT5p46XGRb8qR2zW6Bndmb7wyqNF3i+H27NC/S8W1G8wtrxa92A=="));
////
////		System.out.println(customDecryptSeedCEV("LrruvN4uTvczpYy2/GLd4Q=="));
////		System.out.println("1234444st ::: " + customEncryptSeedCEV("jdbc:mysql://172.26.8.95:3306/AML_RBA?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true"));
//
////		System.out.println(customEncryptSeedCEV("jdbc:mysql://127.0.0.1:7101/upts_batch?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul"));
////		System.out.println(customEncryptSeedCEV("jdbc:mysql://127.0.0.1:7103/upts_wallet?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul"));
////		System.out.println(customEncryptSeedCEV("jdbc:mysql://127.0.0.1:7107/AML_RBA?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true"));
////
////
////		System.out.println(customEncryptSeedCEV("jdbc:mysql://192.168.0.176:3306/upts_batch?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul"));
////		System.out.println(customEncryptSeedCEV("jdbc:mysql://192.168.0.176:3306/upts_wallet?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul"));
////		System.out.println(customEncryptSeedCEV("jdbc:mysql://192.168.0.176:7107/AML_RBA?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true"));
////		System.out.println(customEncryptSeedCEV("upchain"));
////		System.out.println(customEncryptSeedCEV(""));
//        System.out.println(customEncryptSeedCEV("cbwallet"));
//        System.out.println(customEncryptSeedCEV(""));
//        System.out.println(customEncryptSeedCEV("un28mtw5pg154nd9f1zgeavqe85k9p"));
//        System.out.println(customEncryptSeedCEV("") + "local");
//        System.out.println(customEncryptSeedCEV("jdbc:mysql://127.0.0.1:33308/upts?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true"));
//        System.out.println(customEncryptSeedCEV("jdbc:mysql://127.0.0.1:43307/?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true"));
//        System.out.println(customEncryptSeedCEV("dbadmin"));
//        System.out.println(customEncryptSeedCEV("Coinbit#dba12~!"));
////		System.out.println(customEncryptSeedCEV(""));
////		System.out.println(customEncryptSeedCEV("Coinbit#dba12~!"));
//        System.out.println(customEncryptSeedCEV("jdbc:mysql://d-wallet-k.cxskmjj18ljm.ap-northeast-2.rds.amazonaws.com:3306/upts_wallet?useUnicode=true&autoReconnect=true&useSSL=false&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true"));
//        System.out.println(customEncryptSeedCEV("AML_RBA"));
//        System.out.println(customEncryptSeedCEV("Aml_rb!?2"));
//
//        System.out.println(customDecryptKSign("MEMBER_DETAIL_HISTORY", "MEMO", "dfuzIlAObxx6UI2r6CZo8T5oFDkO3rn0fwzCCFVIsfBknuvl2q/pT2efQRAtx7D2HCq0k8MHIn37mGEaCC9/3I3o5ICjneZaN3DJ32nWVzcEJsrtkhC0nQylcI8OS0XIiw3tVqP336srhUV9bAA/vGN0B9XnHWXvENwlqPlN6OHOCeyAFWa7IlZrUkjfW9yIvJvm+BOemTmTCt2B2neBhdhz7Aw9S4+Nmh22dhWW7oMdpK406uLXrUOe7zE4lk9ze347srxinLYQbN1ETxENmAORytFrm5l6bxi70QaFiddDQLx9UXLYF1IBZmVdQtYY"));
//        System.out.println(customDecryptSeedCEV("dfuzIlAObxx6UI2r6CZo8T5oFDkO3rn0fwzCCFVIsfBknuvl2q/pT2efQRAtx7D2HCq0k8MHIn37mGEaCC9/3I3o5ICjneZaN3DJ32nWVzcEJsrtkhC0nQylcI8OS0XIiw3tVqP336srhUV9bAA/vGN0B9XnHWXvENwlqPlN6OHOCeyAFWa7IlZrUkjfW9yIvJvm+BOemTmTCt2B2neBhdhz7Aw9S4+Nmh22dhWW7oMdpK406uLXrUOe7zE4lk9ze347srxinLYQbN1ETxENmAORytFrm5l6bxi70QaFiddDQLx9UXLYF1IBZmVdQtYY"));
//        System.out.println(customEncryptSeedCEV("Coinbit#dba12~!"));
////		System.out.println("1234444st ::: " + customEncryptSeedCEV("upchain"));
////		System.out.println("1234444st ::: " + customEncryptSeedCEV("UP!MWne4pRFjmPGcm6v"));
//
//
//        System.out.println("==============NEXT================");
//
////		System.out.println("1234444st ::: " + customEncryptSeedCEV("coinbit"));
////		System.out.println("1234444st ::: " + customEncryptSeedCEV("coinbit#123"));
////		System.out.println("pw ::: " + encryptSHA256("coinbit1234!@"));
//        System.out.println("==============END================");
//    }
//
//    public static String customDecryptSeedCEV(String plainTex) throws Exception {
//        String result = "";
//        if (crypto == null) {
//            crypto = new UPTSCrypto();
//            crypto = UPTSCrypto.getInstanceDomain("http://127.0.0.1", 9050);
//        }
//        result = crypto.decryptSeedCEV(plainTex);
//
//        if (result == null) {
//            throw new Exception();
//        }
//        return result;
//    }
//
//    // 암호화 Test
//    public static String customEncryptSeedCEV(String plainTex) throws Exception {
//        String result = "";
//        if (crypto == null) {
//            crypto = new UPTSCrypto();
//            crypto = UPTSCrypto.getInstanceDomain("http://127.0.0.1", 9050);
//        }
//        result = crypto.encryptSeedCEV(plainTex);
//        if (result == null) {
//            throw new Exception();
//        }
//        return result;
//    }
//
//
//    // 암호화
//    public static String customEncryptKSign(String table, String column, String value) throws Exception {
//        String result = "-";
//        if (crypto == null) {
//            crypto = new UPTSCrypto();
//            crypto = UPTSCrypto.getInstanceDomain("http://127.0.0.1", 9050);
//        }
//        result = crypto.encryptCEV(table, column, value);
//
//        if (result == null) {
//            throw new Exception();
//        }
//        return result;
//    }
//
//    // 복호화
//    public static String customDecryptKSign(String table, String column, String value) throws Exception {
//        String result = "";
//        if (value != null) {
//            if (crypto == null) {
//                crypto = new UPTSCrypto();
//                crypto = UPTSCrypto.getInstanceDomain("http://127.0.0.1", 9050);
//            }
//            result = crypto.decryptCEV(table, column, value);
//            if (result == null) {
//                throw new Exception();
//            }
//        }
//        return result;
//    }
//}
