package com.azure.speech;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.translation.SpeechTranslationConfig;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognitionResult;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognizer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 还需要调试   语音翻译为其他语言的语音/文本
 * https://docs.microsoft.com/zh-cn/azure/cognitive-services/speech-service/get-started-speech-translation?tabs=script%2Cterminal&pivots=programming-language-java
 */
public class App {

    static final String SPEECH__SUBSCRIPTION__KEY = "14d20e5808e3498890e0ad8cfa9e7691";
    static final String SPEECH__SERVICE__REGION = "southeastasia";

    public static void main(String[] args) {
        try {
            translateSpeech();
            System.exit(0);
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(1);
        }
    }

    static void translateSpeech() throws ExecutionException, InterruptedException {
        SpeechTranslationConfig translationConfig = SpeechTranslationConfig.fromSubscription(
                SPEECH__SUBSCRIPTION__KEY, SPEECH__SERVICE__REGION);

        String fromLanguage = "en-US";
        String[] toLanguages = { "it", "fr", "de" };
        translationConfig.setSpeechRecognitionLanguage(fromLanguage);
        for (String language : toLanguages) {
            translationConfig.addTargetLanguage(language);
        }

        try (TranslationRecognizer recognizer = new TranslationRecognizer(translationConfig)) {
            System.out.printf("Say something in '%s' and we'll translate...", fromLanguage);

            TranslationRecognitionResult result = recognizer.recognizeOnceAsync().get();
            if (result.getReason() == ResultReason.TranslatedSpeech) {
                System.out.printf("Recognized: \"%s\"\n", result.getText());
                for (Map.Entry<String, String> pair : result.getTranslations().entrySet()) {
                    System.out.printf("Translated into '%s': %s\n", pair.getKey(), pair.getValue());
                }
            }
        }
    }
}
