package com.sungyeh.web;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
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
    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private PersonRepository personRepository;

    @Resource
    private CloudVisionService cloudVisionService;

    @Resource
    private TotpAuthenticatorService totpAuthenticatorService;


    @Value("${google.api.key}")
    private String key;

    @Value("${google.oauth.id}")
    private String clientId;

    @Value("${google.oauth.secret}")
    private String clientSecret;

    @Value("${google.oauth.redirect}")
    private String redirect;

    @Value("${line.oauth.id}")
    private String lineClientId;

    @Value("${line.oauth.secret}")
    private String lineClientSecret;

    @Value("${line.oauth.redirect}")
    private String lineRedirect;

    @GetMapping("jpa")
    public String jpa(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("department", departmentRepository.findByNo("RD"));
        return "jpa";
    }

    @GetMapping("mvc")
    public String mvc(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("department", departmentRepository.findByNo("RD"));
        return "mvc";
    }

    @GetMapping("security")
    public String security() {
        return "security";
    }

    @GetMapping("google-map")
    public String googleMap(Model model) {
        model.addAttribute("key", this.key);
        return "google-map";
    }

    @GetMapping("cloud-vision")
    public String cloudVision() {
        return "cloud-vision";
    }

    @PostMapping("detect")
    @ResponseBody
    public String detect(@RequestParam("para") String para) {
        return cloudVisionService.send(para);
    }

    @GetMapping("authenticator")
    public String authenticator(Model model) {
        String url = totpAuthenticatorService.createUrl("PBCDIEBZ35BWWWAK", "user");

        byte[] image = getQRCodeImage(url, 200, 200);
        String qrcode = Base64.getEncoder().encodeToString(image);
        model.addAttribute("qrcode", "data:image/png;base64," + qrcode);
        return "authenticator";
    }

    @GetMapping("totp-check/{code}")
    @ResponseBody
    public boolean totpCheck(@PathVariable("code") String code) {
        return totpAuthenticatorService.valid(Integer.parseInt(code), "PBCDIEBZ35BWWWAK");
    }

    @GetMapping("rtc")
    public String rtc() {
        return "rtc";
    }

    @GetMapping("dialogflow")
    public String dialogflow() {
        return "dialogflow";
    }

    @GetMapping("recaptcha")
    public String recaptcha() {
        return "recaptcha";
    }

    @GetMapping("open-id")
    public String openid(Model model, HttpServletRequest request) {
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        request.getSession().setAttribute("state", state);
        model.addAttribute("state", state);
        model.addAttribute("nonce", UUID.randomUUID().toString());
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientSecret", clientSecret);
        model.addAttribute("redirect", redirect);
        model.addAttribute("lineClientId", lineClientId);
        model.addAttribute("lineClientSecret", lineClientSecret);
        model.addAttribute("lineRedirect", lineRedirect);

        return "open-id";
    }

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
