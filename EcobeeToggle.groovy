definition (
	name: "Ecobee Toggle",
	namespace: "6361726d703366616e",
	author: "Nathaniel Hall",
	description: "Disables and Enables Ecobee Thermostats based on door or window status",
	category: "",
	iconUrl: "http://www.nathanielhall.com/favicon.ico",
	iconX2Url: "http://www.nathanielhall.com/favicon.ico"
)

preferences {
	section() {
		paragraph "Select the door or window sensors to monitor."
		input "ContactSensor", "capability.contactSensor", title: "Contact Sensor", required: true, multiple: true
	}
	section() {
		paragraph "This SmartApp uses the Ecobee Thermostat Device Handler from SmartThings"
		paragraph "Select the Ecobee thermostat to control."
		input "Thermostat", "capability.thermostat", title: "Ecobee Thermostat", required: true, multiple: true
	}
}

def initialize() {
	subscribe ContactSensor, "contact", ContactChange
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

def ContactChange(evt) {
	log.debug "Contact ${evt.device} changed"
}

