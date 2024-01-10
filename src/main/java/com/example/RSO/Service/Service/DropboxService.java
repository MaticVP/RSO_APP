package com.example.RSO.Service.Service;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.example.RSO.Service.Controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class DropboxService {

    @Autowired
    private DbxClientV2 dropboxClient;

    Logger logger = LoggerFactory.getLogger(DropboxService.class);

    public void uploadFile(InputStream fileInputStream, String fileName, String user_name) {
        logger.info("Entering (uploadFile) DROPBOX API");
        try
        {
            dropboxClient.files().createFolderV2("/"+user_name);
        }
        catch (Exception f) {
            try (InputStream in = fileInputStream) {
                if (dropboxClient != null) {
                    FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + user_name + "/" + user_name+".png")
                            .uploadAndFinish(in);
                }
                // Handle metadata or any additional logic
            } catch(Exception e) {
                logger.error(e.toString());
            }
        }
        logger.info("Exiting (uploadFile) DROPBOX API");
    }

    public byte[] getPhoto(String user_name) {
        logger.info("Entering (getPhoto) DROPBOX API");
        try
        {
            dropboxClient.files().createFolderV2("/"+user_name);
        }
        catch (Exception f) {
            try (InputStream inputStream = dropboxClient.files().downloadBuilder("/" + user_name + "/" + user_name+".png").start().getInputStream()) {
                if (dropboxClient != null) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    return out.toByteArray();
                }
                // Handle metadata or any additional logic
            } catch(Exception e) {
                logger.error(e.toString());
            }
        }
        logger.info("Exiting (getPhoto) DROPBOX API");
        return new byte[0];
    }

    public void dropBoxPing() throws DbxException {
        dropboxClient.files().listFolder("");
    }

    public byte[] getImage(String user_name,String project_name) {
        logger.info("Entering (getImage) DROPBOX API");
        try
        {
            dropboxClient.files().createFolderV2("/"+user_name);
        }
        catch (Exception f) {
            try (InputStream inputStream = dropboxClient.files().downloadBuilder("/"+user_name+"/"+project_name).start().getInputStream()) {
                if (dropboxClient != null) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    return out.toByteArray();
                }
                // Handle metadata or any additional logic
            } catch(Exception e) {
                logger.error(e.toString());
            }
        }
        logger.info("Exiting (getImage) DROPBOX API");
        return new byte[0];
    }

    public void saveImage(InputStream fileInputStream, String project_name, String user_name) {
        logger.info("Entering (saveImage) DROPBOX API");
        try
        {
            dropboxClient.files().createFolderV2("/"+user_name);
        }
        catch (Exception f) {
            try (InputStream in = fileInputStream) {
                if (dropboxClient != null) {
                    FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + user_name + "/" + project_name+".png")
                            .uploadAndFinish(in);
                }
                // Handle metadata or any additional logic
            } catch(Exception e) {
                logger.error(e.toString());
            }
        }
        logger.info("Exiting (saveImage) DROPBOX API");
    }

    public void createFolder(InputStream fileInputStream, String user_profile_path) {
        logger.info("Entering (createFolder) DROPBOX API");
        try (InputStream in = fileInputStream) {
            if (dropboxClient != null) {
                dropboxClient.files().createFolderV2("/"+user_profile_path);
            }
            // Handle metadata or any additional logic
        } catch (Exception e) {
            logger.error(e.toString());
        }
        logger.info("Exiting (createFolder) DROPBOX API");
    }

    // Add more methods for other Dropbox API functionalities as needed
}