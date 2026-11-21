package me.levansj01.verus.gui.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.impl.CheckGUI;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.gui.impl.TypeGUI;

public class GUIManager {
    private final Map<CheckType, GUI> typeGuis = new HashMap<>();
    private static GUIManager instance;
    private MainGUI mainGui;
    private CheckGUI checkGui;

    public void enable(VerusPlugin plugin) {
        Check[] checks = plugin.getTypeLoader().loadChecks();

        for (CheckType type : CheckType.values()) {
            if (!type.ignore()) {
                this.typeGuis.put(type, new TypeGUI(type, Arrays.stream(checks).filter(Array -> Array.getType() == type).collect(Collectors.toList())));
            }
        }

        this.mainGui = new MainGUI();
        this.checkGui = new CheckGUI();
    }

    public Map<CheckType, GUI> getTypeGuis() {
        return this.typeGuis;
    }

    public static GUIManager getInstance() {
        return instance == null ? (instance = new GUIManager()) : instance;
    }

    public GUI getTypeGui(CheckType type) {
        return this.typeGuis.get(type);
    }

    public CheckGUI getCheckGui() {
        return this.checkGui;
    }

    public MainGUI getMainGui() {
        return this.mainGui;
    }

    public void disable() {
        if (this.mainGui != null) {
            this.mainGui.clear();
        }

        if (this.checkGui != null) {
            this.checkGui.clear();
        }

        this.typeGuis.values().forEach(GUI::clear);
    }
}
