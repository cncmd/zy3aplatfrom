package com.agent

interface IState{
    def enter (owner) ;
    def execute(owner);
    def exit(owner);
}
