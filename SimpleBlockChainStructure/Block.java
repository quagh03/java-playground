package SimpleBlockChainStructure;

import java.util.Date;

public class Block {
    public String Hash;
    public String previousHash;
    private String data;
    private Long timeStamp;

    public Block(String data, String previousHash){
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.Hash = calculateHash();
    }

    public String calculateHash(){
        String calculatedHash = crypt.sha256(previousHash + Long.toString(timeStamp) + data);

        return calculatedHash;
    }

    public String getData(){
        return this.data;
    }

    public Long getTimeStamp(){
        return this.timeStamp;
    }
}
