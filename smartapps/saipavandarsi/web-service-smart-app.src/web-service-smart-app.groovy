/**
 *  Web Service Smart App 
 *
 *  Copyright 2017 saipavan
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
    name: "Web Service Smart App ",
    namespace: "saipavandarsi",
    author: "saipavan",
    description: "My first Smart App as webservice",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Allow external services to control these things...") {
		// writing input and which capability to perform in this section
        input "switches","capability.switch",multiple:true,required:true
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
	// TODO: subscribe to attributes, devices, locations, etc.
}

//writing mappings for both get and put 
mappings {
  path("/switches") {
    action: [
      GET: "listSwitches"
    ]
  }
  path("/switches/:command") {
    action: [
      PUT: "updateSwitches"
    ]
  }
}

// this method will get all the swithces
def listSwitches() {
	def resp =[]
	switches.each{
    	
		resp<< [name:it.displayName,value: it.currentValue("switch")]
	}
	return resp
}

// this method will execute command either "on" or "off" for the switches
void updateSwitches() {
//assign params to a variable called command
def command = params.command
// By using switch case execute the commands passed
switch(command){
	case "on":
		switches.on()
	    break
    case "off":
    	switches.off()
        break
    default:
    	httpError(400,"$command is not a valid command for all the switches specified")
}
}

