package javaparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Sample1 {

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir");
        System.out.println("root path " + root);
        File file = new File(root + "/src/main/java/Main.java");
        CompilationUnit compilationUnit = StaticJavaParser.parse(file);

        List<ImportDeclaration> importDeclarations = compilationUnit.getImports();
        for (ImportDeclaration importDeclaration : importDeclarations) {
            System.out.println("nameAsString = " + importDeclaration.getNameAsString() + " id = " + importDeclaration.getName().getIdentifier() + " Qualifier = " + importDeclaration.getName().getQualifier().get());
        }

        for (ImportDeclaration importDeclaration : importDeclarations) {
            String tryPath = convertFilePath(importDeclaration);
            File tryFile = new File(root + "/src/main/java/" + tryPath);
            System.out.println("convertFilePath(importDeclaration)" + tryPath);
            if (tryFile.exists()) {
                System.out.println("yes");
            }
        }
    }

    private static String convertFilePath(ImportDeclaration importDeclaration) {
        String importStr = importDeclaration.getNameAsString();
        String[] pathSegs = importStr.split("\\.");
        StringBuilder tryPathBuilder = new StringBuilder();
        for (int i = 0; i < pathSegs.length - 1; i++) {
            tryPathBuilder.append(pathSegs[i]).append("/");
        }
        tryPathBuilder.append(pathSegs[pathSegs.length - 1]).append(".java");
        String tryPath = tryPathBuilder.toString();
        return tryPath;
    }
}
