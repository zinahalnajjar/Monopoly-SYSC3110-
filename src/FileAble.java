package com.company;

import java.io.Serializable;

public interface FileAble extends Serializable {

    public static String fileName = "data.bin";

    /**
     * Saves the object passed as argument to a file
     */
    public void save(Game game);

    /**
     * Saves the caller object to a file
     */
    public void save();

    /**
     *
     * @return returns stored object in the file
     */
    public Game load();

}
