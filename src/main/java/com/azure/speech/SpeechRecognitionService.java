package com.azure.speech;

import com.azure.dao.SpeechRecognition;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 语音转文字
 */
@Service
public class SpeechRecognitionService {
    private static String YourSubscriptionKey = "14d20e5808e3498890e0ad8cfa9e7691";
    private static String YourServiceRegion = "southeastasia";

    /**
     * 语音转文字 mp3格式
     * @param speechRecognition
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void recognizeFromMicrophone(SpeechRecognition speechRecognition) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);
        speechConfig.setSpeechRecognitionLanguage(speechRecognition.getLanguage());
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(speechRecognition.getPath());
        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);

        System.out.println("Speak into your microphone.");
        Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
        SpeechRecognitionResult speechRecognitionResult = task.get();

        if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
            System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
        }
        else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
            System.out.println("NOMATCH: Speech could not be recognized.");
        }
        else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
            CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
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