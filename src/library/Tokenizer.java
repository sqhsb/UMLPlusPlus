package library;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Code from Elisa Baniassard CPSC410 Lecture
 */
public class Tokenizer {

    private static String program;
    private static List<String> literals;
    private String[] tokens;
    private int currentToken;
    private static Tokenizer theTokenizer;

    private Tokenizer(String filename, List<String> literalsList){
        literals = literalsList;
        try {
            program = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Didn't find file");
            System.exit(0);
        }
        spaceKillingTokenize();
    }

    //modifies: this.tokens
    //effects: will result in a list of tokens (sitting at this.tokens) that has no spaces within tokens.
    //          so if you want spaces within tokens, use the spaceHappyTokenize method (above) instead
    private void spaceKillingTokenize(){
        String tokenizedProgram = program;
        tokenizedProgram = tokenizedProgram.replace("\n","");
        tokenizedProgram = tokenizedProgram.replaceAll("([0-9]+)","_$1_");
        //System.out.println(program);

        for (String s : literals){
            tokenizedProgram = tokenizedProgram.replace(s,"_"+s+"_");
            //System.out.println(tokenizedProgram);
        }
        tokenizedProgram = tokenizedProgram.replaceAll("[ ]+","");
        //System.out.println(tokenizedProgram);
        String [] temparray=tokenizedProgram.split("[_]+");
        tokens = new String[temparray.length-1];

        System.arraycopy(temparray,1,tokens,0,temparray.length-1);
        System.out.println(Arrays.asList(tokens));
    }


    private String checkNext(){
        String token="";
        if (currentToken<tokens.length){
            token = tokens[currentToken];
        }
        else
            token="NO_MORE_TOKENS";
        return token;
    }

    public String getNext(){
        String token="";
        if (currentToken<tokens.length){
            token = tokens[currentToken];
            currentToken++;
        }
        else
            token="NULLTOKEN";
        return token;
    }


    public boolean checkToken(String regexp){
        String s = checkNext();
        System.out.println("comparing: "+s+"  to  "+regexp);
        return (s.matches(regexp));
    }

    private String checkNextToken(){
        String token="";
        if (currentToken + 1 < tokens.length){
            token = tokens[currentToken + 1];
        }
        else
            token="NO_MORE_TOKENS";
        return token;
    }
    public boolean checkNextToken(String regexp){
        String s = checkNextToken();
        System.out.println("comparing: "+s+"  to  "+regexp);
        return (s.matches(regexp));
    }


    public String getAndCheckNext(String regexp){
        String s = getNext();
        if (!s.matches(regexp)) System.exit(0);
        System.out.println("matched: "+s+"  to  "+regexp);
        return s;
    }

    public boolean moreTokens(){
        return currentToken<tokens.length;
    }

    public static void makeTokenizer(String filename, List<String> literals){
        if (theTokenizer==null){
            theTokenizer = new Tokenizer(filename,literals);
        }
    }

    public static Tokenizer getTokenizer(){
        return theTokenizer;
    }

}
