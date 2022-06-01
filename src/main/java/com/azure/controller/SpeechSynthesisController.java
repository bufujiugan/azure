package com.azure.controller;

import com.azure.dao.SpeechSynthesis;
import com.azure.speech.SpeechSynthesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

/**
 * 文字转语音
 */
@Controller
@RequestMapping("/speech")
public class SpeechSynthesisController {

@Autowired
private SpeechSynthesisService speechSynthesisService;

    /**
     * 文本转语音
     * @param speechSynthesis
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/text2Speech")
    public void text2Speech(@RequestBody SpeechSynthesis speechSynthesis) throws ExecutionException, InterruptedException {
        speechSynthesisService.text2Speech(speechSynthesis);
    }

    /**
     * 文本转语音并下载
     * @param speechSynthesis
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/text2SpeechDown")
    public void text2SpeechDown(@RequestBody SpeechSynthesis speechSynthesis) throws ExecutionException, InterruptedException {
        speechSynthesisService.text2SpeechDown(speechSynthesis);
    }

}
