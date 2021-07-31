package interpretador;

public class Token <T>{
    public TokenType tipo;
    public T atributo;
    
    public boolean temValor(){
        return (atributo!=null);
    }
    
    public Token(TokenType tipo, T value){
        this.tipo = tipo;
        atributo = value;
    }
}
