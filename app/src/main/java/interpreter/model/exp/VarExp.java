package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String id) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        return symTable.lookup(id);
        // Expresia doar caută numele (ex: "v") în dicționar.
        // Metoda lookup() din MyDictionary aruncă automat eroare dacă variabila nu e găsită.
        // Dacă o găsește, îi returnează valoarea (IntValue(5)) către cel care a cerut-o.
    }

    @Override
    public String toString() {
        return id;
    }
}