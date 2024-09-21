//
//  RecordAudioFunction.swift
//  iosApp
//
//  Created by Sebastian Cardona Henao on 20/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import AVFoundation
import ComposeApp

class RecordAudioManager: NSObject, AVAudioRecorderDelegate {

    private var recordingSession: AVAudioSession!
    private var audioRecorder: AVAudioRecorder!
    private let onResult: (Data?) -> Void

    init(onResult: @escaping (Data?) -> Void) {
        self.onResult = onResult
        super.init()
    }

    func startRecording() {
        recordingSession = AVAudioSession.sharedInstance()
        do {
            try recordingSession.setCategory(.playAndRecord, mode: .default)
            try recordingSession.setActive(true)
            recordingSession.requestRecordPermission() { [unowned self] allowed in
                DispatchQueue.main.async {
                    if allowed {
                        self.startRecordingSession()
                    } else {
                        print("Permission denied")
                        self.onResult(nil)
                    }
                }
            }
        } catch {
            print("Failed to set up recording session: \(error.localizedDescription)")
            self.onResult(nil)
        }
    }

    private func startRecordingSession() {
        let tempDirectory = FileManager.default.temporaryDirectory
        let audioFilename = tempDirectory.appendingPathComponent("audioRecording.m4a")

        let settings = [
            AVFormatIDKey: Int(kAudioFormatMPEG4AAC),
            AVSampleRateKey: 44100.0,
            AVNumberOfChannelsKey: 2,
            AVEncoderAudioQualityKey: AVAudioQuality.high.rawValue
        ]

        do {
            audioRecorder = try AVAudioRecorder(url: audioFilename, settings: settings)
            audioRecorder.delegate = self
            audioRecorder.record()
        } catch {
            print("Failed to create audio recorder: \(error.localizedDescription)")
            self.onResult(nil)
        }
    }

    func stopRecording() {
        audioRecorder.stop()
        
        guard let audioFileURL = audioRecorder?.url else {
            onResult(nil)
            return
        }
        
        do {
            let audioData = try Data(contentsOf: audioFileURL)
            onResult(audioData)
        } catch {
            print("Failed to read audio data: \(error.localizedDescription)")
            onResult(nil)
        }

        audioRecorder = nil
    }

    // MARK: - AVAudioRecorderDelegate
    func audioRecorderDidFinishRecording(_ recorder: AVAudioRecorder, successfully flag: Bool) {
        if !flag {
            print("Recording failed")
            onResult(nil)
        }
    }
}
