package com.itacademy.jd2.po.hotel.service;

import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

public interface IStorageService {
    String APPLICATION_NAME = "IT.Shmotel";
    JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    // Directory to store user credentials.
    String CREDENTIALS_FOLDER = "credentials";

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved credentials/ folder.
     */
    List<String> SCOPES = Collections
            .singletonList(DriveScopes.DRIVE_PHOTOS_READONLY/* DRIVE */);
    String CLIENT_SECRET_DIR = "/client_secret.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT
     *            The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException
     *             If there is no client_secret.
     */

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    String getUploadedFileUrl(MultipartFile file) throws GeneralSecurityException, IOException;
}
