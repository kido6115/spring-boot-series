package com.sungyeh.service;

/**
 * TotpAuthenticatorService
 *
 * @author sungyeh
 */
public interface TotpAuthenticatorService {

    /**
     * 產生QRCode
     *
     * @param secret 金鑰
     * @param userId 使用者ID
     * @return QRCode URL
     */
    String createUrl(String secret, String userId);

    /**
     * 產生金鑰
     *
     * @return 金鑰
     */
    String createKey();

    /**
     * 驗證
     *
     * @param code   驗證碼
     * @param secret 金鑰
     * @return 驗證是否成功
     */
    boolean valid(int code, String secret);
}
