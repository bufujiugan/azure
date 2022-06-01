package com.azure.controller;

import com.azure.dao.SpeechRecognition;
import com.azure.speech.SpeechRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.ExecutionException;

/**
 * 语音转文字
 */
@Controller("/speechRecognition")
public class SpeechRecognitionController {

    @Autowired
    private SpeechRecognitionService speechRecognitionService;

    @PostMapping("/recognizeFromMicrophone")
    public void recognizeFromMicrophone(@RequestBody SpeechRecognition speechRecognition) throws ExecutionException, InterruptedException {
        speechRecognitionService.recognizeFromMicrophone(speechRecognition);
    }
}
