package interpretador;

public class Lexer {
    final private String espaco = " \n\t";
    public int posicao;
    public String input;
    
    public boolean temInput(){
        return !input.isEmpty() && posicao < input.length();
    }
    
    public Lexer(String input){
        this.input = input;
        posicao = 0;
    }
    
    private char proxChar(){
        if(posicao == input.length()){
            return Character.MIN_VALUE;
        }
        return input.charAt(posicao++);
    }
    
    public Token proxToken(){
        Character peek;
        do{
            peek = proxChar();
        }while(espaco.contains(peek.toString()));
        
        if(Character.isDigit(peek)){
            String v = "";
            do{
                v+=peek;
                peek = proxChar();
            }while(Character.isDigit(peek)||peek == '.');
            if(peek != Character.MIN_VALUE){
                posicao--;
            }
            return new Token(TokenType.Num, Float.valueOf(v));
        }
        
        switch(peek){
            case '$':
                var v = "";
                do{
                    v+=peek;
                    peek = proxChar();
                }while(!espaco.contains(peek.toString())&&peek!='+'&&peek!='-'
                        &&peek!='*'&&peek!='/'&&peek!='('&&peek!=')'&&peek!='='
                        &&peek!=';');
                if(peek != Character.MIN_VALUE){
                    posicao--;
                }
                return new Token(TokenType.Var, v);
            
            case '+':
                return new Token(TokenType.Sum, null);
               
            case '-': 
                return new Token(TokenType.Sub, null);
              
            case '*':
                return new Token(TokenType.Mult, null);
                
            case '/':
                return new Token(TokenType.Div, null);
                
            case '=':
                return new Token(TokenType.Comp, null);
            
            case '(':
                return new Token(TokenType.OpBracket, null);
                
            case ')':
                return new Token(TokenType.ClBracket, null);
                
            case ';':
                return new Token(TokenType.EOL, null);
            
            case 'p':
                String p = "";
                do{
                    p+=peek;
                    peek = proxChar();
                }while(peek != '(');
                if(peek != Character.MIN_VALUE){
                    posicao--;
                }
                return new Token(TokenType.Print, p);
                    
            case Character.MIN_VALUE:
                return new Token(TokenType.EOF, null);
                
            default:
                return new Token(TokenType.Invalido, null);
        }
    }
}