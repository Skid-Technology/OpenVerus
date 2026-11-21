package me.levansj01.verus.storage.database.check;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.check.Check;

public class CheckValues {
    private boolean alert;
    private int maxViolation;
    private List<String> commands;
    private boolean punish = true;
    private final String checkId;

    public void setPunish(boolean punish) {
        this.punish = punish;
    }

    public String getCommand(int value) {
        return this.commands.get(value);
    }

    public boolean isAlert() {
        return this.alert;
    }

    public void setMaxViolation(int maxViolation) {
        this.maxViolation = maxViolation;
    }

    public boolean hasCommands() {
        return !this.commands.isEmpty();
    }

    public int getMaxViolation() {
        return this.maxViolation;
    }

    public boolean hasCommand(String str) {
        return this.commands.contains(str);
    }

    public CheckValues(Check check) {
        this(check.identifier());
        this.maxViolation = check.getMaxViolation();
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public CheckValues(String checkId) {
        this.alert = true;
        this.maxViolation = Integer.MAX_VALUE;
        this.commands = new ArrayList<>();
        this.checkId = checkId;
    }

    public String getCheckId() {
        return this.checkId;
    }

    public int getCommandsSize() {
        return this.commands.size();
    }

    public void addCommand(String str) {
        this.commands.add(str);
    }

    public void removeCommand(int value) {
        this.commands.remove(value);
    }

    public boolean isPunish() {
        return this.punish;
    }

    public List<String> getCommands() {
        return this.commands;
    }
}
