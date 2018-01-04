package com.agent
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

class Env {
    static final VERSION="0.0.0.8"
    static def json_encode = { obj -> new JsonOutput().toJson(obj) }
    static def json_decode = { text-> new JsonSlurper().parseText(text)}
    static def log = {text-> println(text)}
}