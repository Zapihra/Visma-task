import java.util.*;

class UriComponents{

    //parses the most important parts and returns them in a list
    public List<String> parse(String uri) {
        List<String> parsed = new ArrayList<String>();
        
        String[] list = uri.split("://"); 
        parsed.add(list[0]);//visma-identity
        String[] secondParse = list[1].split("?"); 
        parsed.add(secondParse[0]);//sign
        parsed.add(secondParse[1]); //source=vismasign&documentid=105ab4

        return parsed;
    }

    //checks if the sheme is correct
    public boolean checkSheme(String sheme) {  

        if (sheme == "visma-identity") {
                return true;
        }
        else{
            return false;
        } 
    }

    //returns parsed parameters as key values in regards of the path
    public HashMap<String,Integer> checkPathAndParameters(String path, String parameters) {
        String[] parsedParameters = parameters.split("=");
        HashMap<String, Integer> key=new HashMap<String, Integer>();

        if (path == "login" ) {
            /*Example: visma-identity://login?source=severa */
            String[] parsedSecond = parsedParameters[0].split("?");
            if (parsedParameters[1] == "severa" || parsedParameters[1] == "netvisor" || parsedParameters[1] == "vismasign"){
                return key.put(parsedSecond[1], parsedParameters[1]);
            }
        }

        else if (path == "confrim"){
            //Example: source netvisor&paymentnumber 102226
            String[] parsedSecond = parsedParameters[1].split("&");

            if (parsedSecond[1] == "paymentnumber") {
                return key.put(parsedSecond[1], parsedParameters[2]);
            }
            
        }    

        else if (path == "sign"){
            
            //source vismasign&documentid 105ab44
            String[] parsedSecond = parsedParameters[1].split("&");

            if (parsedSecond[1] == "documentnumber") {
                int number = Integer.parseInt(parsedParameters[2]);

                return key.put(parsedSecond[1], number);
            }
        }
        //if they are incorrect it returns error and value of -1
        return key.put("error", -1);
    
    }
}

public class Identify{
    public static HashMap<String,Integer> main(String uri) {
        List<String> parsed = new ArrayList<String>();

        HashMap<String, Integer> key=new HashMap<String, Integer>();

        UriComponents obj = new UriComponents();
        boolean check;

        parsed = obj.parse(uri);
        check = obj.checkSheme(parsed.get(0));

        // if its the correct sheme it starts to parse the parameters
        if (check == true) {
            key = obj.checkPathAndParameters(parsed.get(1), parsed.get(2));
            /* Task:
            5. Class returns:
            Path
            Parameters as key value pairs
            */

            return key;
        }
        
    }        
    
}