package com.silion.androidproject.diffutil;

/**
 * Created by silion on 2017/8/16.
 */

public class Model implements Cloneable {
    String name;
    int imageId;

    public Model(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    @Override
    protected Model clone() throws CloneNotSupportedException {
        Model model = (Model) super.clone();
        return model;
    }

    /**
     * 名字相同则认为是同一个model
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Model) obj).name);
    }

    /**
     * 对比数据是否相同
     *
     * @param model
     * @return
     */
    public boolean equalsContents(Model model) {
        if (!this.name.equals(model.name)) {
            return false;
        }

        if (this.imageId != model.imageId) {
            return false;
        }

        return true;
    }
}
