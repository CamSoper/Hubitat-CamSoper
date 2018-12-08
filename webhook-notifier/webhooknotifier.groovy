/**
 *  Webhook Notifier Last updated 12/7/2018 1.0
 *  Based on Pushbullet 
 *  DRIVER CODE
 * 
 *  Copyright 2015 Eric Roberts as Pushbullet (device)
 *  Adapted for Hubitat Elevation by Brian S. Lowrance (Rayzurbock) 
 *  Re-adapted for generic web hook use by Cam Soper (https://github.com/CamSoper)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

metadata {
    definition (name: "WebhookNotifier", namespace: "camthegeek", author: "Cam Soper") {
        command "test"
        capability "Polling"
        capability "Refresh"
        capability "Speech Synthesis"
    }
}

def parse(String description) {
    log.debug "Parsing '${description}'"
}

def poll() { 
}

def refresh() {
    log.debug "Executing 'refresh'"
    poll()
}

def test() {
    log.debug "Executing 'test'"
    speak("WebhookNotifier Test")
}

def speak(message) {
    log.debug("message: $message")
    parent.speak(message)
}
