package io.github.itzispyder.clickcrystals.modules.settings;

public abstract class SettingBuilder<T, B extends SettingBuilder<T, B, S>, S extends ModuleSetting<T>> {

    protected SettingChangeCallback<ModuleSetting<T>> changeAction;
    protected String name, description;
    protected T def, val;

    public SettingBuilder() {
        name = description = "";
        def = val = null;
        changeAction = setting -> {};
    }

    protected <T> T getOrDef(T val, T def) {
        return val != null ? val : def;
    }

    public B name(String name) {
        this.name = name;
        return (B)this;
    }

    public B description(String description) {
        this.description = description;
        return (B)this;
    }

    public B def(T def) {
        this.def = def;
        return (B)this;
    }

    public B val(T val) {
        this.val = val;
        return (B)this;
    }

    public B onSettingChange(SettingChangeCallback<ModuleSetting<T>> changeAction) {
        this.changeAction = changeAction;
        return (B)this;
    }

    public abstract S build();
}
