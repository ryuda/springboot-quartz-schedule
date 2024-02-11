package com.springbootquartz.controller;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.springbootquartz.dto.ApiResponse;
import com.springbootquartz.service.QuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DummyController {

    private final QuartzService quartzService;

    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    @GetMapping(value="/dummy")
    public ResponseEntity<ApiResponse> addScheduleJob(@RequestParam(value="test") String test){
        String rtnStr = "test";

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setChannel("chrome")
        );
        Page page = browser.newPage();
        page.navigate("https://letcode.in/");
        System.out.println(page.title());

        // Close
        page.close();
        browser.close();
        playwright.close();


        return new ResponseEntity<>(new ApiResponse(rtnStr,"Success"), HttpStatus.OK);
    }

}

