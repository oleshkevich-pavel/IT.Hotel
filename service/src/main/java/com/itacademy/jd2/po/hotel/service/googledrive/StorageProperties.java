package com.itacademy.jd2.po.hotel.service.googledrive;

import org.springframework.context.annotation.Configuration;

@Configuration("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "src/main/resources/upload-dir/";

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

}
