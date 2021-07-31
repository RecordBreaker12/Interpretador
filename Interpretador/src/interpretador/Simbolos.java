package interpretador;

import java.util.HashMap;

public class Simbolos {
    HashMap<String, Float> tabela = new HashMap<>();
    
    public void set(String chave, float valor){
        tabela.put(chave, valor);
    }
    
    public float getV(String chave){
        return tabela.get(chave);
    }
}
