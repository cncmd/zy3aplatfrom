/**
 * @author agent.zy
 */

package com.agent

class StateMechine {
    def currState;
    def prevState;
    def owner;
    
    StateMechine (ow) {
        owner = ow;
    }
    
    def revertPreState() {
        this.changeState(this.prevState);
    }
    
    def setPrevState(state) {
        this.prevState = state;
    }
    
    def getCurrentState() {
        return this.currState;
    }
    
    def changeState(state) {
        this.prevState = this.currState;
        if(this.prevState) {
            this.prevState.exit(this.owner);
        }
        this.currState = state;
        this.currState.enter(this.owner);
    }
    
    def update() {
        if(this.currState) {
            this.currState.execute(this.owner);
        }
    }
}

