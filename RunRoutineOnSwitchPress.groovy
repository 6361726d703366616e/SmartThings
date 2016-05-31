/**
 *  Run Routine on Switch Press
 *
 *  Copyright 2015 Nathaniel Hall
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
    name: "Run Routine on Switch Press",
    namespace: "6361726d703366616e",
    author: "Nathaniel Hall",
    description: "Run Routine on switch press",
    category: "Mode Magic",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	page(name: "selectActions", nextPage: "Preferences", uninstall: true)
	page(name: "Preferences", install: true, uninstall: true) {
    	section("Switch to monitor") {
			input "watchswitch", "capability.switch", required: true, title: "Which switch?"
	        // Add input to make change on "On" or "Off"
		}
        section([mobileOnly:true]) {
            label title: "Assign a name", required: false
            mode title: "Set for specific mode(s)", required: false
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
	subscribe(watchswitch, "switch", changeMode)
}

def changeMode(watchswitch) {
	def selectedAction = settings.action
	if ( watchswitch.currentSwitch == "off" ) {
		location.helloHome?.execute(selectedAction)
	}
}

def selectActions() {
    dynamicPage(name: "selectActions", title: "Select Hello Home Action to Execute", install: false, uninstall: false) {

        // get the available actions
            def actions = location.helloHome?.getPhrases()*.label
            if (actions) {
            // sort them alphabetically
            actions.sort()
                    section("Hello Home Actions") {
                            log.trace actions
                // use the actions as the options for an enum input
                input "action", "enum", title: "Select an action to execute", options: actions
                    }
            }
    }
}
// TODO: implement event handlers
