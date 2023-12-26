import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class DropboxService {

    @Autowired
    private DbxClientV2 dropboxClient;

    public void uploadFile(InputStream fileInputStream, String fileName) {
        try (InputStream in = fileInputStream) {
            FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + fileName)
                    .uploadAndFinish(in);
            // Handle metadata or any additional logic
        } catch (Exception e) {
            // Handle exceptions
        }
    }

    // Add more methods for other Dropbox API functionalities as needed
}