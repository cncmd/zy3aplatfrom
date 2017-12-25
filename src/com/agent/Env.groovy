package com.agent
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

class Env {
    static def json_encode = { obj -> new JsonOutput().toJson(obj) }
    static def json_decode = { text-> new JsonSlurper().parseText(text)}
    static def log = {text-> println(text)}
}