package interpretador;


public class Interpretador {
    
    public static void main(String[] args) throws Exception {
        Lexer lex1 = new Lexer(
        "$x = 2 - (2 + 2/5);\n" +
        "$y = 2 * 10 + 1;\n" +
        "$z = ($x + $y) * 5;\n" +
        "$p = 3.14;\n" +
        "print($z);");
        
        Parser parse1 = new Parser(lex1);
        parse1.prog();
        
        Lexer lex2 = new Lexer(
        "$x = (2 - 2) + 2/5;\n" +
        "$y = 2 * 10 + 1;\n" +
        "$z = ($x + $y) * 5;\n" +
        "$p = 3.14 * $z;\n" +
        "print($p);");
        
        Parser parse2 = new Parser(lex2);
        parse2.prog();
    }
    
}
