package sa.edu.kau.fcit.cpit252;

import java.io.EOFException;

// Proxy for OCR service with rate limiting
public class OCRServiceProxy implements OCRService {
private final OCRService realService;
private final String key;

//max request a free account can send
private final int MAX_REQUESTs = 3;
private Map<String, Integer> requestCounts;

public OCRServiceProxy(String dataPath, String Key){
    this.realService = new RealOCRService(dataPath);
    this.key =key;
    this.requestCounts = new HashMap<>();
}

    @Override
    public String recognizeText(String imagePath, String language) throws Exception{
    // Check how many request were made for accounts on the free trial
    int requestCounts = this.requestCounts.getOrDefault(this.key,1);
    if(requestCounts<= MAX_REQUESTs){
        //increment the request counts and store it in the map
        this.requestCounts.put(this.key, requestCounts + 1);
        //perform ORC on the given image
        return this.realService.recognizeText(imagePath, language);
    }else{
        throw new EOFException("You have sent to many request to our service. to send more"+"Requests, please create an account and select a paid.");
    }
    }

}
