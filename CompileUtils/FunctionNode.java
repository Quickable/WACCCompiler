package CompileUtils;

import java.util.List;

public class FunctionNode {
    public final String returnType;
    public final List<String> paramTypes;

    public FunctionNode(String returnType, List<String> paramTypes) {
        this.returnType = returnType;
        this.paramTypes = paramTypes;
    }

    @Override
    public String toString() {
        String output = "Return type: " + returnType;
        if (paramTypes.size() == 0) {
            output += ", No arguments.";
            return output;
        } else {
            output += ", Argument types: ";
        }
        for(int i=0; i < paramTypes.size(); i++) {
            output += paramTypes.get(i);
            if (i != paramTypes.size() - 1) {
                output += ", ";
            } else output += ".";
        }
        return output;
    }
}
