package com.lianggao.whut.androidebook.Model;

/**
 * @author LiangGao
 * @description:
 * @data:${DATA} 9:20
 */
public class ClassBook {
    private String class_name;
    private String file_name;
    private String file_path;

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    @Override
    public String toString() {
        return "ClassBook{" +
                "class_name='" + class_name + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_path='" + file_path + '\'' +
                '}';
    }
}
