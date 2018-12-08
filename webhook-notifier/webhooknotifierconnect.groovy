/**
 *  Webhook Notifier Connect - created 12/7/2018 1.0
 *  APP CODE
 *
 *  Copyright 2015 Eric Roberts as Pushbullet Connect
 *  Adapted for Hubitat Elevation by Brian S. Lowrance (https://github.com/Rayzurbock) 
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
definition(
    name: "Webhook Notifier Connect",
    namespace: "camthegeek",
    author: "Cam Soper",
    description: "Setup a 'speech' device that sends a webhook.",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    state.version = "1.0"
    page name: "enterUrl"
}

def enterUrl() {
    def pageProperties = [
        name: "enterUrl",
        title: "Notifier Properties",
        install: true,
        uninstall: true
    ]
    
    return dynamicPage(pageProperties) {
        section("Enter your webhook URL") {
            input "webhookUrl", "text", title: "Webhook URL", required: true
        }
        section("What should the message property name in the JSON payload be?") {
            input "valueName", "text", title: "JSON Property Name", required: true
        }
        section("Enter an optional prefix for your message (such as 'Hubitat says'). No trailing space required.") {
            input "prefixText", "text", title: "Message Prefix", required: false
        }
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"

    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    
    unsubscribe()
    initialize()
}

def initialize() {
    def devices = getChildDevices()
	if(!devices)
	{
		def d = addChildDevice("camthegeek", "WebhookNotifier", "webhookNotifier", null, [name: "Webhook Notifier", label: "Webhook Notifier", completedSetup: true])
	}
	state.webhookUrl = settings.webhookUrl
    state.valueName = settings.valueName
    state.prefixText = settings.prefixText
    state.speak = false
}

def uninstalled() {
    removeChildDevices(getChildDevices())
}

private removeChildDevices(delete) {
    delete.each {
        deleteChildDevice(it.deviceNetworkId)
    }
}

def speak(message) {
    state.speak = false
    log.debug "speak message: $message, url: ${state.webhookUrl}" 
       
    def successClosure = { response ->
      log.debug "speak request was successful, $response.data"
      sendEvent(name:"speak", value: message, isStateChange:true)
      state.speak = true
    }

    if(state.prefixText)
    {
		log.debug "adding prefix"
        message = state.prefixText + " " + message
    }

    def postBody = [
		"${state.valueName}": message
		]  
      
	def params = [
		uri: state.webhookUrl,
        requestContentType: "application/json",
        headers: ["Content-Type": "application/json"],
		body: postBody
    ]
    
	log.debug "payload: ${params}"
    httpPost(params, successClosure)
}

def TRACE(msg) {
    log.debug(msg)
}
