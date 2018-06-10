package com.itacademy.jd2.po.hotel.service.googledrive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.itacademy.jd2.po.hotel.service.IStorageService;

@Service
public class StorageServiceImpl implements IStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    private final Path rootLocation;

    public StorageServiceImpl(final StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(final MultipartFile file) {
        try {
            if (file.isEmpty()) {
                LOGGER.warn("Failed to store empty file");
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            LOGGER.warn("Failed to store file");
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            LOGGER.warn("Failed to read stored files");
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(final String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(final String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                LOGGER.warn("Could not read file: {}", filename);
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            LOGGER.warn("Could not read file: {}", filename);
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
       // FileSystemUtils.deleteRecursively(rootLocation.toFile());
        for (java.io.File f :  rootLocation.toFile().listFiles()) {
            FileSystemUtils.deleteRecursively(f);
            /*f.delete();*/
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            LOGGER.warn("Could not initialize storage");
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String getUploadedFileUrl(MultipartFile file) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        java.io.File filePath = new java.io.File(rootLocation + "/" + file.getOriginalFilename());
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        // адрес папки, куда грузим файлы
        // https://drive.google.com/drive/folders/1rOcBYXePv0QRERJZKXRmKP02pTyZi58G?usp=sharing
        fileMetadata.setParents(Arrays.asList("1rOcBYXePv0QRERJZKXRmKP02pTyZi58G"));
        File f = service.files().create(fileMetadata, mediaContent).setFields("id").execute();
        String url = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=" + f.getId();
        return url;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveQuickstart.class.getResourceAsStream(CLIENT_SECRET_DIR);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                        .setAccessType("offline").build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
}
