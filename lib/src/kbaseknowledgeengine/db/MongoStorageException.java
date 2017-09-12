package kbaseknowledgeengine.db;

public class MongoStorageException extends Exception {
    
    private static final long serialVersionUID = -3808399998367289874L;

    public MongoStorageException(String message) {
        super(message);
    }
    
    public MongoStorageException(Throwable cause) {
        super(cause);
    }

    public MongoStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
