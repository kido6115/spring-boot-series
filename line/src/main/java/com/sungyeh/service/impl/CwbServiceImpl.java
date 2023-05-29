package com.sungyeh.service.impl;

import com.sungyeh.config.LineCustomConfig;
import com.sungyeh.service.CwbImageAlt;
import com.sungyeh.service.CwbService;
import com.sungyeh.service.FirebaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * CwbServiceImpl
 *
 * @author sungyeh
 */
@Service("com.sungyeh.service.impl.CwbServiceImpl")
@Slf4j
public class CwbServiceImpl implements CwbService {

    private final static String URL = "https://www.cwb.gov.tw";

    @Resource
    private LineCustomConfig lineCustomConfig;

    @Resource
    private FirebaseService firebaseService;

    public List<String> getImage() {
        Document doc;
        try {
            doc = Jsoup.connect(URL + "/V8/C/").get();
        } catch (IOException e) {
            log.error("error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return Arrays.asList(
                getImageByAlt(doc, CwbImageAlt.RADAR),
                getImageByAlt(doc, CwbImageAlt.RAINFALL),
                getImageByAlt(doc, CwbImageAlt.UV),
                getImageByAlt(doc, CwbImageAlt.TEMPERATURE)
        );
    }


    private String getImageByAlt(Document document, CwbImageAlt alt) {
        String target = String.format("img[alt=\"%s\"]", alt.getAlt());
        log.info("target: {}", target);
        return URL + document.select(target).get(0).attr("src");
    }

}
