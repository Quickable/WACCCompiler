package CompileUtils;

import java.util.HashMap;
import java.util.LinkedList;

public class Scope<T> {

    private final HashMap<String, T> table = new HashMap<>();
    private final HashMap<String, Integer> addressTable = new HashMap<>();
    private final Scope<T> enclosingScope;
    public final ValueTable valueTable;
    private ErrorReporter errorReporter;
    private LinkedList<Scope<T>> scopeBelow = new LinkedList<Scope<T>>();
    private int currentBelowTable = 0;
    private int memorySize;

    public Scope(Scope<T> iT, ErrorReporter errorReporter,
                 ValueTable valueTable) {
        this.enclosingScope = iT;
        this.errorReporter = errorReporter;
        this.valueTable = valueTable;
    }
    
    public void setMemSize(int mem) {
    	memorySize = mem;
    }

    public void addType(String ident, T obj, int lineNum, int charNum) {
        if (table.containsKey(ident)) {
            errorReporter.addSemantic(new SemanticError(lineNum, charNum, ident
                    + " already defined"));
        }

        table.put(ident, obj);
    }

    public Scope<T> getScopeAbove() {
        return this.enclosingScope;
    }

    public void addScopeBelow(Scope<T> iT) {
        this.scopeBelow.addLast(iT);
    }

    public Scope<T> getScopeBelow() {
        Scope<T> belowScope = this.scopeBelow.get(currentBelowTable);
        currentBelowTable++;
        return belowScope;
    }

    private T exists(String ident) {
        T value = table.get(ident);

        if (value != null) {
            return value;
        }

        return null;
    }

    public boolean identExistsLocally(String ident) {
        T obj = exists(ident);
        return obj != null;
    }

    public T getTypeGlobal(String ident, int lineNum, int charNum) {
        Scope<T> currentScope = this;

        while (currentScope != null) {
            T obj = currentScope.exists(ident);

            if (obj != null) {
                return obj;
            }

            currentScope = currentScope.enclosingScope;
        }

        errorReporter.addSemantic(new SemanticError(lineNum, charNum, ident
                + " not initialised"));
        errorReporter.print();
        return null;
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Scope) {
//            Scope<T> scope = (Scope<T>) obj;
//            return scope.table.equals(this.table);
//        }
//
//        return false;
//    }

    public void resetTableCounter() {
        currentBelowTable = 0;
        for (Scope<T> iT : scopeBelow) {
            iT.resetTableCounter();
        }
    }

    public void addAddress(String ident, Integer address) {
        addressTable.put(ident, address);
    }

    public Integer getAddress(String ident) {
        Integer address = addressTable.get(ident);
        if (address == null && enclosingScope == null) {
            return null;
        }
        if (address == null) {
            return enclosingScope.getAddress(ident) + memorySize;
        }
        return address;
    }

    public Integer getAddressGlobal(String ident, int scopeOffset, boolean forceOffset) {
        Integer address = addressTable.get(ident);
        if (address == null && enclosingScope == null) {
            return null;
        }
        if (address == null) {
            return enclosingScope.getAddress(ident) + scopeOffset + memorySize;
        }
        if (forceOffset) {
            return address + scopeOffset;
        }
        return address;
    }
}
