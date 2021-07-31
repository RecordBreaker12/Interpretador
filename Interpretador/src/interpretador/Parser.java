package interpretador;

import java.util.Objects;


public class Parser <T>{
    public String output;
    private final Lexer lex;
    private Token lookahead;
    private final Simbolos table = new Simbolos();
    
    public Parser(Lexer lex){
        this.lex = lex;
        lookahead = lex.proxToken();
    } 
    
    private void match(Token token) throws Exception{
        if(lookahead.tipo == token.tipo && Objects.equals(lookahead.atributo, token.atributo)){
            lookahead = lex.proxToken();
        }
        else{
            throw new Exception("Erro: Valores não batem: '" + lookahead.tipo + "' e '" + token.tipo + "'.");
        }
    }
    
    public float expr() throws Exception{
        float t = fact();
        float v;
        switch(lookahead.tipo){
            case Sum:
                match(new Token(TokenType.Sum, null));
                v = expr();
                return t + v;
            case Sub:
                match(new Token(TokenType.Sub, null));
                v = expr();
                return t - v;
            default:
                return t;
        }
    }
    
    public void prog() throws Exception{
        stmt();
        match(new Token(TokenType.EOL, null));
        lines();
    }
    
    public void lines() throws Exception{
        if(lookahead.tipo != TokenType.EOF){
            prog();
        }
    }
    
    public void stmt() throws Exception{
        switch (lookahead.tipo) {
            case Var:
                attr();
                break;
            case Print:
                imp();
                break;
            default:
                throw new Exception("Esperado VAR ou PRINT e recebeu '" + lookahead.tipo + "'");
        }
    }
    
    public void attr() throws Exception{
        var valor = lookahead.atributo;
        match(new Token(TokenType.Var, valor));
        match(new Token(TokenType.Comp, null));
        float v = expr();
        table.set((String) valor, v);
    }
    
    public void imp() throws Exception{
        match(new Token(TokenType.Print, "print"));
        match(new Token(TokenType.OpBracket, null));
        String chave = (String) lookahead.atributo;
        match(new Token(TokenType.Var, chave));
        match(new Token(TokenType.ClBracket, null));
        float valor = table.getV(chave);
        System.out.println(valor);
    }
    
    public float fact() throws Exception{
        var termo = term();
        float fato;
        switch (lookahead.tipo) {
            case Mult:
                match(new Token(TokenType.Mult, null));
                fato = fact();
                return (float) termo * fato;
                
            case Div:
                match(new Token(TokenType.Div, null));
                fato = fact();
                return (float) termo / fato;
            
            default:
                return (float) termo;
        }
    }
    
    public float term() throws Exception{
        switch(lookahead.tipo){
            case OpBracket:
                match(new Token(TokenType.OpBracket, null));
                float v = expr();
                match(new Token(TokenType.ClBracket, null));
                return v;
            
            case Num:
                var valor = lookahead.atributo;
                match(new Token(TokenType.Num, valor));
                return (float) valor;
            
            case Var:
                String chave = (String) lookahead.atributo;
                match(new Token(TokenType.Var, chave));
                return table.getV(chave);
                
            default:
                throw new Exception("Simbolo inesperado '" + lookahead.tipo + "'");
        }
    }
}
