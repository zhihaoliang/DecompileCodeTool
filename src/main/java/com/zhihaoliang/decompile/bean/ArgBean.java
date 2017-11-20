package com.zhihaoliang.decompile.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * Created by haoliang on 2017/11/16.
 * email:zhihaoliang07@163.com
 *
 * @author zhihaoliang
 */
@Root(name = "argBean", strict = false)
public class ArgBean {


    @Attribute(name = "state")
    private String state;

    @Attribute(name = "oldName")
    private String oldName;

    @Attribute(name = "newName")
    private String newName;

    private boolean isCopy;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public boolean isCopy() {
        return isCopy;
    }

    public void setCopy(boolean copy) {
        isCopy = copy;
    }

    public boolean equals(ArgBean obj) {
        if (obj == null) {
            return false;
        }

        return state.equals(obj.getState()) && oldName.equals(obj.getOldName());
    }
}
