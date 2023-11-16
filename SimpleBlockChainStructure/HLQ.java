package SimpleBlockChainStructure;

import java.util.ArrayList;

public class HLQ {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    public static void main(String[] args){
        blockchain.add(new Block("Faculty of Information Technology", null));
        blockchain.add(new Block("Hanoi University of Civil Engineering", 
            blockchain
                .get(blockchain.size() - 1 )
                .Hash
        ));
        blockchain.add(new Block("66PM2", 
            blockchain
                .get(blockchain.size() - 1 )
                .Hash
        ));

        for(int i = 0; i < blockchain.size(); i++){
            System.out.println("Block " + i);
            System.out.println("Previous Hash: " + blockchain.get(i).previousHash);
            System.out.println("Timestamp: " + blockchain.get(i).getTimeStamp());
            System.out.println("Data: " + blockchain.get(i).getData());
            System.out.println("Hash: " + blockchain.get(i).Hash);
            System.out.println("-----------------------------------------------");
        }
    }
}
