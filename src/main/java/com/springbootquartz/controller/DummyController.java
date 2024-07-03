package com.springbootquartz.controller;


import com.google.gson.Gson;
import com.microsoft.playwright.*;
import com.springbootquartz.dto.ApiResponse;
import com.springbootquartz.service.QuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DummyController {

    private final QuartzService quartzService;

    private final Gson gson = new Gson();
    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    @GetMapping(value="/dummy")
    public ResponseEntity<ApiResponse> addScheduleJob(@RequestParam(value="test") String test){
        String rtnStr = "test";

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setChannel("chrome")
        );

        Page page = browser.newPage();
        page.navigate("https://letcode.in/edit");
//        System.out.println(page.title());

        page.locator("#fullName").type("JKRYUDA");
        page.locator("#join").fill("HELLO");

        Locator locator = page.locator("#join");
        locator.press("End");
        locator.type(" Ryuda");
        locator.press("Tab");
        // Close
//        page.close();
//        browser.close();
//        playwright.close();


        return new ResponseEntity<>(new ApiResponse(rtnStr,"Success"), HttpStatus.OK);
    }

//    @RequestMapping(value = "/scraper", method = RequestMethod.GET)
    @GetMapping(value="/scraper")
    public ResponseEntity<ApiResponse> websiteScraper(@RequestParam(value="test") String test){
        log.info("JK> Start Scraper");
        String text = "";
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();

            // 웹사이트로 이동
            page.navigate("https://www.spojoy.com/live/?mct=toto");

            // 필요한 정보 가져오기
//            ElementHandle element = page.querySelector(".proto_title");
//            ElementHandle element = page.querySelector(".your-element-selector");
//            text = element.innerText();

            Locator locator = page.locator("#proto_title");
            text = locator.innerText();
            log.info("JK> text: {}", text);
            System.out.println();

            browser.close();
        }

        return new ResponseEntity<>(new ApiResponse(text,"Success"), HttpStatus.OK);
    }

    @GetMapping(value="/scraper/wise")
    public ResponseEntity<ApiResponse> wisetotoScraper(){
        log.info("JK> Start Scraper");
        String text = "";
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();

            // 웹사이트로 이동
            page.navigate("https://www.wisetoto.com/index.htm?tab_type=proto");

            // 필요한 정보 가져오기
//            ElementHandle element = page.querySelector(".proto_title");
//            ElementHandle element = page.querySelector(".your-element-selector");
//            text = element.innerText();

            Locator locator = page.locator(".info_text");
            List<String> textList = locator.allInnerTexts();
            text = textList.get(0);

            log.info("JK> textList: {}", gson.toJson(textList));
            System.out.println();

            browser.close();
        }

        return new ResponseEntity<>(new ApiResponse(text,"Success"), HttpStatus.OK);
    }
}


