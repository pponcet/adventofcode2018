package com.pponcet.adventofcode.day20;

public class State {
    private String str;
    private Room currentRoom;

    public State(String str, Room currentRoom) {
        this.str = str;
        this.currentRoom = currentRoom;
    }

    public String getString() {
        return str;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
