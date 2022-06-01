package com.azure.speech;

import com.azure.dao.SpeechSynthesis;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * 文字转语音
 */
@Service
public class SpeechSynthesisService {
    private static String subscriptionKey = "0d83197a466b4255833085b903456b68";
    private static String subscriptionRegion = "eastasia";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        text2Speech("Note: the voice setting will not overwrite the voice element in input SSML.");
    }

    public void text2SpeechDown(SpeechSynthesis speechSynthesis) throws ExecutionException, InterruptedException {
        SpeechConfig config = SpeechConfig.fromSubscription(subscriptionKey, subscriptionRegion);
        String text = speechSynthesis.getText();
        //语言类型为中文
        config.setSpeechSynthesisLanguage(speechSynthesis.getLanguage());
        config.setSpeechSynthesisVoiceName(speechSynthesis.getVoiceName());
        if (text.isEmpty()) {
            return;
        }
        AudioConfig audioConfig = AudioConfig.fromWavFileOutput(String.format("E:/20220531/%s.mp3", UUID.randomUUID()));
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(config, audioConfig);
        {
            SpeechSynthesisResult result = synthesizer.SpeakTextAsync(text).get();
            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("Speech synthesized for text [" + text + "]");
            } else if (result.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }
            result.close();
        }
        synthesizer.close();
        System.exit(0);
    }

    public void text2Speech(SpeechSynthesis speechSynthesis) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(subscriptionKey, subscriptionRegion);
        speechConfig.setSpeechSynthesisLanguage(speechSynthesis.getLanguage());
        speechConfig.setSpeechSynthesisVoiceName(speechSynthesis.getVoiceName());
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);
        // Get text from the console and synthesize to the default speaker.
        System.out.println("Enter some text that you want to speak >");
        String text = speechSynthesis.getText();
        if (text.isEmpty()) {
            return;
        }

        SpeechSynthesisResult speechRecognitionResult = speechSynthesizer.SpeakTextAsync(text).get();

        if (speechRecognitionResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + text + "]");
        } else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechRecognitionResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }
        }

        System.exit(0);
    }
}