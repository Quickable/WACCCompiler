package StdLib;

import CompileUtils.FunctionNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StandardLibrary {

    private static HashMap<String, FunctionNode> stdLibFuncs = new HashMap<>();
    private static List<String> addedFuncs = new LinkedList<>();
    public StandardLibrary() {
        stdLibFuncs.put("printIntArray", new FunctionNode("bool", Arrays.asList("int[]")));
        stdLibFuncs.put("stringEquals", new FunctionNode("bool", Arrays.asList("char[]", "char[]")));
        stdLibFuncs.put("intArrayContains", new FunctionNode("bool", Arrays.asList("int[]", "int")));
        stdLibFuncs.put("sumIntArray", new FunctionNode("int", Arrays.asList("int[]")));
        stdLibFuncs.put("stringContains", new FunctionNode("bool", Arrays.asList("string", "string")));
    }

    public List<StringBuilder> add(String ident) {
        if (!addedFuncs.contains(ident)) {
            addedFuncs.add(ident);
            return loadFile(ident);
        }
        return null;
    }

    public void printStdLibFuncs() {
        for (Map.Entry<String, FunctionNode> entry : stdLibFuncs.entrySet()) {
            System.out.println("");
            String ident = entry.getKey().toString();;
            String types = entry.getValue().toString();
            System.out.println("Function \"std." + ident + "\", " + types);
        }
    }

    public FunctionNode getFunction(String ident) {
        return stdLibFuncs.get(ident);
    }

    private List<StringBuilder> loadFile(String ident) {
        List<StringBuilder> output = null;
        try {
            FileReader fileReader = new FileReader("./src/StdLib/" + ident + ".s");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            boolean textSection = false;
            boolean dataSection = false;
            int i = 2;
            output = new LinkedList<>();
            output.add(0, new StringBuilder());
            output.add(1, new StringBuilder());

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.contains(".text")) {
                    textSection = true;
                    dataSection = false;
                    continue;
                }
                if (line.contains(".data")) {
                    dataSection = true;
                }
                if (line.contains("*DEPENDENCIES*")) {
                    textSection = false;
                    continue;
                }
                if (line.contains(".global")) {
                    continue;
                }
                if (dataSection) {
                    output.get(0).append(line + '\n');
                } else if (textSection){
                    output.get(1).append(line + '\n');
                } else {
                    StringBuilder sB = new StringBuilder();
                    sB.append(line.replaceAll("\\s", ""));
                    output.add(i, sB);
                    i++;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            "./src/StdLib/" + ident + ".s");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + "./src/StdLib/" + ident + ".s");

        }
        return output;
    }
}
