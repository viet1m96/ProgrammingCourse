package main_objects;

import java.io.Serializable;

public class Instruction implements Serializable {
    private final static long serialVersionUID = 11L;
    private String command;
    private String description;
    private String syntax;

    public Instruction(){}

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
