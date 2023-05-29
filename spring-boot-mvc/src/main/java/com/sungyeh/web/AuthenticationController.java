package com.sungyeh.web;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sungyeh.config.GoogleConfig;
import com.sungyeh.config.LineConfig;
import com.sungyeh.config.OauthConfig;
import com.sungyeh.config.SystemConfig;
import com.sungyeh.repository.DepartmentRepository;
import com.sungyeh.repository.PersonRepository;
import com.sungyeh.service.CloudVisionService;
import com.sungyeh.service.TotpAuthenticatorService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * 需要驗證服務
 *
 * @author sungyeh
 */
@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    /**
     * 部門repository
     */
    @Resource
    private DepartmentRepository departmentRepository;
    /**
     * 使用者repository
     */
    @Resource
    private PersonRepository personRepository;
    /**
     * google cloud vision service
     */
    @Resource
    private CloudVisionService cloudVisionService;
    /**
     * totp service
     */
    @Resource
    private TotpAuthenticatorService totpAuthenticatorService;
    /**
     * google api key
     */
    @Value("${google.api.key}")
    private String key;
    /**
     * Google靜態資源
     */
    @Resource
    private GoogleConfig googleConfig;
    /**
     * Line靜態資源
     */
    @Resource
    private LineConfig lineConfig;
    /**
     * 認證伺服器靜態資源
     */
    @Resource
    private OauthConfig oauthConfig;
    /**
     * 系統設定靜態資源
     */
    @Resource
    private SystemConfig systemConfig;

    /**
     * JPA頁面
     *
     * @param model 注入modelandview
     * @return jpa.ftl
     */
    @GetMapping("jpa")
    public String jpa(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("department", departmentRepository.findByNo("RD"));
        return "jpa";
    }

    /**
     * MVC頁面
     *
     * @param model 注入modelandview
     * @return mvc.ftl
     */
    @GetMapping("mvc")
    public String mvc(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("department", departmentRepository.findByNo("RD"));
        return "mvc";
    }

    /**
     * Spring Security頁面
     *
     * @return security.ftl
     */
    @GetMapping("security")
    public String security() {
        return "security";
    }

    /**
     * Google map操作頁面
     *
     * @param model 注入modelandview
     * @return google-map.ftl
     */
    @GetMapping("google-map-demo")
    public String googleMapDemo(Model model) {
        model.addAttribute("key", this.key);
        return "google-map";
    }

    /**
     * Google map操作頁面
     *
     * @return google-map-index.ftl
     */
    @GetMapping("google-map")
    public String googleMap() {
        return "google-map-index";
    }

    /**
     * Cloud vision頁面
     *
     * @return cloud-vision.ftl
     */
    @GetMapping("cloud-vision")
    public String cloudVision() {
        return "cloud-vision";
    }

    /**
     * 偵測API
     *
     * @param para image base64 string
     * @return json string
     */
    @PostMapping("detect")
    @ResponseBody
    public String detect(@RequestParam("para") String para) {
        return cloudVisionService.send(para);
    }

    /**
     * TOTP頁面
     *
     * @param model 注入modelandview
     * @return authenticator.ftl
     */
    @GetMapping("authenticator")
    public String authenticator(Model model) {
        String url = totpAuthenticatorService.createUrl("PBCDIEBZ35BWWWAK", "user");

        byte[] image = getQRCodeImage(url, 200, 200);
        String qrcode = Base64.getEncoder().encodeToString(image);
        model.addAttribute("qrcode", "data:image/png;base64," + qrcode);
        return "authenticator";
    }

    /**
     * 檢查TOTP
     *
     * @param code totp code
     * @return boolean true/false
     */
    @GetMapping("totp-check/{code}")
    @ResponseBody
    public boolean totpCheck(@PathVariable("code") String code) {
        return totpAuthenticatorService.valid(Integer.parseInt(code), "PBCDIEBZ35BWWWAK");
    }

    /**
     * WebRTC頁面
     *
     * @return rtc.ftl
     */
    @GetMapping("rtc")
    public String rtc() {
        return "rtc";
    }

    /**
     * Dialogflow頁面
     *
     * @return dialogflow.ftl
     */
    @GetMapping("dialogflow")
    public String dialogflow() {
        return "dialogflow";
    }

    /**
     * Recaptcha頁面
     *
     * @return recaptcha.ftl
     */
    @GetMapping("recaptcha")
    public String recaptcha() {
        return "recaptcha";
    }

    /**
     * OpenID頁面
     *
     * @param model   注入modelandview
     * @param request 注入request
     * @return open-id.ftl
     */
    @GetMapping("open-id")
    public String openid(Model model, HttpServletRequest request) {
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        request.getSession().setAttribute("state", state);
        model.addAttribute("state", state);
        model.addAttribute("nonce", UUID.randomUUID().toString());
        model.addAttribute("clientId", googleConfig.getId());
        model.addAttribute("clientSecret", googleConfig.getSecret());
        model.addAttribute("redirect", googleConfig.getRedirect());
        model.addAttribute("lineClientId", lineConfig.getId());
        model.addAttribute("lineClientSecret", lineConfig.getSecret());
        model.addAttribute("lineRedirect", lineConfig.getRedirect());
        model.addAttribute("systemUrl", systemConfig.getUrl());
        return "open-id";
    }

    /**
     * 認證伺服器頁面
     *
     * @param model 注入modelandview
     * @return authorization.ftl
     */
    @GetMapping("authorization")
    public String authorization(Model model) {
        model.addAttribute("authorizationServer", oauthConfig.getAuthorizationServer());
        model.addAttribute("redirect", oauthConfig.getRedirect());
        return "authorization";
    }

    /**
     * Swagger頁面
     *
     * @return swagger.ftl
     */
    @GetMapping("swagger")
    public String swagger() {
        return "swagger";
    }

    /**
     * Line頁面
     *
     * @return line.ftl
     */
    @GetMapping("line")
    public String line() {
        return "line";
    }

    /**
     * 靜態分析頁面
     *
     * @return static-analysis.ftl
     */
    @GetMapping("static-analysis")
    public String staticAnalysis() {
        return "static-analysis";
    }

    /**
     * Web Socket頁面
     *
     * @return web-socket.ftl
     */
    @GetMapping("web-socket")
    public String webSocket() {
        return "web-socket";
    }

    /**
     * Asciidoc頁面
     *
     * @return asciidoc.ftl
     */
    @GetMapping("asciidoc")
    public String asciidoc() {
        return "asciidoc";
    }

    /**
     * QRCode產生器
     *
     * @param text   文字
     * @param width  寬度
     * @param height 高度
     * @return QRCode byte array
     */
    private byte[] getQRCodeImage(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            log.error(e.getMessage());
        }
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFE95504);
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return pngOutputStream.toByteArray();
    }
}
